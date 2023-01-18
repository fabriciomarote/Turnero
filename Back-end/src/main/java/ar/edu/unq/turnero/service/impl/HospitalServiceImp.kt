package ar.edu.unq.turnero.service.impl

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.Turno
import ar.edu.unq.turnero.modelo.exception.ErrorSelectionException
import ar.edu.unq.turnero.modelo.exception.EspecialidadVacioException
import ar.edu.unq.turnero.modelo.exception.StringVacioException
import ar.edu.unq.turnero.persistence.HospitalDAO
import ar.edu.unq.turnero.service.HospitalService
import ar.edu.unq.turnero.service.TurnoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class HospitalServiceImp(
    @Autowired
    private val hospitalDAO: HospitalDAO
    ) : HospitalService {

    override fun crear(hospital: Hospital): Hospital {
        this.validarCampos(hospital)
        return hospitalDAO.save(hospital)
    }

    private fun validarCampos(hospital : Hospital) {
        if(hospital.nombre == "" || hospital.direccion == "" || hospital.municipio == "") {
            throw StringVacioException()
        }
    }

    override fun actualizar(hospital: Hospital): Hospital {
        return this.crear(hospital)
    }

    override fun recuperar(hospitalId:Int): Hospital {
        val hospital = hospitalDAO.findByIdOrNull(hospitalId.toLong())
        if (hospital == null) {
            throw RuntimeException("El id [${hospitalId}] no existe.")
        }
        return hospital
    }

    override fun recuperarTodos():List<Hospital> {
        return hospitalDAO.findAllByOrderByNombreAsc()
    }

    // Dado un nombre devuelve una lista de hospitales cuyo nombre contiene el termino de busqueda.
    override fun recuperarPorNombre(nombre: String) : List<Hospital> {
        return hospitalDAO.findByNombreContaining(nombre)
    }

    override fun recuperarPorMunicipio(busqueda: String): List<Hospital> {
        hospitalDAO.findByMunicipioContaining(busqueda)
        return hospitalDAO.findByMunicipioContaining(busqueda)
    }

    override fun recuperarPorEspecialidad(busqueda: String): List<Hospital> {
        return hospitalDAO.findByEspecialidad(busqueda)
    }

    /*override fun recuperarTurnosDisponiblesPorEspecialidad(idDeHospital: Int, especialidad: String): List<Turno> {
        var especialidadClase = this.toEnum(especialidad)
        return hospitalDAO.turnos(idDeHospital.toLong(), especialidadClase)
    }*/

    private fun toEnum(especialidad: String): Especialidad {
        var nuevaEspecialidad: Especialidad
        when(especialidad) {
            "pediatria" -> nuevaEspecialidad = Especialidad.PEDIATRIA
            "oncologia" -> nuevaEspecialidad = Especialidad.ONCOLOGIA
            "traumatologia" -> nuevaEspecialidad = Especialidad.TRAUMATOLOGIA
            "urologia" -> nuevaEspecialidad = Especialidad.UROLOGIA
            "oftalmologia" -> nuevaEspecialidad = Especialidad.OFTALMOLOGIA
            "kinesiologia" -> nuevaEspecialidad = Especialidad.KINESIOLOGIA
            "cardiologia" -> nuevaEspecialidad = Especialidad.CARDIOLOGIA
            "nefrologia" -> nuevaEspecialidad = Especialidad.NEFROLOGIA
            "reumatologia" -> nuevaEspecialidad = Especialidad.REUMATOLOGIA
            "dermatologia" -> nuevaEspecialidad = Especialidad.DERMATOLOGIA
            else -> {
                throw EspecialidadVacioException()
            }
        }
        return nuevaEspecialidad
    }

    override fun recuperarPor(select: String, busqueda: String):List<Hospital>  {
        if (select == "nombre") {
            return this.recuperarPorNombre(busqueda)
        } else if (select == "municipio") {
            return this.recuperarPorMunicipio(busqueda)
        } else if ( select == "especialidad"){
            return this.recuperarPorEspecialidad(busqueda)
        } else {
            throw ErrorSelectionException()
        }
    }

    override fun clear() {
        hospitalDAO.deleteAll()
    }

}