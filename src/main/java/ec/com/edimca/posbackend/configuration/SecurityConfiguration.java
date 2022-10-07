package ec.com.edimca.posbackend.configuration;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ec.com.edimca.posbackend.constants.RoleConstants.RoleNames;
import ec.com.edimca.posbackend.security.AuthExceptionEntryPoint;
import ec.com.edimca.posbackend.security.AuthorizationFilter;
import ec.com.edimca.posbackend.security.CustomAccessDeniedHandler;

import static ec.com.edimca.posbackend.constants.SecurityConstants.HEADER_NAME;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/users/**")
                .hasAnyAuthority(RoleNames.SUPERADMIN.toString(), RoleNames.USER_ADMIN.toString())
                .antMatchers("/products/**")
                .hasAnyAuthority(RoleNames.SUPERADMIN.toString(), RoleNames.STORE_MANAGER.toString())
                .antMatchers("/orders/**")
                .hasAnyAuthority(RoleNames.SUPERADMIN.toString(), RoleNames.STORE_MANAGER.toString())
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager())).sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedOrigins(new ArrayList<String>(Arrays.asList("*")));
        configuration.setAllowedMethods(new ArrayList<String>(Arrays.asList("GET", "POST", "PUT", "DELETE")));
        configuration.setAllowedHeaders(new ArrayList<String>(Arrays.asList(HEADER_NAME, "Content-Type")));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
