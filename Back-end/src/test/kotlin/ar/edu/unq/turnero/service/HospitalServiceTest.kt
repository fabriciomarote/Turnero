package ar.edu.unq.turnero.service

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.Turno
import ar.edu.unq.turnero.modelo.exception.ErrorSelectionException
import ar.edu.unq.turnero.modelo.exception.StringVacioException
import ar.edu.unq.turnero.persistence.*
import ar.edu.unq.turnero.service.impl.*
import org.junit.jupiter.api.Assertions
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
class HospitalServiceTest {

    lateinit var service: HospitalService
    lateinit var turnoService: TurnoService
    lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var hospitalDAO: HospitalDAO
    @Autowired
    lateinit var turnoDAO: TurnoDAO
    @Autowired
    lateinit var usuarioDAO: UsuarioDAO

    lateinit var evitaPueblo: Hospital
    lateinit var garrahan: Hospital
    lateinit var elCruce: Hospital
    lateinit var iriarte: Hospital
    lateinit var italianoCABA: Hospital

    var pediatria: Especialidad = Especialidad.PEDIATRIA
    var urologia: Especialidad = Especialidad.UROLOGIA
    var traumatologia: Especialidad = Especialidad.TRAUMATOLOGIA
    var nefrologia: Especialidad = Especialidad.NEFROLOGIA
    var reumatologia: Especialidad = Especialidad.REUMATOLOGIA
    var dermatologia: Especialidad = Especialidad.DERMATOLOGIA

    lateinit var turno1Evita: Turno
    lateinit var turno3Evita: Turno

    @BeforeEach
    fun prepare() {
        turnoService = TurnoServiceImp(turnoDAO)
        usuarioService = UsuarioServiceImp(usuarioDAO, turnoService)
        this.service = HospitalServiceImp(hospitalDAO)

        evitaPueblo = Hospital(
            "Hospital Evita Pueblo",
            "Berazategui",
            "Calle 136 2905",
            mutableListOf()
        )
        evitaPueblo.agregarEspecialidad(pediatria)
        evitaPueblo.agregarEspecialidad(traumatologia)
        evitaPueblo.agregarEspecialidad(urologia)
        service.crear(evitaPueblo)

        turno1Evita = Turno("20/10/2022 19:00 hs", pediatria, "Julieta Gomez", evitaPueblo)
        turno3Evita = Turno("02/11/2022 11:15 hs", nefrologia, "Juana Molina", evitaPueblo)

        elCruce = Hospital(
            "Hospital El Cruce - Nestor Kirchner",
            "Florencio Varela",
            "Av. Calchaquí 5401",
            mutableListOf()
        )
        elCruce.agregarEspecialidad(pediatria)
        elCruce.agregarEspecialidad(traumatologia)
        elCruce.agregarEspecialidad(urologia)
        elCruce.agregarEspecialidad(nefrologia)
        elCruce.agregarEspecialidad(dermatologia)
        elCruce.agregarEspecialidad(reumatologia)
        service.crear(elCruce)

        iriarte = Hospital(
            "Hospital Quilmes - Isidoro Iriarte",
            "Quilmes",
            "Allison Bell N°770",
            mutableListOf()
        )
        iriarte.agregarEspecialidad(dermatologia)
        iriarte.agregarEspecialidad(urologia)
        iriarte.agregarEspecialidad(traumatologia)
        iriarte.agregarEspecialidad(nefrologia)
        service.crear(iriarte)

        garrahan = Hospital(
            "Hospital Garrahan",
            "CABA",
            "Pichincha 1890",
            mutableListOf()
        )
        garrahan.agregarEspecialidad(dermatologia)
        garrahan.agregarEspecialidad(urologia)
        garrahan.agregarEspecialidad(traumatologia)
        garrahan.agregarEspecialidad(nefrologia)
        service.crear(garrahan)

        italianoCABA = Hospital(
            "Hospital Italiano de Buenos Aires",
            "CABA",
            "Av. Juan Bautista Alberdi 439",
            mutableListOf()
        )
        italianoCABA.agregarEspecialidad(pediatria)
        italianoCABA.agregarEspecialidad(traumatologia)
        italianoCABA.agregarEspecialidad(urologia)
        service.crear(italianoCABA)

    }

