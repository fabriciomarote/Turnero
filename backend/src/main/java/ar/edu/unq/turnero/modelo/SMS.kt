package ar.edu.unq.turnero.modelo

class SMS {
    var to: String? = null
    var message: String? = null

    constructor()

    constructor(to: String, message: String):this() {
        this.to = to
        this.message = message
    }

}