package ar.edu.unq.turnero.modelo.exception

class PasswordIncorrectoException: Exception() {

    override val message: String?
        get() = "La contraseña es incorrecta."

    companion object {
        private val serialVersionUID = 1L
    }
}