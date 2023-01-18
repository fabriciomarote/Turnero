package ar.edu.unq.turnero.spring.controller

import ar.edu.unq.turnero.service.TurnoService
import ar.edu.unq.turnero.spring.controller.DTOs.TurnoAsignadoDTO
import ar.edu.unq.turnero.spring.controller.DTOs.TurnoDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/turno")
class TurnoController(private val turnoService: TurnoService) {

    @PostMapping
    fun crear(@RequestBody turno: TurnoDTO) = turnoService.crear(turno.aModelo())

    @PutMapping("/{turnoId}")
    fun actualizar(@PathVariable turnoId: Long, @RequestBody turno: TurnoDTO) : ResponseEntity<Any>  {
        var turnoRecuperado = turnoService.recuperar(turnoId.toInt())!!
        val turno = turnoService.actualizar(turno.aModelo(turnoRecuperado))
        return ResponseEntity.ok().body(turno)
    }

    @GetMapping("/{turnoId}")
    fun recuperar(@PathVariable turnoId: Long) = TurnoDTO.desdeModelo(turnoService.recuperar(turnoId.toInt())!!)

    @GetMapping("/usuario/{usuarioId}")
    fun recuperarTurnosDe(@PathVariable usuarioId: Long) = turnoService.recuperarTurnosDe(usuarioId).map { turno -> TurnoAsignadoDTO.desdeModelo(turno)  }

    @GetMapping("/todos")
    fun recuperarTodos() = turnoService.recuperarTodos().map { turno -> TurnoDTO.desdeModelo(turno)  }

    @GetMapping("/todos/{hospitalId}")
    fun turnosDeHospital(@PathVariable hospitalId: Long) =
        turnoService.recuperarTurnosPorHospital(hospitalId).map { turno -> TurnoDTO.desdeModelo(turno) }

    @GetMapping("/todos/{hospitalId}/{especialidad}")
    fun turnosDisponibles(@PathVariable hospitalId: Long, @PathVariable especialidad: String) =
        turnoService.recuperarTurnosDisponiblesPorHospitalYEspecialidad(hospitalId.toInt(), especialidad).map { turno -> TurnoDTO.desdeModelo(turno) }

    @DeleteMapping("/{turnoId}")
    fun eliminar(@PathVariable turnoId: Int) = turnoService.eliminar(turnoId)}