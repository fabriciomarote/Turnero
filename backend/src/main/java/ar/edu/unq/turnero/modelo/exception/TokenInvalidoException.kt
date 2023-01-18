package ar.edu.unq.turnero.modelo.exception

class TokenInvalidoException : Exception() {

    override val message: String?
        get() = "El token es invalido"

    companion object {

        private val serialVersionUID = 1L
    }
}