package ar.edu.unq.turnero.spring.controller.DTOs

import ar.edu.unq.turnero.modelo.Usuario

class UsuarioLogueadoDTO(
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
            UsuarioLogueadoDTO(
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

}