    @Test
    fun seCreaHospitalWildeTest() {
        val wilde = Hospital(
            "Hospital Zonal General de Agudos “Dr. E. Wilde”",
            "Quilmes",
            "Calle Falsa 123",
            mutableListOf()
        )
        wilde.agregarEspecialidad(traumatologia)
        wilde.agregarEspecialidad(nefrologia)
        val hospital = service.crear(wilde)

        val hospitalId = hospital.id!!
        val wildeRecuperado = service.recuperar(hospitalId)

        Assertions.assertEquals("Hospital Zonal General de Agudos “Dr. E. Wilde”", wildeRecuperado.nombre)
        Assertions.assertEquals("Quilmes", wildeRecuperado.municipio)
        Assertions.assertEquals("Calle Falsa 123", wildeRecuperado.direccion)
        Assertions.assertEquals(2, wildeRecuperado.especialidades.size)
        Assertions.assertEquals(wilde, wildeRecuperado)
    }

    @Test
    fun noSePuedeCrearUnHospitalSinNombreTest() {
        val hospital = Hospital("", "municipio", "direccion", mutableListOf())
        try {
            service.crear(hospital)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals("El string no puede ser vacío.", e.message)
        }
    }

    @Test
    fun noSePuedeCrearUnHospitalSinMunicipioTest() {
        val wilde = Hospital(
            "Hospital Zonal General de Agudos “Dr. E. Wilde”",
            "",
            "Calle Falsa 123",
            mutableListOf()
        )

        try {
            service.crear(wilde)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals(e.message, "El string no puede ser vacío.")
        }
    }

    @Test
    fun noSePuedeCrearUnHospitalSinDireccionTest() {
        val wilde = Hospital(
            "Hospital Zonal General de Agudos “Dr. E. Wilde”",
            "Quilmes",
            "",
            mutableListOf()
        )
        try {
            service.crear(wilde)
            Assertions.fail("Expected a StringVacioException to be thrown")
        } catch (e: StringVacioException) {
            Assertions.assertEquals("El string no puede ser vacío.", e.message)
        }
    }

    @Test
    fun seRecuperaUnHospitalPorNombreNestorTest() {
        val hospitales = service.recuperarPorNombre("nestor")

        Assertions.assertEquals(1, hospitales.size)
        Assertions.assertTrue(hospitales.contains(elCruce))
    }

    @Test
    fun seRecuperaUnHospitalPorElMunicipioQuilmesTest() {
        val hospitales = service.recuperarPorMunicipio("quilmes")

        Assertions.assertEquals(1, hospitales.size)
        Assertions.assertTrue(hospitales.contains(iriarte))
    }

    @Test
    fun seRecuperanHospitalesPorEspecialidadTest() {
        val hospitales = service.recuperarPorEspecialidad("pediatria")

        Assertions.assertEquals(3, hospitales.size)
        Assertions.assertTrue(hospitales.contains(elCruce))
    }

    @Test
    fun seRecuperaPorLaEspecialidadPediatria() {
        val hospitales = service.recuperarPor("especialidad", "pediat")

        Assertions.assertTrue(hospitales.contains(elCruce))
        Assertions.assertTrue(elCruce.especialidades.contains(pediatria)) // no funciona por query
    }

    @Test
    fun noSePuedeRecuperaPorQueLaSeleccionNoExiste() {
        try {
            service.recuperarPor("berazategui", "oncologia")
            Assertions.fail("Expected a ErrorSelectionException to be thrown")
        } catch (e: ErrorSelectionException) {
            Assertions.assertEquals(e.message, "El valor pasado del selector no es correcto.")
        }
    }

    @AfterEach
    fun cleanUp(){
        //turnoService.clear()
        //service.clear()
    }
}