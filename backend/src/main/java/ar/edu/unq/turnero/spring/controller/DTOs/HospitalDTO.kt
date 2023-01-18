package ar.edu.unq.turnero.spring.controller.DTOs

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.exception.EspecialidadVacioException

class HospitalDTO(
    var id: Long?,
    var nombre:String?,
    var municipio: String?,
    var direccion: String?,
    var especialidades: List<String>,
    //var turnos: List<MiniTurnoDTO>
) {

    companion object {
        fun desdeModelo(hospital: Hospital) =
            HospitalDTO(
                id = hospital.id,
                nombre = hospital.nombre,
                municipio = hospital.municipio,
                direccion = hospital.direccion,
                especialidades = hospital.especialidades
                    .map { especialidad -> especialidad.toString().toLowerCase()}
                    .toCollection(HashSet()).toList(),
                //turnos = hospital.turnos
                //    .map { turno -> MiniTurnoDTO.desdeModelo(turno)}.toMutableList(),
            )
    }

    fun aModelo(): Hospital {
        val hospital = Hospital()
        hospital.id = this.id
        hospital.nombre = this.nombre!!
        hospital.municipio = this.municipio!!
        hospital.direccion = this.direccion!!
        hospital.especialidades = transformEspecialidades(this.especialidades!!)
        //hospital.turnos = this.turnos
        //    ?.map { MiniTurnoDTO  ->  MiniTurnoDTO.aModelo()}.toMutableList()
        return hospital
    }

    fun aModelo(hospital : Hospital): Hospital {
        hospital.nombre = this.nombre!!
        hospital.municipio = this.municipio!!
        hospital.direccion = this.direccion!!
        return hospital
    }

    private fun transformEspecialidades( especialidades: List<String> ) : MutableList<Especialidad> {
        var nuevasEspecialidades : MutableList<Especialidad> = mutableListOf()
         especialidades.forEach { especialidad -> nuevasEspecialidades += toEnum(especialidad) }

        return nuevasEspecialidades
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

