package hamyounghoon.back.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {
    @Bean
    fun openApi(): OpenAPI = OpenAPI().info(v1Info())

    private fun v1Info(): Info {
        return Info()
            .title("HaMyoungHoon back Api")
            .description("HaMyoungHoon.github.io 용도이긴한데 백 서버가 없어서 못 돌림")
            .license(License())
            .contact(Contact())
    }
}
