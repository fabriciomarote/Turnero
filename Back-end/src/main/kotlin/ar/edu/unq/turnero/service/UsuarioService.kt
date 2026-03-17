package ar.edu.unq.turnero.service

import ar.edu.unq.turnero.modelo.Usuario

interface UsuarioService {
    fun crear(usuario: Usuario): Usuario
    fun actualizar(usuario: Usuario): Usuario
    fun recuperar(usuarioId: String): Usuario?
    fun recuperarUsuario(email: String, password: String): Usuario?
    fun recuperarPorToken(token: String): Usuario?
    fun recuperarTodos(): List<Usuario>
    fun eliminar(usuarioId: String)
    fun clear()
}
