package ar.edu.unq.turnero.modelo.exception

class EmailInvalidoException: Exception() {

    override val message: String?
            get() = "El email no es valido, debe contener @"

    companion object {
        private val serialVersionUID = 1L
    }
}