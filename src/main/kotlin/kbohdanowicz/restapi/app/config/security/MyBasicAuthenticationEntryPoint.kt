package kbohdanowicz.restapi.app.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class MyBasicAuthenticationEntryPoint : BasicAuthenticationEntryPoint() {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authEx: AuthenticationException
    ) {
        response.addHeader("WWW-Authenticate", "Basic realm=\"$realmName\"")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val writer = response.writer
        writer.println("HTTP Status 401 - ${authEx.message}")
    }

    override fun afterPropertiesSet() {
        realmName = "kbohdanowicz"
        super.afterPropertiesSet()
    }
}