package ar.edu.unq.turnero.service

import ar.edu.unq.turnero.modelo.SMS
import com.twilio.Twilio
import ar.edu.unq.turnero.service.TwilioAccount
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import java.net.URI

@Component
class SMSService {

    private val twilioAccount = TwilioAccount()
    fun send(sms: SMS) {
        Twilio.init(twilioAccount.ACCOUNT_SID, twilioAccount.AUTH_TOKEN)
        // to number, from: always twilio snd a message
        println(sms.to)
        val message: Message = Message.creator(PhoneNumber(sms.to), PhoneNumber(twilioAccount.FROM_NUMBER), sms.message)
            .setStatusCallback(URI.create("http://677add1a.ngrok.io/smscallback"))
            .create()
    }

    fun receive(smscallback: MultiValueMap<String?, String?>?) {}
}