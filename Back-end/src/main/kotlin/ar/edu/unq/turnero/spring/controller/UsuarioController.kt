package ar.edu.unq.turnero.spring.controller

import ar.edu.unq.turnero.modelo.Usuario
import ar.edu.unq.turnero.service.UsuarioService
import ar.edu.unq.turnero.spring.controller.DTOs.*
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.naming.directory.InvalidAttributesException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/usuario")
class UsuarioController(private val usuarioService: UsuarioService) {

    private val jwtSigningKey = Keys.hmacShaKeyFor(
        (System.getenv("JWT_SECRET") ?: "defaultSecretKeyForDevelopmentOnlyMustBeLongEnoughForHS512Algorithm!!").toByteArray(Charsets.UTF_8)
    )

    @PostMapping("/register")
    fun register(@RequestBody usuario: UsuarioDTO, response: HttpServletResponse) : ResponseEntity<Any> {
        try {
            val user = usuarioService.crear(usuario.aModelo())
            val issuer = user.id!!
            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))
                .signWith(jwtSigningKey).compact()
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
            val issuer = user?.id!!
            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 2000))
                .signWith(jwtSigningKey).compact()
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
    fun actualizar(@PathVariable usuarioId: String, @RequestBody usuario: UsuarioEditDTO): ResponseEntity<Any> {
        val usuarioRecuperado = usuarioService.recuperar(usuarioId)
        try {
            val actualizado = usuarioService.actualizar(usuario.aModelo(usuarioRecuperado!!))
            return ResponseEntity.ok().body(actualizado)
        } catch (error: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message(error.cause?.message ?: error.message ?: "Error"))
        }
    }

    @GetMapping("/todos")
    fun recuperarTodos() = usuarioService.recuperarTodos().map { usuario -> UsuarioDTO.desdeModelo(usuario)  }

    @DeleteMapping("/{usuarioId}")
    fun eliminar(@PathVariable usuarioId: String) = usuarioService.eliminar(usuarioId)
}