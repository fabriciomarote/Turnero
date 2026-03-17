package ar.edu.unq.turnero.persistence

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Turno
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TurnoDAO : MongoRepository<Turno, String> {

    fun findByHospital_IdAndEspecialidadAndPacienteIsNull(
        hospitalId: String,
        especialidad: Especialidad
    ): List<Turno>

    fun findByHospital_Id(hospitalId: String): List<Turno>
    fun findByPaciente_Id(pacienteId: String): List<Turno>
}
