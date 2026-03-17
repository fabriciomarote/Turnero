package ar.edu.unq.turnero.service

import ar.edu.unq.turnero.modelo.Turno

interface TurnoService {
    fun crear(turno: Turno): Turno
    fun actualizar(turno: Turno): Turno
    fun recuperar(turnoId: String): Turno
    fun recuperarTodos(): List<Turno>
    fun recuperarTurnosDe(usuarioId: String): List<Turno>
    fun recuperarTurnosPorHospital(hospitalId: String): List<Turno>
    fun recuperarTurnosDisponiblesPorHospitalYEspecialidad(hospitalId: String, especialidad: String): List<Turno>
    fun eliminar(turnoId: String)
    fun clear()
    fun borrarUsuarioDeTodosSusTurnos(usuarioId: String)
}
