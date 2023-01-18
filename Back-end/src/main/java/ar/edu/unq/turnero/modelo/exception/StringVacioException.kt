package ar.edu.unq.turnero.modelo.exception

class StringVacioException : Exception() {

    override val message: String?
        get() = "El string no puede ser vacío."

    companion object {

        private val serialVersionUID = 1L
    }
}