package ar.edu.unq.turnero.spring.controller

import ar.edu.unq.turnero.service.SMSService
import ar.edu.unq.turnero.spring.controller.DTOs.SmsDTO
import com.twilio.exception.ApiException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RestController
@CrossOrigin
class SMSController(private val service: SMSService) {

    @Autowired
    private val webSocket: SimpMessagingTemplate? = null
    private val TOPIC_DESTINATION = "/topic/sms"
    @RequestMapping(
        value = ["/sms"],
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun smsSubmit(@RequestBody smsRequest: SmsDTO): ResponseEntity<Any> {
        val sms = smsRequest.aModelo()
        try {
            service.send(sms)
            return ResponseEntity<Any>("sent successfully", HttpStatus.OK)
        } catch (e: ApiException) {
            webSocket!!.convertAndSend(TOPIC_DESTINATION, timeStamp + ": Error sending the SMS: " + e.message)
            throw e
        }
        webSocket!!.convertAndSend(TOPIC_DESTINATION, timeStamp + ": SMS has been sent!: " + sms.to)
    }

    private val timeStamp: String
        private get() = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())
}