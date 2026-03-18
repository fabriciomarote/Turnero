package ar.edu.unq.turnero.spring.controller

import ar.edu.unq.turnero.service.TurnoService
import ar.edu.unq.turnero.spring.controller.DTOs.TurnoAsignadoDTO
import ar.edu.unq.turnero.spring.controller.DTOs.TurnoDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/turno")
class TurnoController(private val turnoService: TurnoService) {

    @PostMapping
    fun crear(@RequestBody turno: TurnoDTO) = turnoService.crear(turno.aModelo())

    @PutMapping("/{turnoId}")
    fun actualizar(@PathVariable turnoId: String, @RequestBody turno: TurnoDTO): ResponseEntity<Any> {
        val turnoRecuperado = turnoService.recuperar(turnoId)
        val actualizado = turnoService.actualizar(turno.aModelo(turnoRecuperado))
        return ResponseEntity.ok().body(TurnoDTO.desdeModelo(actualizado))
    }

    @GetMapping("/{turnoId}")
    fun recuperar(@PathVariable turnoId: String) = TurnoDTO.desdeModelo(turnoService.recuperar(turnoId))

    @GetMapping("/usuario/{usuarioId}")
    fun recuperarTurnosDe(@PathVariable usuarioId: String) =
        turnoService.recuperarTurnosDe(usuarioId).map { turno -> TurnoAsignadoDTO.desdeModelo(turno) }

    @GetMapping("/todos")
    fun recuperarTodos() = turnoService.recuperarTodos().map { turno -> TurnoDTO.desdeModelo(turno) }

    @GetMapping("/todos/{hospitalId}")
    fun turnosDeHospital(@PathVariable hospitalId: String) =
        turnoService.recuperarTurnosPorHospital(hospitalId).map { turno -> TurnoDTO.desdeModelo(turno) }

    @GetMapping("/todos/{hospitalId}/{especialidad}")
    fun turnosDisponibles(@PathVariable hospitalId: String, @PathVariable especialidad: String) =
        turnoService.recuperarTurnosDisponiblesPorHospitalYEspecialidad(hospitalId, especialidad).map { turno -> TurnoDTO.desdeModelo(turno) }

    @DeleteMapping("/{turnoId}")
    fun eliminar(@PathVariable turnoId: String) = turnoService.eliminar(turnoId)
}