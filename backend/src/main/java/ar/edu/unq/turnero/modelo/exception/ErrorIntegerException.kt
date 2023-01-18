package ar.edu.unq.turnero.modelo.exception

class ErrorIntegerException : Exception() {

    override val message: String?
        get() = "El numero pasado no puede ser menor a los digitos del atributo." //modificar por parametro

    companion object {

        private val serialVersionUID = 1L
    }
}

