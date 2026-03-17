package ar.edu.unq.turnero.service.impl

import ar.edu.unq.turnero.modelo.Usuario
import ar.edu.unq.turnero.modelo.exception.*
import ar.edu.unq.turnero.persistence.UsuarioDAO
import ar.edu.unq.turnero.service.TurnoService
import ar.edu.unq.turnero.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class UsuarioServiceImp(
    @Autowired
    private val usuarioDAO: UsuarioDAO,
    private val turnoService: TurnoService
) : UsuarioService {

    override fun crear(usuario: Usuario): Usuario {
        validarCampos(usuario)
        return usuarioDAO.save(usuario)
    }

    private fun validarCampos(usuario: Usuario) {
        if (validarNombreYApellido(usuario.nombreYApellido) || validarDNI(usuario.dni) ||
            validarEmail(usuario.email) || validarPassword(usuario.password)
        ) {
            throw StringVacioException()
        }
    }

    private fun validarNombreYApellido(nombreCompleto: String?): Boolean {
        if (nombreCompleto == "") throw NombreYApellidoIncompletoException()
        return false
    }

    private fun validarDNI(dni: Long?): Boolean {
        if (dni == null || dni <= 999999 || dni > 99999999) throw DniInvalidoException()
        if (usuarioDAO.findByDni(dni) != null) throw DniExistenteException()
        return false
    }

    private fun validarEmail(email: String?): Boolean {
        if (email == null || !email.contains("@")) throw EmailInvalidoException()
        if (usuarioDAO.findByEmail(email) != null) throw EmailExistenteException()
        return false
    }

    private fun validarPassword(password: String?): Boolean {
        if (password == "") throw PasswordVacioException()
        if (password != null && password.length < 8) throw PasswordInvalidoException()
        return false
    }

    override fun actualizar(usuario: Usuario): Usuario {
        validarActualizacion(usuario)
        return usuarioDAO.save(usuario)
    }

    private fun validarActualizacion(usuario: Usuario) {
        if (validarNombreYApellido(usuario.nombreYApellido) || validarPassword(usuario.password) ||
            validarEmailDeUsuario(usuario.email)
        ) {
            throw StringVacioException()
        }
    }

    private fun validarEmailDeUsuario(email: String?): Boolean {
        if (email == null || !email.contains("@")) throw EmailInvalidoException()
        val usuario = usuarioDAO.findByEmail(email)
        if (usuario != null && email != usuario.email) throw EmailExistenteException()
        return false
    }

    override fun recuperar(usuarioId: String): Usuario? {
        return usuarioDAO.findById(usuarioId)
            .orElseThrow { RuntimeException("El id [$usuarioId] no existe.") }
    }

    override fun recuperarUsuario(email: String, password: String): Usuario? {
        if (!email.contains("@")) throw EmailInvalidoException()
        val usuario = usuarioDAO.findByEmail(email) ?: throw EmailNoExistenteException()
        if (password.length < 8) throw PasswordInvalidoException()
        if (usuario.password != password) throw PasswordIncorrectoException()
        return usuario
    }

    override fun recuperarPorToken(token: String): Usuario? {
        if (token == "") throw TokenInvalidoException()
        return usuarioDAO.findByToken(token)
            ?: throw Exception("No existe un usuario con el token asignado.")
    }

    override fun recuperarTodos(): List<Usuario> =
        usuarioDAO.findAllByOrderByNombreYApellidoAsc()

    override fun eliminar(usuarioId: String) {
        turnoService.borrarUsuarioDeTodosSusTurnos(usuarioId)
        usuarioDAO.deleteById(usuarioId)
    }

    override fun clear() {
        usuarioDAO.deleteAll()
    }
}
