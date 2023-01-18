package ar.edu.unq.turnero.modelo.exception

class EmailNoExistenteException: Exception() {

    override val message: String?
        get() = "El email no se encuentra registrado."

    companion object {
        private val serialVersionUID = 1L
    }
}