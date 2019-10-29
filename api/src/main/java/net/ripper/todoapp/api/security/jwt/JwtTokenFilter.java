package net.ripper.todoapp.api.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper mapper;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, ObjectMapper mapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.mapper = mapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        try {
            if (token != null && jwtTokenProvider.isValid(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException ex) {
            setJwtExpiredResponse(((HttpServletResponse) response));
            return;
        }
        chain.doFilter(request, response);
    }

    private void setJwtExpiredResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        JwtError jwtError = new JwtError();
        jwtError.code = HttpStatus.UNAUTHORIZED.value();
        jwtError.reason = "Jwt token has expired";
        try {
            response.getWriter().write(mapper.writeValueAsString(jwtError));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class JwtError {
        private int code;
        private String reason;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
