package ar.edu.unq.turnero

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
open class TurneroApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<TurneroApplication>(*args)
        }
    }
}