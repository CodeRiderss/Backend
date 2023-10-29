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
    private val orderRepository: OrderRepository,
    private val messageRepository: MessageRepository
) {
    @Bean
    fun init(
        carRepository: CarRepository,
        userRepository: UserRepository,
        ratingRepository: RatingRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            val format = "dd.mm.yyyy"
            val formater = SimpleDateFormat(format)
            val rating1 = ratingRepository.save(
                Rating(
                    rating = 3,
                    description = "Rudis Auto stinkt ein bisschen"
                )
            )
            val rating2 = ratingRepository.save(
                Rating(
                    rating = 1,
                    description = "Rudi war nett aber das Auto war vermüllt"
                )
            )
            val rudi = userRepository.save(
                User(
                    name = "Rudi Rakete",
                    password = "1234",
                    description = "Moin ich bin der Rudi ich wohne in meinen Auto",
                    birthday = formater.parse("11.04.1973").toInstant(),
                    experience = "Profi",
                    telephone = "015127594864",
                    email = "rudi.ludolf@gmail.com",
                    profileUrl = "https://get.pxhere.com/photo/man-person-people-white-boy-cute-male-portrait-young-professional-profession-lifestyle-smiling-smile-cheerful-fun-happy-happiness-glasses-handsome-865531.jpg",
                    ratings = listOf(rating1, rating2)
                )
            )
            val beathe = userRepository.save(
                User(
                    name = "Beathe Frauke",
                    password = "1234",
                    description = "Hi ich bin die Beathe ich bin neu hier und freue mich auf euch alle <3",
                    birthday = formater.parse("17.03.1989").toInstant(),
                    experience = "Neuling",
                    telephone = "015174579367",
                    email = "beathe.frauke@gmail.com",
                    profileUrl = "https://c.pxhere.com/photos/78/1d/beautiful_girl_in_the_park_lying_on_the_leaves_autumn_portrait_romantic_park_feeling_in_love-1198265.jpg",
                    ratings = listOf()
                )
            )
            val peter = userRepository.save(
                User(
                    name = "Peter Lustig",
                    password = "1234",
                    description = "Hi mein Name ist Peter Lustig, NEIN NICHT DER PETER LUSTIG",
                    birthday = formater.parse("23.09.1994").toInstant(),
                    experience = "Neuling",
                    telephone = "0151894725",
                    email = "peter.lustig@gmail.com",
                    profileUrl = "https://plus.unsplash.com/premium_photo-1681822817140-bce6c12809fb?auto=format&fit=crop&q=80&w=2672&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                    ratings = listOf()
                )
            )
            val vw = carRepository.save(
                Car(
                    model = "VW Golf GTI Clubsport",
                    user = rudi,
                    buildYear = 2017,
                    imageUrl = "https://cf-cdn-v6.volkswagen.at/media/Content_Model_Overview_ModelList_Model_Image_Component/51646-633315-modelList-429295-image/dh-1115-f17024/e9dcebaa/1695742044/golf-gti.png"
                )
            )
            val offer = offerRepository.save(
                Offer(
                    startDate = formater.parse("06.04.2023").toInstant(),
                    endDate = formater.parse("23.06.2023").toInstant(),
                    latitude = 50.107312,
                    longitude = 8.664892,
                    active = true,
                    user = rudi,
                    pricePerHourInCent = 500,
                    car = vw
                )
            )
            val order1 = orderRepository.save(
                Order(
                    startDate = formater.parse("08.04.2023").toInstant(),
                    endDate = formater.parse("15.04.2023").toInstant(),
                    user = peter,
                    offer = offer
                )
            )
            val order2 = orderRepository.save(
                Order(
                    startDate = formater.parse("30.04.2023").toInstant(),
                    endDate = formater.parse("04.05.2023").toInstant(),
                    user = beathe,
                    offer = offer
                )
            )
            val message1 = messageRepository.save(
                Message(
                    text = "Hey kannst du mir den vorbeifahren in die Mühlgasse?",
                    time = formater.parse("06.04.2023").toInstant(),
                    from = peter,
                    to = rudi,
                )

            )
            val message2 = messageRepository.save(
                Message(
                    text = "Ne das kann ich nicht machen, ich hab kein Führerschein :(",
                    time = formater.parse("07.04.2023").toInstant(),
                    from = rudi,
                    to = peter,
                )

            )
            val message3 = messageRepository.save(
                Message(
                    text = "Ok ich fahr mit Bus",
                    time = formater.parse("08.04.2023").toInstant(),
                    from = peter,
                    to = rudi,
                )
            )
            val rating3 = ratingRepository.save(
                Rating(
                    rating = 5,
                    description = "Echt geile Karre! Riecht auch nicht!"
                )
            )
            val franz = userRepository.save(
                User(
                    name = "Franz Funkenstein",
                    password = "1234",
                    description = "Hi, ich bin Franz, der Spaßmacher! Immer auf der Suche nach Abenteuern und bereit, die Straßen mit meinem schicken Cabrio unsicher zu machen. Wenn du ein Auto für einen Roadtrip oder ein romantisches Wochenende suchst, bin ich dein Mann!",
                    birthday = formater.parse("10.03.1998").toInstant(),
                    experience = "Neuling",
                    telephone = "0151894725",
                    email = "franz.funkenstein@gmail.com",
                    ratings = listOf(rating3)
                )
            )
            val audi = carRepository.save(
                Car(
                    model = "Audi A5 Coupe",
                    user = rudi,
                    buildYear = 2017,
                    imageUrl = "https://www.audibusinessinnovation.com/content/dam/nemo/models/a5/a5-coupe/my-2022/derivative-startpage/stage/1920x1080-AA5_191009_2.jpg?imwidth=768"
                )
            )
            val offer2 = offerRepository.save(
                Offer(
                    startDate = formater.parse("01.01.2023").toInstant(),
                    endDate = formater.parse("24.12.2023").toInstant(),
                    latitude = 50.55949718158492,
                    longitude = 9.691078129095068,
                    active = true,
                    user = franz,
                    pricePerHourInCent = 200,
                    car = audi
                )
            )
            val message4 = messageRepository.save(
                Message(
                    text = "Ich brauche DRINGEND ein Auto. VG Rudi",
                    time = formater.parse("06.04.2023").toInstant(),
                    from = rudi,
                    to = franz,
                )
            )
            val order3 = orderRepository.save(
                Order(
                    startDate = formater.parse("01.04.2023").toInstant(),
                    endDate = formater.parse("01.05.2023").toInstant(),
                    user = rudi,
                    offer = offer2
                )
            )
            val order4 = orderRepository.save(
                Order(
                    startDate = formater.parse("01.04.2023").toInstant(),
                    endDate = formater.parse("01.05.2023").toInstant(),
                    user = peter,
                    offer = offer2
                )
            )


        }
    }

}

fun main(args: Array<String>) {
    runApplication<CodeRidersApplication>(*args)
}
