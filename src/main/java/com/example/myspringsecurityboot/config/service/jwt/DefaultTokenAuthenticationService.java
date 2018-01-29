package com.example.myspringsecurityboot.config.service.jwt;

import com.example.myspringsecurityboot.model.User;
import com.example.myspringsecurityboot.model.UserAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Optional;

/**
 * Service for checking token.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultTokenAuthenticationService implements TokenAuthenticationService {

    private final UserDetailsService userDetailsService;

    @Value("${secret.key}")
    private String key;

    @Value("${auth.header.name}")
    private String headerName;

    @Override
    @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
    public Authentication authenticate(final HttpServletRequest request) {

        final String token = request.getHeader(headerName);

        final Optional<Jws<Claims>> tokenData = Optional.ofNullable(parseToken(token));

        if (tokenData.isPresent()) {

            final Long validateTime = (Long) tokenData.get().getBody().get("validateTime");

            final Calendar calendar = Calendar.getInstance();

            final Long nowTime = calendar.getTime().getTime();

            if (nowTime < validateTime) {

                final Optional<User> user = Optional.ofNullable(getUserFromToken(tokenData.get()));

                if (user.isPresent()) {

                    return new UserAuthentication(user.get());
                }
            }
        }

        return null;
    }

    private Jws<Claims> parseToken(final String token) {

        if (token != null) {

            try {

                return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                    | SignatureException | IllegalArgumentException e) {

                return null;
            }
        }

        return null;
    }

    private User getUserFromToken(final Jws<Claims> tokenData) throws RuntimeException {
        return (User) userDetailsService
                .loadUserByUsername(tokenData.getBody().get("username").toString());
    }
}