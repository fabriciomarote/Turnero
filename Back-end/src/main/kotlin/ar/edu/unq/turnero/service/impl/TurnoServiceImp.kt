package ar.edu.unq.turnero.service.impl

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Turno
import ar.edu.unq.turnero.modelo.exception.EspecialidadVacioException
import ar.edu.unq.turnero.modelo.exception.StringVacioException
import ar.edu.unq.turnero.persistence.TurnoDAO
import ar.edu.unq.turnero.service.TurnoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class TurnoServiceImp(
    @Autowired
    private val turnoDAO: TurnoDAO
) : TurnoService {

    override fun crear(turno: Turno): Turno {
        validarBase(turno)
        return turnoDAO.save(turno)
    }

    private fun validarBase(turno: Turno) {
        if (turno.fechaYHora == "" || turno.especialidad == null ||
            turno.especialista == "" || turno.hospital == null
        ) {
            throw StringVacioException()
        }
    }

    private fun validarActualizacion(turno: Turno) {
        if (turno.paciente?.nombreYApellido == "" || turno.paciente?.dni == null ||
            turno.paciente?.telefono == null || turno.paciente?.email == ""
        ) {
            throw StringVacioException()
        }
    }

    override fun actualizar(turno: Turno): Turno {
        validarActualizacion(turno)
        turno.cambiarFechaEmitido()
        return crear(turno)
    }

    override fun recuperar(turnoId: String): Turno {
        return turnoDAO.findById(turnoId)
            .orElseThrow { RuntimeException("El id [$turnoId] no existe.") }
    }

    override fun recuperarTodos(): List<Turno> = turnoDAO.findAll()

    override fun eliminar(turnoId: String) {
        val turno = recuperar(turnoId)
        turno.desasignarAPaciente()
        turnoDAO.save(turno)
    }

    override fun recuperarTurnosDisponiblesPorHospitalYEspecialidad(
        hospitalId: String,
        especialidad: String
    ): List<Turno> =
        turnoDAO.findByHospital_IdAndEspecialidadAndPacienteIsNull(hospitalId, toEnum(especialidad))

    override fun recuperarTurnosPorHospital(hospitalId: String): List<Turno> =
        turnoDAO.findByHospital_Id(hospitalId)

    override fun recuperarTurnosDe(usuarioId: String): List<Turno> =
        turnoDAO.findByPaciente_Id(usuarioId)

    override fun borrarUsuarioDeTodosSusTurnos(usuarioId: String) {
        turnoDAO.findByPaciente_Id(usuarioId).forEach { turno ->
            turno.desasignarAPaciente()
            turnoDAO.save(turno)
        }
    }

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

    override fun clear() {
        turnoDAO.deleteAll()
    }
}
