package welper.welper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WelperApplication

fun main(args: Array<String>) {
    runApplication<WelperApplication>(*args)
}
