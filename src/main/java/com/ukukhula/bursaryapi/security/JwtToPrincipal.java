package com.ukukhula.bursaryapi.security;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtToPrincipal {

    public UserPrincipal convert(DecodedJWT jwt)
    {
        return UserPrincipal.builder()
                .microsoftAccessToken(jwt.getSubject())
                .username(jwt.getClaim("username").asString())
                .authorities(extractAuthoritiesFromJwt(jwt))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromJwt(DecodedJWT jwt)
    {
        Claim claim = jwt.getClaim("authorities");

        if (claim.isNull() || claim.isMissing())
        {
            return List.of();
        }
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
