package com.kbohdanowicz.restapi.app.config.security

import com.kbohdanowicz.restapi.app.config.env.EnvironmentVariables
import com.kbohdanowicz.restapi.app.controller.CommissionController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.core.userdetails.User as SpringUser

@Configuration
@EnableWebSecurity
class SecurityConfig {

    companion object {
        private const val USER_ROLE = "USER"
    }

    @Autowired
    private lateinit var authenticationEntryPoint: MyBasicAuthenticationEntryPoint

    @Bean
    fun userDetailsService(): InMemoryUserDetailsManager {
        val username = EnvironmentVariables.restApiUsername
        val password = EnvironmentVariables.restApiPassword

        val user = SpringUser
            .withUsername(username)
            .password(passwordEncoder().encode(password))
            .roles(USER_ROLE)
            .build()

        return InMemoryUserDetailsManager(user)
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.also {
            it.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            it.authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(CommissionController.COMMISSION_ENDPOINT)
                    .authenticated()
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint)
            }
        }.build()

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()
}
