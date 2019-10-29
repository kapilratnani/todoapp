package net.ripper.todoapp.api.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.ripper.todoapp.api.security.jwt.JwtTokenFilter;
import net.ripper.todoapp.api.security.jwt.JwtTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper mapper;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider, ObjectMapper mapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.mapper = mapper;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider, mapper), UsernamePasswordAuthenticationFilter.class);
    }
}
