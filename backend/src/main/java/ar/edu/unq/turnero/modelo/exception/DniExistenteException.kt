package ar.edu.unq.turnero.modelo.exception

class DniExistenteException : Exception() {

    override val message: String?
        get() = "El DNI ya se encuentra registrado."

    companion object {
        private val serialVersionUID = 1L
    }
}