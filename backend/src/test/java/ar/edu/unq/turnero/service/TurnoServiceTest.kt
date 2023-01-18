package ar.edu.unq.turnero.service
import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.Turno
import ar.edu.unq.turnero.modelo.Usuario
//import ar.edu.unq.turnero.modelo.exception.ErrorIntegerException
import ar.edu.unq.turnero.modelo.exception.StringVacioException
import ar.edu.unq.turnero.persistence.*
import ar.edu.unq.turnero.service.impl.*
import org.junit.Assert
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class TurnoServiceTest {

    lateinit var turnoService: TurnoService
    lateinit var hospitalService: HospitalService
    lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var turnoDAO: TurnoDAO
    @Autowired
    lateinit var hospitalDAO: HospitalDAO
    @Autowired
    lateinit var usuarioDAO: UsuarioDAO

    lateinit var evitaPueblo: Hospital
    lateinit var elCruce: Hospital

    lateinit var turno1Evita: Turno
    lateinit var turno2Evita: Turno
    lateinit var turno3Evita: Turno
    lateinit var turno1Wilde: Turno
    lateinit var turno2Wilde: Turno
    lateinit var turno1Iriarte: Turno
    lateinit var turno2Iriarte: Turno
    lateinit var turno3Iriarte: Turno

    var pediatria: Especialidad = Especialidad.PEDIATRIA
    var urologia: Especialidad = Especialidad.UROLOGIA
    var traumatologia: Especialidad = Especialidad.TRAUMATOLOGIA
    var nefrologia: Especialidad = Especialidad.NEFROLOGIA
    var reumatologia: Especialidad = Especialidad.REUMATOLOGIA
    var dermatologia: Especialidad = Especialidad.DERMATOLOGIA

    @BeforeEach
    fun prepare() {
        this.turnoService = TurnoServiceImp(turnoDAO)
        this.hospitalService = HospitalServiceImp(hospitalDAO)
        this.usuarioService = UsuarioServiceImp(usuarioDAO, turnoService)

        evitaPueblo = Hospital("Hospital Evita Pueblo", "Berazategui", "Calle 136 2905", mutableListOf<Especialidad>())
        evitaPueblo.agregarEspecialidad(pediatria)
        evitaPueblo.agregarEspecialidad(traumatologia)
        evitaPueblo.agregarEspecialidad(urologia)
        hospitalService.crear(evitaPueblo)

        elCruce = Hospital(
            "Hospital El Cruce - Nestor Kirchner",
            "Florencio Varela",
            "Av. Calchaquí 5401",
            mutableListOf<Especialidad>()
        )
        elCruce.agregarEspecialidad(pediatria)
        elCruce.agregarEspecialidad(traumatologia)
        elCruce.agregarEspecialidad(urologia)
        elCruce.agregarEspecialidad(nefrologia)
        elCruce.agregarEspecialidad(dermatologia)
        elCruce.agregarEspecialidad(reumatologia)
        hospitalService.crear(elCruce)

        turno1Evita = Turno("20/10/2022 19:00 hs", pediatria, "Julieta Gomez", evitaPueblo)
        turno2Evita = Turno("08/11/2022 17:30 hs", pediatria, "Carlos Ameghino", evitaPueblo)
        turno3Evita = Turno("02/11/2022 11:15 hs", nefrologia, "Juana Molina", elCruce)
        turno1Wilde = Turno("11/12/2022 08.45 hs", traumatologia, "Camila Avendanio", elCruce)

        turnoService.crear(turno1Evita)
        turnoService.crear(turno2Evita)
        turnoService.crear(turno3Evita)
        turnoService.crear(turno1Wilde)
    }

    @Test
    fun `se crea un turno para el hospital Evita Pueblo`() {
        var turnoEvita = Turno("10/10/2022 10:00 hs", reumatologia, "Julieta Gomez", evitaPueblo)
        var turno = turnoService.crear(turnoEvita)

        val turnoId = turno.id!!.toInt()
        var turnoRecuperado = turnoService.recuperar(turnoId)

        Assert.assertEquals(null, turnoRecuperado!!.paciente)
        Assert.assertEquals(reumatologia, turnoRecuperado!!.especialidad)
        Assert.assertEquals("Julieta Gomez", turnoRecuperado!!.especialista)
        Assert.assertEquals("10/10/2022 10:00 hs", turnoRecuperado!!.fechaYHora)
        Assert.assertEquals(null , turnoRecuperado!!.fechaEmitido)
        Assert.assertEquals(evitaPueblo.nombre, turnoRecuperado!!.hospital!!.nombre)

        Assert.assertEquals(turnoEvita, turnoRecuperado)
    }

    @Test
    fun `no se puede crear un turno para el hospital Evita Pueblo porque no tiene una fecha asignada`() {
        var turnoEvita = Turno("", reumatologia, "Julieta Gomez", evitaPueblo)
        try {
            turnoService.crear(turnoEvita)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals("El string no puede ser vacío.", e.message)
        }
    }

    @Test
    fun `no se puede crear un turno para el hospital Evita Pueblo porque no tiene una especialidad asignada`() {
        var turnoEvita = Turno("10/10/2022 10:00 hs", null, "Julieta Gomez", evitaPueblo)
        try {
            turnoService.crear(turnoEvita)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals("El string no puede ser vacío.", e.message)
        }
    }

    @Test
    fun `no se puede crear un turno para el hospital Evita Pueblo porque no tiene un especialista asignado`() {
        var turnoEvita = Turno("10/10/2022 10:00 hs", reumatologia, "", evitaPueblo)
        try {
            turnoService.crear(turnoEvita)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals("El string no puede ser vacío.", e.message)
        }
    }

    @Test
    fun `no se puede crear un turno para el hospital Evita Pueblo porque no tiene un hospital asignado`() {
        var turnoEvita = Turno("", reumatologia, "Julieta Gomez", null)
        try {
            turnoService.crear(turnoEvita)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals("El string no puede ser vacío.", e.message)
        }
    }
/*
    @Test
    fun `se actualiza un turno para el paciente Jorge Perez`() {
        var turnoEvita = Turno("10/10/2022 10:00 hs", reumatologia, "Julieta Gomez", evitaPueblo)
        var turno = turnoService.crear(turnoEvita)
        var turnoId = turno.id!!.toInt()

        var turnoRecuperado = turnoService.recuperar(turnoId)

        Assert.assertEquals("", turnoRecuperado!!.paciente!!.nombreYApellido)
        Assert.assertEquals(null, turnoRecuperado!!.paciente!!.dni)
        Assert.assertEquals(null, turnoRecuperado!!.paciente!!.telefono)
        Assert.assertEquals("", turnoRecuperado!!.paciente!!.email)
        Assert.assertEquals("10/10/2022 10:00 hs", turnoRecuperado!!.fechaYHora)
        Assert.assertEquals(reumatologia, turnoRecuperado!!.especialidad)
        Assert.assertEquals("Julieta Gomez", turnoRecuperado!!.especialista)
        Assert.assertEquals(evitaPueblo.nombre, turnoRecuperado!!.hospital!!.nombre)

        turnoEvita.cambiarNombrePaciente("Jorge Perez")
        turnoEvita.cambiarDniPaciente(22345678)
        turnoEvita.cambiarTelefonoPaciente(1123456789)
        turnoEvita.cambiarEmailPaciente("jorgePerez@gmail.com")
        turnoService.actualizar(turnoEvita)

        turnoRecuperado = turnoService.recuperar(turnoId)

        Assert.assertEquals("Jorge Perez", turnoRecuperado!!.nombreYApellidoPaciente)
        Assert.assertEquals(22345678.toLong(), turnoRecuperado!!.dniPaciente)
        Assert.assertEquals(1123456789.toLong(), turnoRecuperado!!.telefonoPaciente)
        Assert.assertEquals("jorgePerez@gmail.com", turnoRecuperado!!.emailPaciente)
    }

    @Test
    fun `no se puede actualizar un turno porque el nombre y apellido del paciente es vacio`() {
        var turnoEvita = Turno("10/10/2022 10:00 hs", reumatologia, "Julieta Gomez", evitaPueblo)
        var turno = turnoService.crear(turnoEvita)
        var turnoId = turno.id!!.toInt()

        var turnoRecuperado = turnoService.recuperar(turnoId)

        Assert.assertEquals("", turnoRecuperado!!.nombreYApellidoPaciente)
        try {
            turnoEvita.cambiarNombrePaciente("")
            turnoService.actualizar(turnoEvita)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals("El string no puede ser vacío.", e.message)
        }
    }

    @Test
    fun `no se puede actualizar un turno porque el email del paciente es vacio`() {
        var turnoEvita = Turno("10/10/2022 10:00 hs", reumatologia, "Julieta Gomez", evitaPueblo)
        var turno = turnoService.crear(turnoEvita)
        var turnoId = turno.id!!.toInt()

        var turnoRecuperado = turnoService.recuperar(turnoId)

        Assert.assertEquals("", turnoRecuperado!!.nombreYApellidoPaciente)
        try {
            turnoEvita.cambiarEmailPaciente("")
            turnoService.actualizar(turnoEvita)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals("El string no puede ser vacío.", e.message)
        }
    }
*/
    /*
    @Test
    fun `no se puede actualizar un turno porque el dni pasado no cumple con la validacion`() {
        var turnoEvita = Turno("10/10/2022     10:00 hs", reumatologia, "Julieta Gomez", evitaPueblo)
        var turno = turnoService.crear(turnoEvita)
        var turnoId = turno.id!!.toInt()
        var turnoRecuperado = turnoService.recuperar(turnoId)
        Assert.assertEquals(null, turnoRecuperado!!.dniPaciente)
        try {
            turnoEvita.cambiarDniPaciente(0)
            turnoService.actualizar(turnoEvita)
            Assertions.fail("Expected a ErrorIntegerException to be thrown")
        } catch (e: ErrorIntegerException) {
            Assertions.assertEquals("El numero pasado no puede ser menor a los digitos del atributo.", e.message)
        }
    }
     */

    @Test
    fun seRecuperanLosTurnosDelHospitalEvitaEnPediatria() {
        var turnos = turnoService.recuperarTurnosDisponiblesPorHospitalYEspecialidad(evitaPueblo!!.id!!.toInt(), "pediatria")

        Assertions.assertEquals(2, turnos.size)
    }

    @Test
    fun unTurnoSeAsignaAUsuarioDeFormaCorrecta(){
        var user = Usuario("Candela Aguayo",42473021, "caandelaAguayo@yahoo.com", 1124456734, "12345678")
        usuarioService.crear(user)

        user.sacarTurno(turno1Evita)
        user.sacarTurno(turno2Evita)
        user.sacarTurno(turno1Wilde)
        turnoService.actualizar(turno1Evita)
        turnoService.actualizar(turno2Evita)
        turnoService.actualizar(turno1Wilde)

        var turno1 = turnoService.recuperar(turno1Evita.id!!.toInt())
        var turno2 = turnoService.recuperar(turno2Evita.id!!.toInt())
        var turno3 = turnoService.recuperar(turno1Wilde.id!!.toInt())

        //Assertions.assertEquals(3, user.turnosAsignados.size)
        Assertions.assertEquals(user.id, turno1!!.paciente!!.id)
        Assertions.assertEquals(user.id, turno2!!.paciente!!.id)
        Assertions.assertEquals(user.id, turno3!!.paciente!!.id)
    }

    @Test
    fun seBorraAlUsuarioDeUnTurnoAsignadoCorrectamente() {
        var user = Usuario("Candela Aguayo", 32023021, "cndelaAguayo@yahoo.com", 1124456734, "12345678")
        usuarioService.crear(user)

        user.sacarTurno(turno1Evita)
        user.sacarTurno(turno2Evita)
        user.sacarTurno(turno1Wilde)
        turnoService.actualizar(turno1Evita)
        turnoService.actualizar(turno2Evita)
        turnoService.actualizar(turno1Wilde)

        var turno1 = turnoService.recuperar(turno1Evita.id!!.toInt())
        var turno2 = turnoService.recuperar(turno2Evita.id!!.toInt())
        var turno3 = turnoService.recuperar(turno1Wilde.id!!.toInt())

        //Assertions.assertEquals(3, user.turnosAsignados.size)
        Assertions.assertEquals(user.id, turno1!!.paciente!!.id)
        Assertions.assertEquals(user.id, turno2!!.paciente!!.id)
        Assertions.assertEquals(user.id, turno3!!.paciente!!.id)

        turnoService.borrarUsuarioDeTodosSusTurnos(user.id!!.toInt())

        var turnoRecuperado1 = turnoService.recuperar(turno1Evita.id!!.toInt())
        var turnoRecuperado2 = turnoService.recuperar(turno2Evita.id!!.toInt())
        var turnoRecuperado3 = turnoService.recuperar(turno1Wilde.id!!.toInt())

        Assertions.assertNull(turnoRecuperado1!!.paciente)
        Assertions.assertNull(turnoRecuperado2!!.paciente)
        Assertions.assertNull(turnoRecuperado3!!.paciente)
    }

    @AfterEach
    fun cleanUp(){
        turnoService.clear()
        hospitalService.clear()
        usuarioService.clear()
    }
}