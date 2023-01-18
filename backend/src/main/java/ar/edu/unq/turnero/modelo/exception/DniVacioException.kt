package ar.edu.unq.turnero.modelo.exception

class DniVacioException : Exception() {

    override val message: String?
        get() = "Debe ingresar un DNI."

    companion object {

        private val serialVersionUID = 1L
    }
}