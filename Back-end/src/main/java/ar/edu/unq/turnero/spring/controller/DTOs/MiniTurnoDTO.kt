package ar.edu.unq.turnero.spring.controller.DTOs

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Turno
import ar.edu.unq.turnero.modelo.exception.EspecialidadVacioException

class MiniTurnoDTO(
    var id: Long?,
    var fechaYHora: String?,
    var fechaEmitido: String?,
    var especialidad: String?,
    var especialista:  String?,
    var hospitalNombre: String?,
) {

    companion object {

        fun desdeModelo(turno: Turno) =
            MiniTurnoDTO(
                id = turno.id,

                fechaYHora = turno.fechaYHora,
                fechaEmitido = turno.fechaEmitido,
                especialidad = turno.especialidad.toString().toLowerCase(),
                especialista = turno.especialista,
                hospitalNombre = turno.hospital!!.nombre)
    }

    fun aModelo(): Turno {
        val turno = Turno()
        turno.id = this.id
        turno.fechaYHora = this.fechaYHora!!
        turno.fechaEmitido = this.fechaEmitido!!
        turno.especialidad = toEnum(this.especialidad)
        turno.especialista = this.especialista
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

