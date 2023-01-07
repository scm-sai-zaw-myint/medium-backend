package scm.api.restapi.medium.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import scm.api.restapi.medium.bl.service.AuthService;
import scm.api.restapi.medium.persistence.entiry.Users;
import scm.api.restapi.medium.persistence.repo.UsersRepo;


@Component
public class JWTUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;
    
    @Value("${app.jwt.validity}")
    private long EXPIRE_DURATION;
    
    @Autowired
    AuthService authService;
    
    @Autowired
    UsersRepo usersRepo;
    
    @SuppressWarnings("deprecation")
    public String generateAccessToken(Users user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer("VueOJT")
                .setIssuedAt(new Date())
                .setId(user.getIp())
                .setExpiration(new Date(System.currentTimeMillis() + (EXPIRE_DURATION * 60) * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    
    @SuppressWarnings("deprecation")
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            Claims claims = this.parseClaims(token);
            Users user = this.authService.authUser(token);
            if(user != null && user.getId() != null) {
                if(!claims.getId().equals(user.getIp())) {
                    LOGGER.error("Token has been invoked at "+user.getUpdatedAt(), token);
                    return false;
                }else {
                    return true;
                }
            }else if(user == null) {
                return false;
            }
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", token+"\n"+ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }
        return false;
    }
    
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
    
    @SuppressWarnings("deprecation")
    public UserDetails getUserDetails(String token) {
        Users userDetails = new Users();
        Claims claims = this.parseClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        String[] jwtSubject = subject.split(",");
        Integer id = Integer.parseInt(jwtSubject[0]);
        userDetails.setId(id);
        userDetails.setEmail(jwtSubject[1]);
        Users user = this.usersRepo.getById(id);
        if(!user.getIp().equals(claims.getId())) {
            LOGGER.error("User logout or token revoke!", token);
            return null;
        }
        return userDetails;
    }
    
    @SuppressWarnings("deprecation") 
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
