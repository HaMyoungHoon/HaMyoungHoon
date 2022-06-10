package hamyounghoon.back.config.security

import hamyounghoon.back.model.mhha.person.PersonModel
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider {
    @Value(value = "\${spring.jwt.secret}") var secretKey : String = ""
    var tokenValidMilliseconds = 1000L * 60 * 60 * 8
    @Autowired lateinit var userDetailsService : UserDetailsService

    companion object {
        const val authToken : String = "auth_token"
    }
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(user: PersonModel, validTime: Long = tokenValidMilliseconds): String {
        val now = Date()
        return Jwts.builder().setClaims(Jwts.claims().setSubject(user.id).apply {
            this["id"] = user.id
            this["name"] = user.name
            this["status"] = user.status
        }).setIssuedAt(now).setExpiration(Date(now.time + validTime))
            .signWith(SignatureAlgorithm.HS256, secretKey).compact()
    }

    fun getAuthentication(token : String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getPersonData(token: String): PersonModel {
        return getAuthentication(token).principal as PersonModel
    }

    fun getUserPk(token : String): String =
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject

    fun resolveToken(req : HttpServletRequest): String? =
        req.getHeader(authToken)

    fun validateToken(token : String) = try {
        !Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.expiration.before(Date())
    }
    catch (e: Exception) {
        false
    }
}
