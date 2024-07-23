package collaboration.groupe.collaboration.security;

import collaboration.groupe.collaboration.entities.Jwt;
import collaboration.groupe.collaboration.entities.Utilisateur;
import collaboration.groupe.collaboration.services.UserImpl;
import collaboration.groupe.collaboration.services.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    public JwtFilter(UserImpl user, JwtService jwtService) {
        this.jwtService=jwtService;
        this.user = user;
    }

    private JwtService jwtService;
    private UserImpl user;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //ajout pour le token dans la bd
        Jwt tokenInDb=null;
        String username=null;
        String token=null;
        boolean isTokenExpired=true;
        final String authorization = request.getHeader("Authorization");

        if(authorization!=null && authorization.startsWith("bearer ")){
            token=authorization.substring(7);
            //ca aussi juste la ligne juste en bas
            tokenInDb= this.jwtService.tokenByValue(token);
            isTokenExpired=jwtService.isTokenExpired(token);
            username=jwtService.extractUsername(token);

        }
        if(!isTokenExpired
                && tokenInDb.getUser().getEmail()==username
                && SecurityContextHolder.getContext().getAuthentication()==null){
           UserDetails userDetails=  this.user.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,
                    null,
                    userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
           SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }


        filterChain.doFilter(request,response);
    }




}
