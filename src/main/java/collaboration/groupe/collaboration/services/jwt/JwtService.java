package collaboration.groupe.collaboration.services.jwt;

import collaboration.groupe.collaboration.entities.Jwt;
import collaboration.groupe.collaboration.entities.Utilisateur;
import collaboration.groupe.collaboration.repository.JwtRepository;
import collaboration.groupe.collaboration.repository.UtilisateurRepository;
import collaboration.groupe.collaboration.services.UserImpl;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service
public class JwtService {


    public static final String BEARER = "bearer";
    private final String Encryption_kEY ="608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";

    private  UserImpl user;
    private JwtRepository jwtRepository;

    private UtilisateurRepository utilisateurRepository;
    public Map<String, Object> generate(String username){
        Utilisateur userDetails=  this.user.loadUserByUsername(username);
        final Map<String,Object> jwtMap=this.generateJwt(userDetails);
        //Ajout pour la déconnexion
       final Jwt jwt= Jwt
                .builder()
               .value((String) jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .user(userDetails)
                .build();
       this.jwtRepository.save(jwt);
        return jwtMap;
    }

    private Map<String, Object> generateJwt(Utilisateur utilisateur){
        final long currentTime = System.currentTimeMillis();
        final long currentTimeExpiration = currentTime +30*60*1000;
        //J'ai remplacer String par Object(il semble que ca soit Object)
        final Map<String, Object> claims = Map.of(
                "nom", utilisateur.getNom(),
                Claims.EXPIRATION,new Date(currentTimeExpiration),
                Claims.SUBJECT,utilisateur.getEmail()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTimeExpiration))
                .setSubject(utilisateur.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of(BEARER,bearer);
    }

    private Key getKey() {
        final byte[] decode = Decoders.BASE64.decode(Encryption_kEY);
        return Keys.hmacShaKeyFor(decode);
    }
    //Cette partie du haut marque la creation d'un jwt token

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate= this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> function){
        Claims claims=getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


//    public Jwt tokenByValue(String value) {
//        return this.jwtRepository.findByValue(value).orElseThrow(
//                ()-> new RuntimeException("Le jwt token est inconnu ")
//        );
//    }
    public Jwt tokenByValue(String value) {
        return this.jwtRepository.findByValueAndDesactiveAndExpire(value,
                false,
                false
        ).orElseThrow(
                ()-> new RuntimeException("Le jwt token est inconnu ")
        );
    }

//    private Date getExpirationDateFromToken(String token) {
//       return this.getClaim(token, Claims::getExpiration);
//    }






//    public void deconnexion() {
//        Utilisateur  utilisateur= (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//      Jwt jwt=this.jwtRepository.findByUserValidToken(
//              utilisateur.getEmail(),
//              false,
//              false).orElseThrow(
//              ()-> new RuntimeException("Token invalide")
//      );
//      jwt.setExpire(true);
//      jwt.setDesactive(true);
//      this.jwtRepository.save(jwt);
//    }

    public void deconnexion() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof String) {
            String email = (String) principal;
            Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Jwt jwt = jwtRepository.findByUserValidToken(utilisateur.getEmail(), false, false)
                    .orElseThrow(() -> new RuntimeException("Token invalide"));
            jwt.setExpire(true);
            jwt.setDesactive(true);
            jwtRepository.save(jwt);
        } else {
            throw new RuntimeException("Erreur lors de la récupération de l'utilisateur connecté");
        }
    }

    public void disableTokens(Utilisateur utilisateur){
        final List<Jwt> jwtList= this.jwtRepository.findByUser(utilisateur.getEmail()).map(
                jwt-> {
                    jwt.setDesactive(true);
                    jwt.setExpire(true);
                    return jwt;
                }
        ).collect(Collectors.toList());
        this.jwtRepository.saveAll(jwtList);
    }
}
