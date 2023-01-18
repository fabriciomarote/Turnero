package ar.edu.unq.turnero.spring.controller.DTOs

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Turno
import ar.edu.unq.turnero.modelo.exception.EspecialidadVacioException

class TurnoDTO(
    var id: Long?,
    var fechaYHora: String?,
    var fechaEmitido: String?,
    var especialidad: String?,
    var especialista:  String?,
    var hospital: MiniHospitalDTO?,
    var paciente: PacienteDTO?,
) {

    companion object {

        fun desdeModelo(turno: Turno) =
            TurnoDTO(
                id = turno.id,
                fechaYHora = turno.fechaYHora,
                fechaEmitido = turno.fechaEmitido,
                especialidad = turno.especialidad.toString().toLowerCase(),
                especialista = turno.especialista,
                hospital = MiniHospitalDTO(turno.hospital!!.id, turno.hospital!!.nombre, turno.hospital!!.direccion, turno.hospital!!.especialidades.map { especialidad -> especialidad.toString().toLowerCase()}
                    .toCollection(HashSet()).toList(),),
                paciente = PacienteDTO(turno.paciente?.id, turno.paciente?.nombreYApellido, turno.paciente?.dni, turno.paciente?.email, turno.paciente?.telefono))
    }

    fun aModelo(): Turno {
        val turno = Turno()
        turno.id = this.id
        turno.fechaYHora = this.fechaYHora!!
        turno.fechaEmitido = this.fechaEmitido!!
        turno.especialidad = toEnum(this.especialidad)
        turno.especialista = this.especialista
        turno.hospital = this.hospital!!.aModelo()
        turno.paciente = this.paciente?.aModelo()
        return turno
    }

    fun aModelo(turno: Turno): Turno {
        turno.paciente = this.paciente?.aModelo()
        return turno
    }

    private fun toEnum(especialidad: String?): Especialidad {
        var nuevaEspecialidad: Especialidad
        when(especialidad) {
            "Pediatria" -> nuevaEspecialidad = Especialidad.PEDIATRIA
            "Oncologia" -> nuevaEspecialidad = Especialidad.ONCOLOGIA
            "Traumatologia" -> nuevaEspecialidad = Especialidad.TRAUMATOLOGIA
            "Urologia" -> nuevaEspecialidad = Especialidad.UROLOGIA
            "Oftalmologia" -> nuevaEspecialidad = Especialidad.OFTALMOLOGIA
            "Kinesiologia" -> nuevaEspecialidad = Especialidad.KINESIOLOGIA
            "Cardiologia" -> nuevaEspecialidad = Especialidad.CARDIOLOGIA
            "Nefrologia" -> nuevaEspecialidad = Especialidad.NEFROLOGIA
            "Reumatologia" -> nuevaEspecialidad = Especialidad.REUMATOLOGIA
            "Dermatologia" -> nuevaEspecialidad = Especialidad.DERMATOLOGIA
            else -> {
                throw EspecialidadVacioException()
            }
        }
        return nuevaEspecialidad
    }
}

