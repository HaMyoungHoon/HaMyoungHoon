package hamyounghoon.back.config.mybatis

import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver

@Configuration
@MapperScan(basePackages = ["hamyounghoon.back.mapper.mhha"],
    sqlSessionFactoryRef = MhhaConfig.SESSION_FACTORY)
class MhhaConfig {
    companion object {
        const val DATA_SOURCE = "MhhaDataSource"
        const val SESSION_FACTORY = "MhhaSessionFactory"
    }

    @Bean(name = [DATA_SOURCE])
    @ConfigurationProperties(prefix = "spring.datasource.mhha")
    fun dataSource(): HikariDataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()

    @Bean(name = [SESSION_FACTORY])
    fun sessionFactory(): SqlSessionFactory {
        val session = SqlSessionFactoryBean()
        session.setDataSource(this.dataSource())
        session.setPlugins(MhhaInterceptor())
        session.setMapperLocations(*PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/mhha/**/*.xml"))
        session.vfs = SpringBootVFS::class.java
        return session.`object`!!
    }
}
