package kbohdanowicz.restapi.security

import kbohdanowicz.restapi.controller.CommissionController
import kbohdanowicz.restapi.logic.read.input.model.User
import kbohdanowicz.restapi.logic.read.readJson
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
        private const val CREDENTIALS_OF_USERS_PATH = "input/credentials.json"
        private const val USER_ROLE = "USER"
    }

    @Autowired
    private val authenticationEntryPoint: MyBasicAuthenticationEntryPoint? = null

    @Bean
    fun userDetailsService(): InMemoryUserDetailsManager {
        val credentialsOfUsers: List<User> = readJson(CREDENTIALS_OF_USERS_PATH)
        val users = credentialsOfUsers.map { (username, password) ->
            SpringUser.withUsername(username)
                .password(passwordEncoder().encode(password))
                .roles(USER_ROLE)
                .build()
        }.toTypedArray()
        // val users = SpringUser.withUsername("user")
        //     .password(passwordEncoder().encode("pass"))
        //     .roles(USER_ROLE)
        //     .build()
        return InMemoryUserDetailsManager(*users)
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
