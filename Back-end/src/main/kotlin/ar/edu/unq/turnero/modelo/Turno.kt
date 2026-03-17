package ar.edu.unq.turnero.modelo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Document(collection = "turno")
class Turno() {

    @Id
    var id: String? = null
    var fechaYHora: String? = null
    var fechaEmitido: String? = null
    var especialidad: Especialidad? = null
    var especialista: String? = null

    @DBRef(lazy = true)
    var hospital: Hospital? = null

    @DBRef(lazy = true)
    var paciente: Usuario? = null

    constructor(fecha: String, especialidad: Especialidad?, especialista: String, hospital: Hospital?) : this() {
        this.fechaYHora = fecha
        this.especialidad = especialidad
        this.especialista = especialista
        this.hospital = hospital
    }

    fun cambiarFechaEmitido() {
        this.fechaEmitido = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a"))
    }

    fun asignarAPaciente(paciente: Usuario) {
        this.paciente = paciente
        cambiarFechaEmitido()
    }

    fun desasignarAPaciente() {
        this.paciente = null
        fechaEmitido = null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Turno
        if (id != other.id) return false
        if (fechaYHora != other.fechaYHora) return false
        if (especialidad != other.especialidad) return false
        if (especialista != other.especialista) return false
        if (hospital != other.hospital) return false
        return true
    }
}
