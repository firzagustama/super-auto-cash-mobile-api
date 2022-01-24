package id.superautocash.mobile.api.security.configuration

import id.superautocash.mobile.api.interceptor.AuthTokenInterceptor
import id.superautocash.mobile.api.security.service.UserDetailSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userDetailsSecurityService: UserDetailSecurityService

    @Autowired
    lateinit var passwordEncoder: SecurityPasswordEncoder

    @Autowired
    lateinit var authTokenInterceptor: AuthTokenInterceptor

    @Autowired
    lateinit var authEntryJwtPoint: AuthEntryJwtPoint

    @Autowired
    lateinit var forbiddenHandler: ForbiddenHandler

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth
            ?.userDetailsService(userDetailsSecurityService)
            ?.passwordEncoder(passwordEncoder)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity?) {
        http!!.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(authEntryJwtPoint).and()
            .exceptionHandling().accessDeniedHandler(forbiddenHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers(
                "/menu/create"
            ).hasAuthority("2").and()
            .authorizeRequests().antMatchers(
                "/user/login",
                "/user/register",
                "/user/check",
                "/merchant/login",
                "/merchant/register",
                "/menu/{\\d+}",
                "/menu/detail/{\\d+}"
            )
            .permitAll()
            .anyRequest().authenticated()

        http.addFilterBefore(authTokenInterceptor, UsernamePasswordAuthenticationFilter::class.java)
    }
}