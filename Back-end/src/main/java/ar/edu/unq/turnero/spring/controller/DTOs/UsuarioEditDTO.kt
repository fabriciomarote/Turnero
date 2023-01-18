package ar.edu.unq.turnero.spring.controller.DTOs

import ar.edu.unq.turnero.modelo.Usuario

class UsuarioEditDTO(
    var id: Long?,
    var nombreYApellido: String?,
    var image: String?,
    var email: String?,
    var telefono: Long?,
    var password: String?) {

    companion object {
        fun desdeModelo(usuario: Usuario) =
            UsuarioEditDTO(
                id = usuario.id,
                nombreYApellido = usuario.nombreYApellido,
                image = usuario.image,
                email = usuario.email,
                telefono = usuario.telefono,
                password = usuario.password,
            )
    }

    fun aModelo(usuario: Usuario): Usuario {
        usuario.nombreYApellido = this.nombreYApellido!!
        usuario.image = this.image!!
        usuario.email = this.email!!
        usuario.telefono = this.telefono
        usuario.password = this.password!!
        return usuario
    }

}