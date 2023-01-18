package ar.edu.unq.turnero.modelo.exception

class EmailExistenteException: Exception() {

    override val message: String?
        get() = "El email ya se encuentra registrado."

    companion object {
        private val serialVersionUID = 1L
    }
}