package ar.edu.unq.turnero.persistence

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.Turno
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
interface TurnoDAO : CrudRepository<Turno, Long> {

    fun findAllBy() : List<Turno>

    fun findByHospitalIdAndEspecialidadAndPacienteIsNull(idHospital: Long, especialidad: Especialidad) : List<Turno>

    @Query("SELECT * FROM turno t WHERE t.hospital_id = ?1", nativeQuery= true)
    fun turnosDeHospital(id: Long) : List<Turno>

    @Query("SELECT * FROM turno t WHERE t.paciente_id = ?1", nativeQuery= true)
    fun turnosAsignadosAUsuarioPor(id: Long) : List<Turno>

    @Modifying
    @Query( "UPDATE turno t SET t.paciente_id = null WHERE t.paciente_id = ?1", nativeQuery= true )
    fun borrarUsuarioDeTodosSusTurnos(usuarioId: Long)
}
