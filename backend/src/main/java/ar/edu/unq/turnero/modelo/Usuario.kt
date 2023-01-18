package ar.edu.unq.turnero.modelo

import javax.persistence.*

@Entity
class Usuario() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(nullable = false, length = 500)
    var nombreYApellido: String? = null
    var image: String? = "https://objetivoligar.com/wp-content/uploads/2017/03/blank-profile-picture-973460_1280-580x580.jpg"
    var dni: Long? = null
    var telefono: Long? = null
    var token: String? = null
    var password: String? = null
    @Column(unique = true)
    var email: String? = null

    constructor(nombreYApellido: String, dni: Long, email: String, telefono: Long, password: String):this() {
        this.nombreYApellido = nombreYApellido
        this.dni = dni
        this.email = email
        this.telefono = telefono
        this.password = password
    }

    constructor(nombreYApellido: String, image: String, dni: Long, email: String, telefono: Long, password: String):this() {
        this.nombreYApellido = nombreYApellido
        this.dni = dni
        this.image = image
        this.email = email
        this.telefono = telefono
        this.password = password
    }

    constructor(nombreYApellido: String, dni: Long):this() {
        this.nombreYApellido = nombreYApellido
        this.dni = dni
    }

    fun sacarTurno(turno: Turno) {
        turno.asignarAPaciente(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Usuario

        if (id != other.id) return false
        if (nombreYApellido != other.nombreYApellido) return false
        if (image != other.image) return false
        if (dni != other.dni) return false
        if (email != other.email) return false
        if (telefono != other.telefono) return false
        if (password != other.password) return false

        return true
    }
}