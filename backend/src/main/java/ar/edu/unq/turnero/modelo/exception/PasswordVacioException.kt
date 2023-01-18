package ar.edu.unq.turnero.modelo.exception

class PasswordVacioException : Exception() {

    override val message: String?
        get() = "Debe ingresar una contraseña."

    companion object {

        private val serialVersionUID = 1L
    }
}