package ar.edu.unq.turnero.service.impl

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.exception.ErrorSelectionException
import ar.edu.unq.turnero.modelo.exception.EspecialidadVacioException
import ar.edu.unq.turnero.modelo.exception.StringVacioException
import ar.edu.unq.turnero.persistence.HospitalDAO
import ar.edu.unq.turnero.service.HospitalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class HospitalServiceImp(
    @Autowired
    private val hospitalDAO: HospitalDAO
) : HospitalService {

    override fun crear(hospital: Hospital): Hospital {
        validarCampos(hospital)
        return hospitalDAO.save(hospital)
    }

    private fun validarCampos(hospital: Hospital) {
        if (hospital.nombre == "" || hospital.direccion == "" || hospital.municipio == "") {
            throw StringVacioException()
        }
    }

    override fun actualizar(hospital: Hospital): Hospital = crear(hospital)

    override fun recuperar(hospitalId: String): Hospital {
        return hospitalDAO.findById(hospitalId)
            .orElseThrow { RuntimeException("El id [$hospitalId] no existe.") }
    }

    override fun recuperarTodos(): List<Hospital> = hospitalDAO.findAllByOrderByNombreAsc()

    override fun recuperarPorNombre(busqueda: String): List<Hospital> =
        hospitalDAO.findByNombreContaining(busqueda)

    override fun recuperarPorMunicipio(busqueda: String): List<Hospital> =
        hospitalDAO.findByMunicipioContaining(busqueda)

    override fun recuperarPorEspecialidad(busqueda: String): List<Hospital> =
        hospitalDAO.findByEspecialidadesContaining(toEnum(busqueda))

    private fun toEnum(especialidad: String): Especialidad = when (especialidad.lowercase()) {
        "pediatria" -> Especialidad.PEDIATRIA
        "oncologia" -> Especialidad.ONCOLOGIA
        "traumatologia" -> Especialidad.TRAUMATOLOGIA
        "urologia" -> Especialidad.UROLOGIA
        "oftalmologia" -> Especialidad.OFTALMOLOGIA
        "kinesiologia" -> Especialidad.KINESIOLOGIA
        "cardiologia" -> Especialidad.CARDIOLOGIA
        "nefrologia" -> Especialidad.NEFROLOGIA
        "reumatologia" -> Especialidad.REUMATOLOGIA
        "dermatologia" -> Especialidad.DERMATOLOGIA
        else -> throw EspecialidadVacioException()
    }

    override fun recuperarPor(select: String, busqueda: String): List<Hospital> = when (select) {
        "nombre" -> recuperarPorNombre(busqueda)
        "municipio" -> recuperarPorMunicipio(busqueda)
        "especialidad" -> recuperarPorEspecialidad(busqueda)
        else -> throw ErrorSelectionException()
    }

    override fun clear() {
        hospitalDAO.deleteAll()
    }
}
