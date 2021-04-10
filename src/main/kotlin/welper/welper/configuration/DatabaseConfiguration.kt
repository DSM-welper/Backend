package welper.welper.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfiguration(
    @Value("\${DB_DRIVER}")
    val driverClassName: String,
    @Value("\${DB_URL:jdbc:mysql://localhost:3306/welper?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC&autoReconnection=true}")
    val url: String,
    @Value("\${DB_NAME}")
    val username: String,
    @Value("\${DB_PASS}")
    val password: String,

) {

    @Bean
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build()
    }
}