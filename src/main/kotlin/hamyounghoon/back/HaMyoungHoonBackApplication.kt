package hamyounghoon.back

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
class HaMyoungHoonBackApplication {
    companion object{
        var ctx: ConfigurableApplicationContext? = null
    }
}

fun main(args: Array<String>) {
    val app = SpringApplicationBuilder()
    app.sources(HaMyoungHoonBackApplication::class.java)
        .listeners(ApplicationPidFileWriter("./mhha.pid"))
        .build()

    HaMyoungHoonBackApplication.ctx = app.run(*args)
}
