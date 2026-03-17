package ar.edu.unq.turnero.modelo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "hospital")
class Hospital() {

    @Id
    var id: String? = null
    var nombre: String? = null
    var municipio: String? = null
    var direccion: String? = null
    var especialidades: MutableList<Especialidad> = mutableListOf()

    constructor(nombre: String, municipio: String, direccion: String, especialidades: MutableList<Especialidad>) : this() {
        this.nombre = nombre
        this.municipio = municipio
        this.direccion = direccion
        this.especialidades = especialidades
    }

    fun agregarEspecialidad(nuevaEspecialidad: Especialidad) {
        this.especialidades.add(nuevaEspecialidad)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Hospital
        if (id != other.id) return false
        if (nombre != other.nombre) return false
        if (municipio != other.municipio) return false
        if (direccion != other.direccion) return false
        return true
    }
}
