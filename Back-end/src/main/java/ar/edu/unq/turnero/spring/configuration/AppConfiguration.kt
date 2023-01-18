package ar.edu.unq.epers.ubermen.tp.spring.configuration

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AppConfiguration {

    @Autowired
    //lateinit var poderDAO: PoderDAO

    @Bean
    open fun modelMapper(): ModelMapper? {
        return ModelMapper()
    }
}