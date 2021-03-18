package welper.welper.configuration

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class TestDatabaseConfiguration {
    val testDriverClassName = "org2.h2.Driver"
    val testUrl = "jdbc:h2:mem:testDB;MODE=mysql"
    val testUsername = "sa"
    val testPassword = ""

    @Bean
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
                .driverClassName(testDriverClassName)
                .url(testUrl)
                .username(testUsername)
                .password(testPassword)
                .build()
    }
}