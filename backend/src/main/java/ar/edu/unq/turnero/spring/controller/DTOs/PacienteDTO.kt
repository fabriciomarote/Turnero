package ar.edu.unq.turnero.spring.controller.DTOs

import ar.edu.unq.turnero.modelo.Usuario

class PacienteDTO(
    var id: Long?,
    var nombreYApellido: String?,
    var dni: Long?,
    var email: String?,
    var telefono: Long?) {

    companion object {
        fun desdeModelo(usuario: Usuario) =
            PacienteDTO(
                id = usuario.id,
                nombreYApellido = usuario.nombreYApellido,
                dni = usuario.dni,
                email = usuario.email,
                telefono = usuario.telefono,
            )
    }

    fun aModelo(): Usuario {
        val usuario = Usuario()
        usuario.id = this.id
        usuario.nombreYApellido = this.nombreYApellido!!
        usuario.dni = this.dni!!
        usuario.email = this.email!!
        usuario.telefono = this.telefono
        return usuario
    }

}