package ar.edu.unq.turnero.modelo.exception

class NombreYApellidoIncompletoException : Exception() {

    override val message: String?
        get() = "Debe ingresar un nombre y apellido para poder registrarse"

    companion object {
        private val serialVersionUID = 1L
    }
}