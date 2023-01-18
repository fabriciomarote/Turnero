package ar.edu.unq.turnero.spring.controller.DTOs

import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.Usuario

class MiniUsuarioDTO(
    var id: Long?,
    var email: String?,
    var password: String?) {

    companion object {
        fun desdeModelo(usuario: Usuario) =
            MiniUsuarioDTO(
                id = usuario.id,
                email = usuario.email,
                password = usuario.password
            )
    }


    fun aModelo(): Usuario {
        val usuario = Usuario()
        usuario.id = this.id
        usuario.email = this.email!!
        usuario.password = this.password!!
        return usuario
    }
}