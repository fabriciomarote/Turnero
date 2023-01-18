package ar.edu.unq.turnero.spring.controller.DTOs

import ar.edu.unq.turnero.modelo.Turno
import ar.edu.unq.turnero.modelo.Usuario

class UsuarioDTO(
    var id: Long?,
    var nombreYApellido: String?,
    var image: String?,
    var dni: Long?,
    var email: String?,
    var telefono: Long?,
    var password: String?,
    var token: String?) {

    companion object {
        fun desdeModelo(usuario: Usuario) =
            UsuarioDTO(
                id = usuario.id,
                nombreYApellido = usuario.nombreYApellido,
                image = usuario.image,
                dni = usuario.dni,
                email = usuario.email,
                telefono = usuario.telefono,
                password = usuario.password,
                token = usuario.token
            )
    }

    fun aModelo(): Usuario {
        val usuario = Usuario()
        usuario.id = this.id
        usuario.nombreYApellido = this.nombreYApellido!!
        usuario.image = this.image!!
        usuario.dni = this.dni!!
        usuario.email = this.email!!
        usuario.telefono = this.telefono
        usuario.password = this.password!!
        usuario.token = this.token
        return usuario
    }

    fun aModelo(usuario: Usuario): Usuario {
        usuario.nombreYApellido = this.nombreYApellido!!
        usuario.email = this.email!!
        usuario.telefono = this.telefono!!
        usuario.image = this.image!!
        usuario.password = this.password!!
        return usuario
    }

}