package ar.edu.unq.turnero.spring.controller

import ar.edu.unq.turnero.modelo.Usuario
import ar.edu.unq.turnero.service.UsuarioService
import ar.edu.unq.turnero.spring.controller.DTOs.*
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.naming.directory.InvalidAttributesException
import javax.servlet.http.HttpServletResponse

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/usuario")
class UsuarioController(private val usuarioService: UsuarioService) {

    @PostMapping("/register")
    fun register(@RequestBody usuario: UsuarioDTO, response: HttpServletResponse) : ResponseEntity<Any> {
        try {
            val user = usuarioService.crear(usuario.aModelo())
            val issuer = user!!.id.toString()
            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "secret").compact()
            response.addHeader("Authorization", jwt)
            user.token = jwt
            val userResponse = usuarioService.actualizar(user)
            return ResponseEntity.ok().body(userResponse)
        } catch (error : Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message(error.cause!!.message!!))
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody usuario: MiniUsuarioDTO, response: HttpServletResponse) : ResponseEntity<Any> {
        try {
            val user = usuarioService.recuperarUsuario(usuario.email!!, usuario.password!!)
            val issuer = user!!.id.toString()
            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 2000))
                .signWith(SignatureAlgorithm.HS512, "secret").compact()
            response.addHeader("Authorization", jwt)
            response.setHeader("Authorization", jwt)
            user.token = jwt
            val userResponse = usuarioService.actualizar(user)
            return ResponseEntity.ok().body(userResponse)
        } catch (error : Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message(error.cause!!.message!!))
        }

    }

    @GetMapping("")
    fun recuperar(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String) : ResponseEntity<Any> {
        try {
            val user = usuarioService.recuperarPorToken(token)
            return ResponseEntity.ok().body(UsuarioLogueadoDTO.desdeModelo(user!!))
        } catch (error : Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message(error.cause!!.message!!))
        }
    }

    @PutMapping("/{usuarioId}")
    fun actualizar(@PathVariable usuarioId: Long, @RequestBody usuario: UsuarioEditDTO): ResponseEntity<Any> {
        var usuarioRecuperado = usuarioService.recuperar(usuarioId.toInt())!!
        try {
            val usuario = usuarioService.actualizar(usuario.aModelo(usuarioRecuperado))
            return ResponseEntity.ok().body(usuario)
        } catch (error : Exception) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message(error.cause!!.message!!))
        }
    }

    @GetMapping("/todos")
    fun recuperarTodos() = usuarioService.recuperarTodos().map { usuario -> UsuarioDTO.desdeModelo(usuario)  }

    @DeleteMapping("/{usuarioId}")
    fun eliminar(@PathVariable usuarioId: Int) = usuarioService.eliminar(usuarioId)
}