package ar.edu.unq.turnero.spring.controller

import ar.edu.unq.turnero.service.HospitalService
import ar.edu.unq.turnero.spring.controller.DTOs.MiniHospitalDTO
import ar.edu.unq.turnero.spring.controller.DTOs.HospitalDTO
import ar.edu.unq.turnero.spring.controller.DTOs.TurnoDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hospital")
class HospitalController(private val hospitalService: HospitalService) {

    @PostMapping
    fun crear(@RequestBody hospital: HospitalDTO) = hospitalService.crear(hospital.aModelo())

    @PutMapping("/{hospitalId}")
    fun actualizar(@PathVariable hospitalId: String, @RequestBody hospitalDTO: HospitalDTO): ResponseEntity<Any> {
        val hospitalRecuperado = hospitalService.recuperar(hospitalId)
        val hospital = hospitalService.actualizar(hospitalDTO.aModelo(hospitalRecuperado))
        return ResponseEntity.ok().body(HospitalDTO.desdeModelo(hospital))
    }

    @GetMapping("/{hospitalId}")
    fun recuperar(@PathVariable hospitalId: String) = HospitalDTO.desdeModelo(hospitalService.recuperar(hospitalId))

    @GetMapping("/search")
    @ResponseBody
    fun recuperarPor(@RequestParam q: String, value: String) = hospitalService.recuperarPor(value, q).map { hospital -> MiniHospitalDTO.desdeModelo(hospital)  }

    @GetMapping("")
    fun recuperarTodos() = hospitalService.recuperarTodos().map { hospital -> HospitalDTO.desdeModelo(hospital)  }

    //@GetMapping("/{hospitalId}/turnos/{especialidad}")
    //fun turnosDisponibles(@PathVariable hospitalId: Long, @PathVariable especialidad: String) =
    //    hospitalService.recuperarTurnosDisponiblesPorEspecialidad(hospitalId.toInt(), especialidad).map { turno -> TurnoDTO.desdeModelo(turno) }
}