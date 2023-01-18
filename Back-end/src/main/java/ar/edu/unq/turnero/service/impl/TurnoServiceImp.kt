package ar.edu.unq.turnero.service.impl

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.Turno
import ar.edu.unq.turnero.modelo.exception.EspecialidadVacioException
import ar.edu.unq.turnero.modelo.exception.StringVacioException
import ar.edu.unq.turnero.persistence.TurnoDAO
import ar.edu.unq.turnero.service.TurnoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
open class TurnoServiceImp(
    @Autowired
    private val turnoDAO: TurnoDAO
) : TurnoService {

    override fun crear(turno: Turno): Turno {
        this.validarBase(turno)
        return turnoDAO.save(turno)
    }

    private fun validarBase(turno : Turno) {
        if(turno.fechaYHora == "" || turno.especialidad == null ||
            turno.especialista == "" || turno.hospital == null) {
            throw StringVacioException()
        }
    }

    private fun validarActualizacion(turno : Turno) {
        if(turno.paciente?.nombreYApellido == "" || turno.paciente?.dni == null ||
            turno.paciente?.telefono == null || turno.paciente?.email == "") {
            throw StringVacioException()
        }
    }

    override fun actualizar(turno: Turno): Turno {
        this.validarActualizacion(turno)
        turno.cambiarFechaEmitido()
        return this.crear(turno)
    }

    override fun recuperar(turnoId:Int): Turno {
        val turno = turnoDAO.findByIdOrNull(turnoId.toLong())
        if (turno == null) {
            throw RuntimeException("El id [${turnoId}] no existe.")
        }
        return turno
    }

    override fun recuperarTodos():List<Turno> {
        return turnoDAO.findAllBy()
    }

    override fun eliminar(turnoId:Int) {
        val turno = this.recuperar(turnoId)
        turno.desasignarAPaciente()
        //turnoDAO.deleteById(turnoId.toLong())
    }

    override fun recuperarTurnosDisponiblesPorHospitalYEspecialidad(idDeHospital: Int, especialidad: String): List<Turno> {
        return turnoDAO.findByHospitalIdAndEspecialidadAndPacienteIsNull(idDeHospital.toLong(), toEnum(especialidad))
    }

    override fun recuperarTurnosPorHospital(idDeHospital: Long): List<Turno> {
        return turnoDAO.turnosDeHospital(idDeHospital)
    }

    override fun recuperarTurnosDe(id: Long): List<Turno> {
          return turnoDAO.turnosAsignadosAUsuarioPor(id)
    }

    override fun borrarUsuarioDeTodosSusTurnos(usuarioId: Int) {
        turnoDAO.borrarUsuarioDeTodosSusTurnos(usuarioId.toLong())
    }

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

    override fun clear() {
        turnoDAO.deleteAll()
    }

}