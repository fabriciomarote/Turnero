package ar.edu.unq.turnero.persistence

import ar.edu.unq.turnero.modelo.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioDAO : MongoRepository<Usuario, String> {

    fun findAllByOrderByNombreYApellidoAsc(): List<Usuario>
    fun findByEmail(email: String): Usuario?
    fun findByToken(token: String): Usuario?
    fun findByDni(dni: Long): Usuario?
}
