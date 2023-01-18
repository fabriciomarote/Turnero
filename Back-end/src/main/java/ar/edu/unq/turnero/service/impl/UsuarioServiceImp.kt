package ar.edu.unq.turnero.service.impl

import ar.edu.unq.turnero.modelo.Usuario
import ar.edu.unq.turnero.modelo.exception.*
import ar.edu.unq.turnero.persistence.UsuarioDAO
import ar.edu.unq.turnero.service.TurnoService
import ar.edu.unq.turnero.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class UsuarioServiceImp(
    @Autowired
    private val usuarioDAO: UsuarioDAO,
    private val turnoService: TurnoService): UsuarioService {

    override fun crear(usuario: Usuario): Usuario {
        this.validarCampos(usuario)
        return usuarioDAO.save(usuario)
    }

    private fun validarCampos(usuario: Usuario) {
        if(validarNombreYApellido(usuario.nombreYApellido) || validarDNI(usuario.dni) || validarEmail(usuario.email)
            || validarPassword(usuario.password)) {
            throw StringVacioException()
        }
    }

    private fun validarNombreYApellido(nombreCompleto: String?) : Boolean {
        if(nombreCompleto == "") {
            throw NombreYApellidoIncompletoException()
        } else {
            return false
        }
    }

    private fun validarDNI(dni: Long?) : Boolean {
        if (dni == null || dni <= 999999 || dni > 99999999) {
            throw  DniInvalidoException()
        } else if(usuarioDAO.findByDni(dni) != null) {
            throw DniExistenteException()
        } else {
            return false
        }
    }

    private fun validarEmail(email: String?) : Boolean {
        val usuario = usuarioDAO.findByEmail(email!!)
        if(!email!!.contains("@")) {
           return throw EmailInvalidoException()
        } else if (usuario != null) {
           return throw EmailExistenteException()
        }
        return false
    }

    private fun validarPassword(password: String?) : Boolean {
        if (password == "") {
            throw PasswordVacioException()
        } else if (password!!.length < 8){
            throw PasswordInvalidoException()
        }
        return false
    }

    override fun actualizar(usuario: Usuario): Usuario {
        if (usuario != null) {
            validarActualizacion(usuario)
            return usuarioDAO.save(usuario)
        } else {
            throw Exception("El usuario no existe.")
        }
    }

    private fun validarActualizacion(usuario: Usuario) {
         if (validarNombreYApellido(usuario.nombreYApellido) || validarPassword(usuario.password) ||
                 validarEmailDeUsuario(usuario.email!!)) {
             throw StringVacioException()
         }
    }

    private fun validarEmailDeUsuario(email: String?) : Boolean {
        val usuario = usuarioDAO.findByEmail(email!!)
        if(!email!!.contains("@")) {
            return throw EmailInvalidoException()
        } else if (usuario != null && email !== usuario.email) {
            return throw EmailExistenteException()
        }
        return false
    }

    override fun recuperar(usuarioId: Int): Usuario? {
        val user = usuarioDAO.findByIdOrNull(usuarioId.toLong())
        if (user == null) {
            throw RuntimeException("El id [${usuarioId}] no existe.")
        }
        return user
    }

    override fun recuperarUsuario(email: String, password: String) : Usuario? {
        val usuario = usuarioDAO.findByEmail(email!!)
        if (!email!!.contains("@")) {
            return throw EmailInvalidoException()
        } else if (usuario == null) {
            return throw EmailNoExistenteException()
        } else if (password!!.length < 8) {
                throw PasswordInvalidoException()
        } else if (usuario.password != password) {
            throw PasswordIncorrectoException()
        }
        return usuario
    }

    override fun recuperarPorToken(token: String) : Usuario? {
        val user = usuarioDAO.findByToken(token)
        if (token == "") {
            throw TokenInvalidoException()
        }
        else if ( user == null) {
            throw Exception("No existe un usuario con el token asignado.")
        }
        return user
    }

    override fun recuperarTodos(): List<Usuario> {
        return usuarioDAO.findAllByOrderByNombreYApellidoAsc()
    }

    override fun eliminar(usuarioId: Int) {
        turnoService.borrarUsuarioDeTodosSusTurnos(usuarioId)
        usuarioDAO.deleteById(usuarioId.toLong())
    }

    override fun clear() {
        usuarioDAO.deleteAll()
    }
}