package ar.edu.unq.turnero.modelo.exception

class EspecialidadVacioException : Exception() {

    override val message: String?
        get() = "El atributo no puede ser vacío."

    companion object {

        private val serialVersionUID = 1L
    }
}