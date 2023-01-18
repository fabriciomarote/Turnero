package ar.edu.unq.turnero.service

import ar.edu.unq.turnero.modelo.Especialidad
import ar.edu.unq.turnero.modelo.Hospital
import ar.edu.unq.turnero.modelo.Turno
import ar.edu.unq.turnero.modelo.Usuario
import ar.edu.unq.turnero.persistence.HospitalDAO
import ar.edu.unq.turnero.persistence.TurnoDAO
import ar.edu.unq.turnero.persistence.UsuarioDAO
import ar.edu.unq.turnero.service.impl.HospitalServiceImp
import ar.edu.unq.turnero.service.impl.TurnoServiceImp
import ar.edu.unq.turnero.service.impl.UsuarioServiceImp
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AplicationData {
    lateinit var turnoService: TurnoService
    lateinit var service: HospitalService
    lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var hospitalDAO : HospitalDAO
    @Autowired
    lateinit var turnoDAO : TurnoDAO
    @Autowired
    lateinit var usuarioDAO : UsuarioDAO

    lateinit var evitaPueblo: Hospital
    lateinit var elCruce: Hospital
    lateinit var iriarte: Hospital
    lateinit var miPueblo: Hospital
    lateinit var interzonal: Hospital
    lateinit var garrahan: Hospital
    lateinit var italianoCABA: Hospital
    lateinit var pirovano: Hospital
    lateinit var sanMartin: Hospital
    lateinit var sanRoque: Hospital
    lateinit var santamarina: Hospital
    lateinit var bocalandro: Hospital
    lateinit var italianoLP: Hospital
    lateinit var sanJuan: Hospital
    lateinit var arturoOnativia: Hospital
    lateinit var presidentePeron: Hospital
    lateinit var ramonSarda: Hospital
    lateinit var argerich: Hospital
    lateinit var sanCayetano: Hospital
    lateinit var joseCPaz: Hospital


    var pediatria: Especialidad = Especialidad.PEDIATRIA
    var kinesiologia: Especialidad = Especialidad.KINESIOLOGIA
    var cardiologia: Especialidad = Especialidad.CARDIOLOGIA
    var urologia: Especialidad = Especialidad.UROLOGIA
    var traumatologia: Especialidad = Especialidad.TRAUMATOLOGIA
    var nefrologia: Especialidad = Especialidad.NEFROLOGIA
    var reumatologia: Especialidad = Especialidad.REUMATOLOGIA
    var oftalmologia: Especialidad = Especialidad.OFTALMOLOGIA
    var dermatologia: Especialidad = Especialidad.DERMATOLOGIA
    var oncologia: Especialidad = Especialidad.ONCOLOGIA

    var user1: Usuario = Usuario("Candela Aguayo", 42073811, "candelaaguayo@yahoo.com",
        1124456734, "12345678")
    var user2: Usuario = Usuario("Marcos Galante", 42073822, "marcosgalante@gmail.com",
        1113456734, "45678912")
    var user3: Usuario = Usuario("Ximena Jida", 42043821, "ximejida@hotmail.com",
        1133456734, "78912345")

    @BeforeEach
    fun prepare() {
        this.turnoService = TurnoServiceImp(turnoDAO)
        this.service = HospitalServiceImp(hospitalDAO)
        this.usuarioService = UsuarioServiceImp(usuarioDAO, turnoService)

        usuarioService.crear(user1)
        usuarioService.crear(user2)
        usuarioService.crear(user3)

        evitaPueblo = Hospital(
            "Hospital Evita Pueblo",
            "Berazategui",
            "Calle 136 2905",
            mutableListOf<Especialidad>()
        )
        evitaPueblo.agregarEspecialidad(pediatria)
        evitaPueblo.agregarEspecialidad(traumatologia)
        evitaPueblo.agregarEspecialidad(urologia)
        service.crear(evitaPueblo)

        var turnoEvita1 = Turno("27/12/2022 17:45 hs", pediatria, "Leonardo Sanchez", evitaPueblo)
        var turnoEvita2 = Turno("31/11/2022 12:00 hs", traumatologia, "Manuel Rodriguez", evitaPueblo)
        var turnoEvita3 = Turno("05/12/2022 09:15 hs", urologia, "Pablo Tobias", evitaPueblo)
        var turnoEvita4 = Turno("20/12/2022 10:00 hs", pediatria, "Leonardo Sanchez", evitaPueblo)
        var turnoEvita5 = Turno("27/12/2022 08:15 hs", pediatria, "Leonardo Sanchez", evitaPueblo)
        var turnoEvita6 = Turno("03/01/2023 09:15 hs", traumatologia, "Veronica Manini", evitaPueblo)
        var turnoEvita7 = Turno("03/01/2023 09:45 hs", traumatologia, "Veronica Manini", evitaPueblo)
        var turnoEvita8 = Turno("29/12/2022 14:30 hs", urologia, "Carlos Saenz", evitaPueblo)
        var turnoEvita9 = Turno("29/12/2022 15:00 hs", urologia, "Carlos Saenz", evitaPueblo)

        turnoService.crear(turnoEvita1)
        turnoService.crear(turnoEvita2)
        turnoService.crear(turnoEvita3)
        turnoService.crear(turnoEvita4)
        turnoService.crear(turnoEvita5)
        turnoService.crear(turnoEvita6)
        turnoService.crear(turnoEvita7)
        turnoService.crear(turnoEvita8)
        turnoService.crear(turnoEvita9)

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
        service.crear(elCruce)

        var turnoElCruce1 = Turno("04/01/2023     17:45 hs", reumatologia, "Carla Esposito", elCruce)
        var turnoElCruce2 = Turno("05/01/2023     17:45 hs", reumatologia, "Carla Esposito", elCruce)
        var turnoElCruce3 = Turno("15/12/2022     16:00 hs", urologia, "Julian Carrasco", elCruce)
        var turnoElCruce4 = Turno("16/12/2022     16:00 hs", urologia, "Julian Carrasco", elCruce)
        var turnoElCruce5 = Turno("23/12/2022     08:00 hs", traumatologia, "Camila Gutierrez", elCruce)
        var turnoElCruce6 = Turno("23/12/2022     08:30 hs", traumatologia, "Camila Gutierrez", elCruce)
        var turnoElCruce7 = Turno("23/12/2022     09:00 hs", traumatologia, "Camila Gutierrez", elCruce)
        var turnoElCruce8 = Turno("04/01/2023     10:00 hs", nefrologia, "Iris Amato", elCruce)
        var turnoElCruce9 = Turno("04/01/2023     10:30 hs", nefrologia, "Iris Amato", elCruce)
        var turnoElCruce10 = Turno("03/01/2023     09:00 hs", dermatologia, "Luis Dandrea", elCruce)
        var turnoElCruce11 = Turno("03/01/2023     09:30 hs", dermatologia, "Luis Dandrea", elCruce)
        var turnoElCruce12 = Turno("03/01/2023     10:00 hs", dermatologia, "Luis Dandrea", elCruce)

        turnoService.crear(turnoElCruce1)
        turnoService.crear(turnoElCruce2)
        turnoService.crear(turnoElCruce3)
        turnoService.crear(turnoElCruce4)
        turnoService.crear(turnoElCruce5)
        turnoService.crear(turnoElCruce6)
        turnoService.crear(turnoElCruce7)
        turnoService.crear(turnoElCruce8)
        turnoService.crear(turnoElCruce9)
        turnoService.crear(turnoElCruce10)
        turnoService.crear(turnoElCruce11)
        turnoService.crear(turnoElCruce12)

        iriarte = Hospital(
            "Hospital Quilmes - Isidoro Iriarte",
            "Quilmes",
            "Allison Bell N°770",
            mutableListOf<Especialidad>()
        )
        iriarte.agregarEspecialidad(dermatologia)
        iriarte.agregarEspecialidad(urologia)
        iriarte.agregarEspecialidad(traumatologia)
        iriarte.agregarEspecialidad(nefrologia)
        service.crear(iriarte)

        var turnoIriarte1 = Turno("15/12/2022     16:00 hs", urologia, "Sandra Vicunia", iriarte)
        var turnoIriarte2 = Turno("16/12/2022     16:00 hs", urologia, "Sandra Vicunia", iriarte)
        var turnoIriarte3 = Turno("23/12/2022     08:00 hs", traumatologia, "Mateo Viña", iriarte)
        var turnoIriarte4 = Turno("23/12/2022     08:30 hs", traumatologia, "Mateo Viña", iriarte)
        var turnoIriarte5 = Turno("23/12/2022     09:00 hs", traumatologia, "Mateo Viña", iriarte)
        var turnoIriarte6 = Turno("04/01/2023     10:00 hs", nefrologia, "Karina Paso", iriarte)
        var turnoIriarte7 = Turno("04/01/2023     10:30 hs", nefrologia, "Karina Paso", iriarte)
        var turnoIriarte8 = Turno("04/01/2023     11:00 hs", nefrologia, "Karina Paso", iriarte)
        var turnoIriarte9 = Turno("03/01/2023     09:30 hs", dermatologia, "Pedro Dandrea", iriarte)
        var turnoIriarte10 = Turno("03/01/2023     10:00 hs", dermatologia, "Pedro Dandrea", iriarte)

        turnoService.crear(turnoIriarte1)
        turnoService.crear(turnoIriarte2)
        turnoService.crear(turnoIriarte3)
        turnoService.crear(turnoIriarte4)
        turnoService.crear(turnoIriarte5)
        turnoService.crear(turnoIriarte6)
        turnoService.crear(turnoIriarte7)
        turnoService.crear(turnoIriarte8)
        turnoService.crear(turnoIriarte9)
        turnoService.crear(turnoIriarte10)

        miPueblo = Hospital(
            "Hospital Mi Pueblo",
            "Florencio Varela",
            "Florida 202",
            mutableListOf<Especialidad>()
        )
        miPueblo.agregarEspecialidad(pediatria)
        miPueblo.agregarEspecialidad(traumatologia)
        miPueblo.agregarEspecialidad(urologia)
        service.crear(miPueblo)

        var turnoMiPueblo1 = Turno("15/12/2022     16:00 hs", urologia, "Hector Lavagna", miPueblo)
        var turnoMiPueblo2 = Turno("16/12/2022     16:00 hs", urologia, "Hector Lavagna", miPueblo)
        var turnoMiPueblo3 = Turno("23/12/2022     08:00 hs", traumatologia, "Maria Castro", miPueblo)
        var turnoMiPueblo4 = Turno("23/12/2022     08:30 hs", traumatologia, "Maria Castro", miPueblo)
        var turnoMiPueblo5 = Turno("23/12/2022     09:00 hs", traumatologia, "Maria Castro", miPueblo)
        var turnoMiPueblo6 = Turno("04/01/2023     10:00 hs", pediatria, "Patricia Marin", miPueblo)
        var turnoMiPueblo7 = Turno("04/01/2023     10:30 hs", pediatria, "Patricia Marin", miPueblo)
        var turnoMiPueblo8 = Turno("04/01/2023     11:00 hs", pediatria, "Patricia Marin", miPueblo)

        turnoService.crear(turnoMiPueblo1)
        turnoService.crear(turnoMiPueblo2)
        turnoService.crear(turnoMiPueblo3)
        turnoService.crear(turnoMiPueblo4)
        turnoService.crear(turnoMiPueblo5)
        turnoService.crear(turnoMiPueblo6)
        turnoService.crear(turnoMiPueblo7)
        turnoService.crear(turnoMiPueblo8)

        interzonal = Hospital(
            "Hospital Interzonal de Agudos Evita",
            "Lanus",
            "Diego Armando Maradona 1910",
            mutableListOf<Especialidad>()
        )
        interzonal.agregarEspecialidad(kinesiologia)
        interzonal.agregarEspecialidad(traumatologia)
        interzonal.agregarEspecialidad(cardiologia)
        interzonal.agregarEspecialidad(pediatria)
        interzonal.agregarEspecialidad(dermatologia)
        service.crear(interzonal)

        var turnoInterzonal1 = Turno("15/12/2022     16:00 hs", kinesiologia, "Marina Vista", interzonal)
        var turnoInterzonal2 = Turno("16/12/2022     16:00 hs", kinesiologia, "Marina Vista", interzonal)
        var turnoInterzonal3 = Turno("23/12/2022     08:00 hs", traumatologia, "Natalia Guido", interzonal)
        var turnoInterzonal4 = Turno("23/12/2022     08:30 hs", traumatologia, "Natalia Guido", interzonal)
        var turnoInterzonal5 = Turno("23/12/2022     09:00 hs", traumatologia, "Natalia Guido", interzonal)
        var turnoInterzonal6 = Turno("03/01/2023     10:00 hs", cardiologia, "Pedro Pascal", interzonal)
        var turnoInterzonal7 = Turno("03/01/2023     10:30 hs", cardiologia, "Pedro Pascal", interzonal)
        var turnoInterzonal8 = Turno("03/01/2023     11:00 hs", cardiologia, "Pedro Pascal", interzonal)
        var turnoInterzonal9 = Turno("08/01/2023     09:30 hs", dermatologia, "Hugo del Carril", interzonal)
        var turnoInterzonal10 = Turno("08/01/2023     10:00 hs", dermatologia, "Hugo del Carril", interzonal)

        turnoService.crear(turnoInterzonal1)
        turnoService.crear(turnoInterzonal2)
        turnoService.crear(turnoInterzonal3)
        turnoService.crear(turnoInterzonal4)
        turnoService.crear(turnoInterzonal5)
        turnoService.crear(turnoInterzonal6)
        turnoService.crear(turnoInterzonal7)
        turnoService.crear(turnoInterzonal8)
        turnoService.crear(turnoInterzonal9)
        turnoService.crear(turnoInterzonal10)

        garrahan = Hospital(
            "Hospital Garrahan",
            "CABA",
            "Pichincha 1890",
            mutableListOf<Especialidad>()
        )
        garrahan.agregarEspecialidad(dermatologia)
        garrahan.agregarEspecialidad(oncologia)
        garrahan.agregarEspecialidad(traumatologia)
        garrahan.agregarEspecialidad(cardiologia)
        service.crear(garrahan)

        var turnoGarrahan1 = Turno("26/12/2022     16:00 hs", oncologia, "Isabel Conte", garrahan)
        var turnoGarrahan2 = Turno("26/12/2022     16:30 hs", oncologia, "Isabel Conte", garrahan)
        var turnoGarrahan3 = Turno("26/12/2022     17:00 hs", oncologia, "Isabel Conte", garrahan)
        var turnoGarrahan4 = Turno("23/12/2022     08:30 hs", traumatologia, "Franco Martinez", garrahan)
        var turnoGarrahan5 = Turno("23/12/2022     09:00 hs", traumatologia, "Franco Martinez", garrahan)
        var turnoGarrahan6 = Turno("04/01/2023     10:00 hs", cardiologia, "Maria Belen Cabalieri", garrahan)
        var turnoGarrahan7 = Turno("04/01/2023     10:30 hs", cardiologia, "Maria Belen Cabalieri", garrahan)
        var turnoGarrahan8 = Turno("04/01/2023     11:00 hs", cardiologia, "Maria Belen Cabalieri", garrahan)
        var turnoGarrahan9 = Turno("08/01/2023     09:30 hs", dermatologia, "Joaquin Leal", garrahan)
        var turnoGarrahan10 = Turno("08/01/2023     10:00 hs", dermatologia, "Joaquin Leal", garrahan)

        turnoService.crear(turnoGarrahan1)
        turnoService.crear(turnoGarrahan2)
        turnoService.crear(turnoGarrahan3)
        turnoService.crear(turnoGarrahan4)
        turnoService.crear(turnoGarrahan5)
        turnoService.crear(turnoGarrahan6)
        turnoService.crear(turnoGarrahan7)
        turnoService.crear(turnoGarrahan8)
        turnoService.crear(turnoGarrahan9)
        turnoService.crear(turnoGarrahan10)

        italianoCABA = Hospital(
            "Hospital Italiano de Buenos Aires",
            "CABA",
            "Av. Juan Bautista Alberdi 439",
            mutableListOf<Especialidad>()
        )
        italianoCABA.agregarEspecialidad(pediatria)
        italianoCABA.agregarEspecialidad(traumatologia)
        italianoCABA.agregarEspecialidad(urologia)
        service.crear(italianoCABA)

        var turnoItalianoCABA1 = Turno("26/12/2022     16:00 hs", urologia, "Lucas Gomez", italianoCABA)
        var turnoItalianoCABA2 = Turno("26/12/2022     16:30 hs", urologia, "Lucas Gomez", italianoCABA)
        var turnoItalianoCABA3 = Turno("26/12/2022     17:00 hs", urologia, "Lucas Gomez", italianoCABA)
        var turnoItalianoCABA4 = Turno("23/12/2022     08:30 hs", traumatologia, "Fernanda Davila", italianoCABA)
        var turnoItalianoCABA5 = Turno("23/12/2022     09:00 hs", traumatologia, "Fernanda Davila", italianoCABA)
        var turnoItalianoCABA6 = Turno("04/01/2023     10:00 hs", pediatria, "Hernan Iurisevich", italianoCABA)
        var turnoItalianoCABA7 = Turno("04/01/2023     10:30 hs", pediatria, "Hernan Iurisevich", italianoCABA)

        turnoService.crear(turnoItalianoCABA1)
        turnoService.crear(turnoItalianoCABA2)
        turnoService.crear(turnoItalianoCABA3)
        turnoService.crear(turnoItalianoCABA4)
        turnoService.crear(turnoItalianoCABA5)
        turnoService.crear(turnoItalianoCABA6)
        turnoService.crear(turnoItalianoCABA7)

        pirovano = Hospital(
            "Hospital Pirovano",
            "CABA",
            "Av. Monroe 3555",
            mutableListOf<Especialidad>()
        )
        pirovano.agregarEspecialidad(pediatria)
        pirovano.agregarEspecialidad(traumatologia)
        pirovano.agregarEspecialidad(urologia)
        pirovano.agregarEspecialidad(oncologia)
        pirovano.agregarEspecialidad(dermatologia)
        pirovano.agregarEspecialidad(reumatologia)
        service.crear(pirovano)

        var turnoPirovano1 = Turno("26/12/2022     16:00 hs", oncologia, "Candela Bercovich", pirovano)
        var turnoPirovano2 = Turno("26/12/2022     16:30 hs", oncologia, "Candela Bercovich", pirovano)
        var turnoPirovano3 = Turno("26/12/2022     17:00 hs", oncologia, "Candela Bercovich", pirovano)
        var turnoPirovano4 = Turno("23/12/2022     08:30 hs", traumatologia, "Claudia Peruggino", pirovano)
        var turnoPirovano5 = Turno("23/12/2022     09:00 hs", traumatologia, "Claudia Peruggino", pirovano)
        var turnoPirovano6 = Turno("04/01/2023     10:00 hs", reumatologia, "Diego Taibo", pirovano)
        var turnoPirovano7 = Turno("04/01/2023     10:30 hs", reumatologia, "Diego Taibo", pirovano)
        var turnoPirovano8 = Turno("04/01/2023     11:00 hs", reumatologia, "Diego Taibo", pirovano)

        turnoService.crear(turnoPirovano1)
        turnoService.crear(turnoPirovano2)
        turnoService.crear(turnoPirovano3)
        turnoService.crear(turnoPirovano4)
        turnoService.crear(turnoPirovano5)
        turnoService.crear(turnoPirovano6)
        turnoService.crear(turnoPirovano7)
        turnoService.crear(turnoPirovano8)

        sanMartin = Hospital(
            "Hospital San Martin",
            "La Plata",
            "Calle 1 y 70,1900",
            mutableListOf<Especialidad>()
        )
        sanMartin.agregarEspecialidad(dermatologia)
        sanMartin.agregarEspecialidad(cardiologia)
        sanMartin.agregarEspecialidad(traumatologia)
        sanMartin.agregarEspecialidad(pediatria)
        service.crear(sanMartin)

        var turnoSanMartin1 = Turno("15/12/2022     16:00 hs", dermatologia, "Federico Luna", sanMartin)
        var turnoSanMartin2 = Turno("16/12/2022     16:00 hs", dermatologia, "Federico Luna", sanMartin)
        var turnoSanMartin3 = Turno("23/12/2022     08:00 hs", traumatologia, "Maria Cosnard", sanMartin)
        var turnoSanMartin4 = Turno("23/12/2022     08:30 hs", traumatologia, "Maria Cosnard", sanMartin)
        var turnoSanMartin5 = Turno("23/12/2022     09:00 hs", traumatologia, "Maria Cosnard", sanMartin)
        var turnoSanMartin6 = Turno("04/01/2023     10:00 hs", pediatria, "Quimey Ramos", sanMartin)
        var turnoSanMartin7 = Turno("04/01/2023     10:30 hs", pediatria, "Quimey Ramos", sanMartin)
        var turnoSanMartin8 = Turno("03/01/2023     13:00 hs", cardiologia, "Julio Avendaño", sanMartin)

        turnoService.crear(turnoSanMartin1)
        turnoService.crear(turnoSanMartin2)
        turnoService.crear(turnoSanMartin3)
        turnoService.crear(turnoSanMartin4)
        turnoService.crear(turnoSanMartin5)
        turnoService.crear(turnoSanMartin6)
        turnoService.crear(turnoSanMartin7)
        turnoService.crear(turnoSanMartin8)

        sanRoque = Hospital(
            "Hospital San Roque",
            "La Plata",
            "Calle 508, 1897, Gonnet",
            mutableListOf<Especialidad>()
        )
        sanRoque.agregarEspecialidad(pediatria)
        sanRoque.agregarEspecialidad(traumatologia)
        sanRoque.agregarEspecialidad(kinesiologia)
        service.crear(sanRoque)

        var turnoSanRoque1 = Turno("26/12/2022     16:00 hs", pediatria, "Alejandro Mamani", sanRoque)
        var turnoSanRoque2 = Turno("26/12/2022     16:30 hs", pediatria, "Alejandro Mamani", sanRoque)
        var turnoSanRoque3 = Turno("23/12/2022     14:00 hs", traumatologia, "Diana Sacayan", sanRoque)
        var turnoSanRoque4 = Turno("23/12/2022     14:30 hs", traumatologia, "Diana Sacayan", sanRoque)
        var turnoSanRoque5 = Turno("23/12/2022     15:00 hs", traumatologia, "Diana Sacayan", sanRoque)
        var turnoSanRoque6 = Turno("04/01/2023     10:00 hs", kinesiologia, "Victor Kesler", sanRoque)
        var turnoSanRoque7 = Turno("04/01/2023     10:30 hs", kinesiologia, "Victor Kesler", sanRoque)

        turnoService.crear(turnoSanRoque1)
        turnoService.crear(turnoSanRoque2)
        turnoService.crear(turnoSanRoque3)
        turnoService.crear(turnoSanRoque4)
        turnoService.crear(turnoSanRoque5)
        turnoService.crear(turnoSanRoque6)
        turnoService.crear(turnoSanRoque7)

        santamarina = Hospital(
            "Hospital Municipal Santa Marina",
            "Esteban Echeverria",
            "Gral. Alvear 350, Monte Grande",
            mutableListOf<Especialidad>()
        )
        santamarina.agregarEspecialidad(pediatria)
        santamarina.agregarEspecialidad(oncologia)
        santamarina.agregarEspecialidad(dermatologia)
        santamarina.agregarEspecialidad(reumatologia)
        service.crear(santamarina)

        var turnoSantamarina1 = Turno("15/12/2022     16:00 hs", dermatologia, "Julieta Federici", santamarina)
        var turnoSantamarina2 = Turno("15/12/2022     16:30 hs", dermatologia, "Julieta Federici", santamarina)
        var turnoSantamarina3 = Turno("23/12/2022     08:00 hs", reumatologia, "Hilda Gomez", santamarina)
        var turnoSantamarina4 = Turno("23/12/2022     08:30 hs", reumatologia, "Hilda Gomez", santamarina)
        var turnoSantamarina5 = Turno("23/12/2022     09:00 hs", reumatologia, "Hilda Gomez", santamarina)
        var turnoSantamarina6 = Turno("04/01/2023     10:00 hs", pediatria, "Lohana Berkins", santamarina)
        var turnoSantamarina7 = Turno("04/01/2023     10:30 hs", pediatria, "Lohana Berkins", santamarina)
        var turnoSantamarina8 = Turno("07/01/2023     15:00 hs", oncologia, "Tomas Salcedo", santamarina)
        var turnoSantamarina9 = Turno("07/01/2023     15:30 hs", oncologia, "Tomas Salcedo", santamarina)
        var turnoSantamarina10 = Turno("07/01/2023     16:00 hs", oncologia, "Tomas Salcedo", santamarina)
        var turnoSantamarina11 = Turno("07/01/2023     16:30 hs", oncologia, "Tomas Salcedo", santamarina)

        turnoService.crear(turnoSantamarina1)
        turnoService.crear(turnoSantamarina2)
        turnoService.crear(turnoSantamarina3)
        turnoService.crear(turnoSantamarina4)
        turnoService.crear(turnoSantamarina5)
        turnoService.crear(turnoSantamarina6)
        turnoService.crear(turnoSantamarina7)
        turnoService.crear(turnoSantamarina8)
        turnoService.crear(turnoSantamarina9)
        turnoService.crear(turnoSantamarina10)
        turnoService.crear(turnoSantamarina11)

        bocalandro = Hospital(
            "Hospital Dr. Carlos Bocalandro",
            "Tres de Febrero",
            "RP8 Nº9100 Km. 20,5, Loma Hermosa",
            mutableListOf<Especialidad>()
        )
        bocalandro.agregarEspecialidad(dermatologia)
        bocalandro.agregarEspecialidad(urologia)
        bocalandro.agregarEspecialidad(traumatologia)
        bocalandro.agregarEspecialidad(nefrologia)
        service.crear(bocalandro)

        var turnoBocalandro1 = Turno("04/01/2023     17:45 hs", dermatologia, "Tamara Tenenbaum", bocalandro)
        var turnoBocalandro2 = Turno("05/01/2023     17:45 hs", dermatologia, "Tamara Tenenbaum", bocalandro)
        var turnoBocalandro3 = Turno("23/12/2022     08:00 hs", traumatologia, "Yvone de Lucia", bocalandro)
        var turnoBocalandro4 = Turno("23/12/2022     08:30 hs", traumatologia, "Yvone de Lucia", bocalandro)
        var turnoBocalandro5 = Turno("23/12/2022     09:00 hs", traumatologia, "Yvone de Lucia", bocalandro)
        var turnoBocalandro6 = Turno("04/01/2023     10:00 hs", nefrologia, "Joel Villagran", bocalandro)
        var turnoBocalandro7 = Turno("04/01/2023     10:30 hs", nefrologia, "Joel Villagran", bocalandro)

        turnoService.crear(turnoBocalandro1)
        turnoService.crear(turnoBocalandro2)
        turnoService.crear(turnoBocalandro3)
        turnoService.crear(turnoBocalandro4)
        turnoService.crear(turnoBocalandro5)
        turnoService.crear(turnoBocalandro6)
        turnoService.crear(turnoBocalandro7)

        italianoLP = Hospital(
            "Hospital Italiano",
            "La Plata",
            "Av. 51",
            mutableListOf<Especialidad>()
        )
        italianoLP.agregarEspecialidad(pediatria)
        italianoLP.agregarEspecialidad(cardiologia)
        italianoLP.agregarEspecialidad(urologia)
        service.crear(italianoLP)

        var turnoItalianoLP1 = Turno("26/12/2022     16:00 hs", urologia, "Osvaldo Yutay", italianoLP)
        var turnoItalianoLP2 = Turno("26/12/2022     16:30 hs", urologia, "Osvaldo Yutay", italianoLP)
        var turnoItalianoLP3 = Turno("26/12/2022     17:00 hs", urologia, "Osvaldo Yutay", italianoLP)
        var turnoItalianoLP4 = Turno("23/12/2022     08:30 hs", cardiologia, "Cristina Sobisch", italianoLP)
        var turnoItalianoLP5 = Turno("23/12/2022     09:00 hs", cardiologia, "Cristina Sobisch", italianoLP)
        var turnoItalianoLP6 = Turno("23/12/2022     10:00 hs", cardiologia, "Cristina Sobisch", italianoLP)
        var turnoItalianoLP7 = Turno("04/01/2023     10:30 hs", pediatria, "Carmen Hernandez", italianoLP)

        turnoService.crear(turnoItalianoLP1)
        turnoService.crear(turnoItalianoLP2)
        turnoService.crear(turnoItalianoLP3)
        turnoService.crear(turnoItalianoLP4)
        turnoService.crear(turnoItalianoLP5)
        turnoService.crear(turnoItalianoLP6)
        turnoService.crear(turnoItalianoLP7)

        sanJuan = Hospital(
            "Hospital San Juan de Dios",
            "CABA",
            "Santa Rosa 1355",
            mutableListOf<Especialidad>()
        )
        sanJuan.agregarEspecialidad(pediatria)
        sanJuan.agregarEspecialidad(traumatologia)
        sanJuan.agregarEspecialidad(urologia)
        sanJuan.agregarEspecialidad(nefrologia)
        sanJuan.agregarEspecialidad(dermatologia)
        sanJuan.agregarEspecialidad(reumatologia)
        service.crear(sanJuan)

        arturoOnativia = Hospital(
            "Hospital Dr. Arturo Oñativia",
            "Rafael Calzada",
            "Dr. Ramon Carillo, 1339",
            mutableListOf<Especialidad>()
        )
        arturoOnativia.agregarEspecialidad(dermatologia)
        arturoOnativia.agregarEspecialidad(urologia)
        arturoOnativia.agregarEspecialidad(traumatologia)
        arturoOnativia.agregarEspecialidad(nefrologia)
        service.crear(arturoOnativia)

        sanCayetano = Hospital(
            "Hospital Municipal San Cayetano",
            "Virreyes",
            "Av. Avellaneda y Chile 4850",
            mutableListOf<Especialidad>()
        )
        sanCayetano.agregarEspecialidad(pediatria)
        sanCayetano.agregarEspecialidad(traumatologia)
        sanCayetano.agregarEspecialidad(urologia)
        service.crear(sanCayetano)

        presidentePeron = Hospital(
            "Hospital Presidente Peron",
            "Avellaneda",
            "Antole France 773, Sarandi",
            mutableListOf<Especialidad>()
        )
        presidentePeron.agregarEspecialidad(pediatria)
        presidentePeron.agregarEspecialidad(traumatologia)
        presidentePeron.agregarEspecialidad(urologia)
        presidentePeron.agregarEspecialidad(nefrologia)
        presidentePeron.agregarEspecialidad(dermatologia)
        presidentePeron.agregarEspecialidad(reumatologia)
        service.crear(presidentePeron)

        ramonSarda = Hospital(
            "Hospital Materno Infantil Ramon Sarda",
            "CABA",
            "Esteban de Luca 2151",
            mutableListOf<Especialidad>()
        )
        ramonSarda.agregarEspecialidad(dermatologia)
        ramonSarda.agregarEspecialidad(urologia)
        ramonSarda.agregarEspecialidad(traumatologia)
        ramonSarda.agregarEspecialidad(nefrologia)
        ramonSarda.agregarEspecialidad(pediatria)
        service.crear(ramonSarda)

        argerich = Hospital(
            "Hospital Gral. de Agudos Dr. Cosme Argerich",
            "CABA",
            "Pi y Margall 750",
            mutableListOf<Especialidad>()
        )
        argerich.agregarEspecialidad(pediatria)
        argerich.agregarEspecialidad(traumatologia)
        argerich.agregarEspecialidad(urologia)
        argerich.agregarEspecialidad(nefrologia)
        argerich.agregarEspecialidad(dermatologia)
        argerich.agregarEspecialidad(reumatologia)
        service.crear(argerich)

        joseCPaz = Hospital(
            "Hospital Oncologico De Jose C. Paz",
            "Jose C. Paz",
            "Av. Hector Arregui 501",
            mutableListOf<Especialidad>()
        )
        joseCPaz.agregarEspecialidad(dermatologia)
        joseCPaz.agregarEspecialidad(urologia)
        joseCPaz.agregarEspecialidad(traumatologia)
        joseCPaz.agregarEspecialidad(nefrologia)
        service.crear(joseCPaz)

        turnoEvita1.asignarAPaciente(user2)
        turnoEvita2.asignarAPaciente(user2)
        turnoEvita3.asignarAPaciente(user2)

        turnoService.actualizar(turnoEvita1)
        turnoService.actualizar(turnoEvita2)
        turnoService.actualizar(turnoEvita3)
    }

    @Test
    fun seCreaHospitalTest() {}
}