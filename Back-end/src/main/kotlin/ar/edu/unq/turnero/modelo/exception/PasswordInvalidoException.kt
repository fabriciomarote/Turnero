package ar.edu.unq.turnero.modelo.exception

class PasswordInvalidoException: Exception() {

    override val message: String?
        get() = "La contraseña debe contener al menos 8 caracteres."

    companion object {
        private val serialVersionUID = 1L
    }
}