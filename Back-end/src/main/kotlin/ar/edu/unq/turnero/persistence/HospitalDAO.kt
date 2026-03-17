package ar.edu.unq.turnero.persistence

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Hospital
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HospitalDAO : MongoRepository<Hospital, String> {

    fun findAllByOrderByNombreAsc(): List<Hospital>
    fun findByNombreContaining(nombre: String): List<Hospital>
    fun findByMunicipioContaining(municipio: String): List<Hospital>
    fun findByEspecialidadesContaining(especialidad: Especialidad): List<Hospital>
}
