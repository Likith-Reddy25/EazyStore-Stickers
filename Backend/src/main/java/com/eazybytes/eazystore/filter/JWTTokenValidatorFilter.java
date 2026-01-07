package com.eazybytes.eazystore.filter;

import com.eazybytes.eazystore.Constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

//    private final Environment env;
    private final List<String> publicPaths;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();

        // ✅ Skip JWT validation for public paths
        for (String publicPath : publicPaths) {
            if (path.startsWith(publicPath.replace("/**", ""))) {
                filterChain.doFilter(request, response);
                return;
            }
        }


        String authHeader = request.getHeader(ApplicationConstants.JWT_HEADER);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String jwt = authHeader.substring(7);
                Environment env= getEnvironment();
                String secret = env.getProperty(
                        ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE
                );

                SecretKey secretKey =
                        Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();

                String username = claims.get("email", String.class);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.emptyList()
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

            }catch (ExpiredJwtException exception) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token Expired");
                return;
            }

            catch (Exception ex) {
                SecurityContextHolder.clearContext();
                ex.printStackTrace();
                throw new BadCredentialsException("Invalid JWT Token");
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {


        String path = request.getRequestURI();
        return publicPaths.stream()
                .anyMatch(publicPath -> antPathMatcher.match(publicPath, path));
    }
}
