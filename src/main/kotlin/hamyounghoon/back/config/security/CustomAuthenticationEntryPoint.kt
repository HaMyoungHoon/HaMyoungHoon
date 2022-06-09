package hamyounghoon.back.config.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(request : HttpServletRequest, response : HttpServletResponse, authException : AuthenticationException) {
        response.sendRedirect("/common/exception/entryPoint")
    }
}
