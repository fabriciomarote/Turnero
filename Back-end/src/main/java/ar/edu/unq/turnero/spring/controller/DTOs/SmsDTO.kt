package ar.edu.unq.turnero.spring.controller.DTOs

import ar.edu.unq.turnero.modelo.SMS
import ar.edu.unq.turnero.modelo.Turno

class SmsDTO(
    var to: String?,
    var message: String?) {

    companion object {

        fun desdeModelo(sms: SMS) =
            SmsDTO(
                to = sms.to,
                message = sms.message,
            )
    }

    fun aModelo(): SMS {
        val sms = SMS()
        sms.to = this.to
        sms.message = this.message!!
        return sms
    }
}