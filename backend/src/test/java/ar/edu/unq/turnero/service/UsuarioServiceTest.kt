package ar.edu.unq.turnero.service

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.Turno
import ar.edu.unq.turnero.modelo.Usuario
import ar.edu.unq.turnero.modelo.exception.DniInvalidoException
import ar.edu.unq.turnero.modelo.exception.PasswordVacioException
import ar.edu.unq.turnero.modelo.exception.StringVacioException
import ar.edu.unq.turnero.persistence.HospitalDAO
import ar.edu.unq.turnero.persistence.TurnoDAO
import ar.edu.unq.turnero.persistence.UsuarioDAO
import ar.edu.unq.turnero.service.impl.HospitalServiceImp
import ar.edu.unq.turnero.service.impl.TurnoServiceImp
import ar.edu.unq.turnero.service.impl.UsuarioServiceImp
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
class UsuarioServiceTest {

    lateinit var usuarioService: UsuarioService
    lateinit var hospitalService: HospitalService
    lateinit var turnoService: TurnoService

    @Autowired
    lateinit var usuarioDAO: UsuarioDAO
    @Autowired
    lateinit var hospitalDAO: HospitalDAO
    @Autowired
    lateinit var turnoDAO: TurnoDAO

    @BeforeEach
    fun prepare() {
        turnoService = TurnoServiceImp(turnoDAO)
        usuarioService = UsuarioServiceImp(usuarioDAO, turnoService)
        hospitalService = HospitalServiceImp(hospitalDAO)
    }

    @Test
    fun seCreaUnUsuarioValido() {
        var user = Usuario("Candela Aguayo", 42073821, "candelaAguayoo@yahoo.com",
            24456734, "12345678")
        usuarioService.crear(user)

        Assertions.assertNotNull(user.id)
    }

    @Test
    fun seCreaUnUsuarioInvalidoPorFaltaDeContrasenia() {
        var user = Usuario("Candela Aguayo", 20456734, "candelaAguayoo@yahoo.com",
            42043821, "")
        try {
            usuarioService.crear(user)
        } catch (e: PasswordVacioException) {
            Assertions.assertEquals("Debe ingresar una contraseña.", e.message)
        }
    }

    @Test
    fun seRecuperaUnUsuarioDeFormaCorrecta() {
        var user = Usuario("Candela Aguayo", 42073821, "candelaAguayoo@yahoo.com",
            24456734, "12345678")
        usuarioService.crear(user)

        var userId: Long? = user.id
        var userCreado: Usuario? = usuarioService.recuperar(userId!!.toInt())

        Assertions.assertNotNull(user.equals(userCreado))
    }

    @Test
    fun seIntentaRecuperaUnUsuarioQueNoExiste() {
        var user = Usuario("Candela Aguayo",  42073821, "candelaAguayo@yahoo.com",
            24456734, "12345678")

        var userId: Long? = user.id

        try {
            var userCreado: Usuario? = usuarioService.recuperar(userId!!.toInt())
        } catch (e: RuntimeException) { }
    }

    @Test
    fun seRecuperanTodosLosUsuariosDeFormaCorrecta() {
        var user1 = Usuario("Candela Aguayo",24456734, "candelaAguayo@yahoo.com",
            42073821, "12345678")
        var user2: Usuario = Usuario("Marcos Galante",13456734, "marcosGalante@gmail.com",
            42073821, "45678912")
        var user3: Usuario = Usuario("Ximena Jida", 33456734, "ximeJida@hotmail.com",
            42073821, "78912345")

        usuarioService.crear(user1)
        usuarioService.crear(user2)
        usuarioService.crear(user3)

        var usuarios = usuarioService.recuperarTodos()

        Assertions.assertEquals(3, usuarios.size)
    }

    @Test
    fun userSacaTurnoDeFormaCorrecta(){
        var evitaPueblo = Hospital("Hospital Evita Pueblo", "Berazategui", "Calle 136 2905", mutableListOf<Especialidad>())
        var pediatria: Especialidad = Especialidad.PEDIATRIA
        var turnoEvita = Turno("20/10/2022 19:00 hs", pediatria, "Julieta Gomez", evitaPueblo)
        hospitalService.crear(evitaPueblo)
        turnoService.crear(turnoEvita)

        var user = Usuario("Candela Aguayo", 42073821, "candelaaAguayo@yahoo.com", 1124456734, "12345678")
        usuarioService.crear(user)

        turnoEvita.asignarAPaciente(user)
        var turnoActualizado = turnoService.actualizar(turnoEvita)

        var pacienteDelTurno = turnoActualizado!!.paciente

        //Assertions.assertEquals(1, usuarioActualizado.turnosAsignados.size)
        Assertions.assertEquals(user.id, pacienteDelTurno!!.id)
    }

    @Test
    fun seEliminaUnUsarioCorrectamente() {
        var user = Usuario("Candela Aguayo",27456734, "candelaAguayo@yahoo.com",
            42073821, "12345678")
        usuarioService.crear(user)
        var usuarioId = user.id!!.toInt()

        Assertions.assertNotNull(usuarioService.recuperar(usuarioId))

        usuarioService.eliminar(usuarioId)

        try {
            usuarioService.recuperar(usuarioId)
        } catch (e: RuntimeException) {
            Assertions.assertEquals("El id [${usuarioId}] no existe.", e.message)
        }
    }

    @Test
    fun seEliminaUnUsarioConTurnosCorrectamente() {
        var evitaPueblo = Hospital("Hospital Evita Pueblo", "Berazategui", "Calle 136 2905", mutableListOf<Especialidad>())
        var pediatria: Especialidad = Especialidad.PEDIATRIA
        var turnoEvita1 = Turno("20/10/2022 19:00 hs", pediatria, "Julieta Gomez", evitaPueblo)
        var turnoEvita2 = Turno("25/10/2022 15:00 hs", pediatria, "Julieta Gomez", evitaPueblo)
        hospitalService.crear(evitaPueblo)
        turnoService.crear(turnoEvita1)
        turnoService.crear(turnoEvita2)

        var user = Usuario("Candela Aguayo",27406734, "candelaAguayo@yahoo.com",
            1142073821, "12345678")
        usuarioService.crear(user)

        turnoEvita1.asignarAPaciente(user)
        turnoEvita2.asignarAPaciente(user)

        turnoService.actualizar(turnoEvita1)
        turnoService.actualizar(turnoEvita2)

        var usuarioId = user.id!!.toInt()

        usuarioService.eliminar(usuarioId)

        try {
            usuarioService.recuperar(usuarioId)
        } catch (e: RuntimeException) {
            Assertions.assertEquals("El id [${usuarioId}] no existe.", e.message)
        }
    }

    @AfterEach
    fun cleanUp(){
        turnoService.clear()
        hospitalService.clear()
        usuarioService.clear()
    }

}