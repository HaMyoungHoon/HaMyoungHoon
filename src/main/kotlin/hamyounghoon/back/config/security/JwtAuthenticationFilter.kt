package hamyounghoon.back.config.security

import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class JwtAuthenticationFilter(var jwtTokenProvider : JwtTokenProvider): GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
//        val token = jwtTokenProvider.resolveToken(request as HttpServletRequest)
//        if (!token.isNullOrEmpty() && jwtTokenProvider.validateToken(token)) {
//            SecurityContextHolder.getContext().authentication = jwtTokenProvider.getAuthentication(token)
//        }
        chain.doFilter(request, response)
    }
}

