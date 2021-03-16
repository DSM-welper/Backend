package welper.welper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WelperApplication

fun main(args: Array<String>) {
    runApplication<WelperApplication>(*args)
}
