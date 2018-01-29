package com.example.myspringsecurityboot.config.service.jwt;

import com.example.myspringsecurityboot.model.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service for get Token by username and password.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultTokenService implements TokenService {

    private final UserDetailsService userDetailsService;

    @Value("${secret.key}")
    private String key;

    @Value("${token.expiration.time}")
    private Integer expirationTime;

    @Override
    public String getToken(final String username, final String password) throws UsernameNotFoundException {
        if (username == null || password == null) {

            return null;
        }

        final Optional<User> user = Optional.of((User) userDetailsService.loadUserByUsername(username));

        final Map<String, Object> token = new HashMap<>();

        if (password.equals(user.get().getPassword())) {

            final Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, expirationTime);
            token.put("authorityType", String.valueOf(user.get().getAuthorities()));
            token.put("userId", user.get().getId());
            token.put("username", user.get().getUsername());
            token.put("validateTime", calendar.getTime());


            final JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.setExpiration(calendar.getTime());
            jwtBuilder.setClaims(token);

            return jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
        } else {

            throw new UsernameNotFoundException("Auth error");
        }
    }
}