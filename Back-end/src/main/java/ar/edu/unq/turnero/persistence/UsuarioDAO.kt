package ar.edu.unq.turnero.persistence

import ar.edu.unq.turnero.modelo.Usuario
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
interface UsuarioDAO : CrudRepository<Usuario, Long>  {

    fun findAllByOrderByNombreYApellidoAsc(): List<Usuario>

    fun findByEmail(email: String) : Usuario?

    fun findByToken(token: String) : Usuario

    fun findByDni(dni: Long) : Usuario?
    @Modifying
    @Query( "UPDATE turno t SET t.paciente_id = null WHERE t.paciente_id = ?1", nativeQuery= true )
    fun borrarUsuarioDeTodosSusTurnos(usuarioId: Long)
}