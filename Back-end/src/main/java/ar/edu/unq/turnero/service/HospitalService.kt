package ar.edu.unq.turnero.service

import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.Turno

interface HospitalService {
    fun crear(hospital:Hospital): Hospital
    fun actualizar(hospital: Hospital): Hospital
    fun recuperar(hospitalId:Int): Hospital?
    fun recuperarTodos(): List<Hospital>
    fun recuperarPor(select: String, busqueda: String): List<Hospital>
    fun recuperarPorNombre(busqueda: String): List<Hospital>
    fun recuperarPorMunicipio(busqueda: String): List<Hospital>
    fun recuperarPorEspecialidad(busqueda: String): List<Hospital>
    //fun recuperarTurnosDisponiblesPorEspecialidad(idDeHospital: Int, especialidad: String) : List<Turno>
    fun clear()

}