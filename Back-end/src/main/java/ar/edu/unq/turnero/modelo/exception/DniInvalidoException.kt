package ar.edu.unq.turnero.modelo.exception

class DniInvalidoException: Exception() {

    override val message: String?
        get() = "Debe ingresar un DNI valido."

    companion object {
        private val serialVersionUID = 1L
    }
}