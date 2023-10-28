package com.coderiders.coderiders

import com.coderiders.coderiders.model.*
import com.coderiders.coderiders.repository.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.text.SimpleDateFormat


@SpringBootApplication
class CodeRidersApplication(
    private val offerRepository: OfferRepository,
    private val orderRepository: OrderRepository
) {
//    @Bean
    fun init(carRepository: CarRepository, userRepository: UserRepository, ratingRepository: RatingRepository): CommandLineRunner {
        return CommandLineRunner {
            val format = "dd.mm.yyyy"
            val formater = SimpleDateFormat(format)
            val rating1 = ratingRepository.save(Rating(
                rating = 1,
                description = "Rudis Auto stinkt ein bisschen"
            ))
            val rating2 = ratingRepository.save(Rating(
                rating = 1,
                description = "Rudi war nett aber das Auto war vermüllt"
            ))
            val rudi = userRepository.save(User(
                name =  "Rudi Rakete",
                password = "1234",
                description = "Moin ich bin der Rudi ich wohne in meinen Auto",
                age = 68,
                experience = "Profi",
                telephone = "015127594864",
                email = "rudi.ludolf@gmail.com",
                ratings = listOf(rating1, rating2)
                )
            )
            val beathe= userRepository.save(User(
                name =  "Beathe Frauke",
                password = "1234",
                description = "Hi ich bin die Beathe ich bin neu hier und freue mich auf euch alle <3",
                age = 36,
                experience = "Neuling",
                telephone = "015174579367",
                email = "beathe.frauke@gmail.com",
                ratings = listOf()
            )
            )
            val peter = userRepository.save(User(
                name =  "Peter Lustig",
                password = "1234",
                description = "Hi mein Name ist Peter Lustig, NEIN NICHT DER PETER LUSTIG",
                age = 23,
                experience = "Neuling",
                telephone = "0151894725",
                email = "peter.lustig2@gmail.com",
                ratings = listOf()
            )
            )
            val BMWx7 = carRepository.save(
                Car(
                    model = "BMW X7",
                    user = rudi,
                    buildYear = 2017
                )
            )
            val offer = offerRepository.save(Offer(
                start= formater.parse("06.04.2023").toInstant(),
                end = formater.parse("23.6.2023").toInstant(),
                place = "Frankfurt HBF",
                active= true,
                user = rudi,
                pricePerHourInCent = 500
            ))
//            val order1 = orderRepository.save(Order(
//                start= formater.parse("08.04.2023").toInstant(),
//                end = formater.parse("15.4.2023").toInstant(),
//                user = peter
//            ))
//            val order2 = orderRepository.save(Order(
//                start= formater.parse("30.04.2023").toInstant(),
//                end = formater.parse("4.5.2023").toInstant(),
//                user = beathe
//            ))
        }
    }

}

fun main(args: Array<String>) {
    runApplication<CodeRidersApplication>(*args)
}
