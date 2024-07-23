package collaboration.groupe.collaboration.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public SecurityConfig(JwtFilter jwtFilter, BCryptPasswordEncoder passwordEncoder) {
        this.jwtFilter = jwtFilter;
        this.passwordEncoder=passwordEncoder;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return  httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("POST", "/register").permitAll()
                        .requestMatchers("POST", "/activation").permitAll()
                        .requestMatchers("POST", "/connection").permitAll()
                        .requestMatchers("POST", "/create").permitAll()
                        .requestMatchers( "/messages/send/{sender}/{recipient}/{content}").permitAll()
                        .requestMatchers( "/messages/user/{userId}").permitAll()
                        .requestMatchers( "/file/upload").permitAll()
                        .requestMatchers( "/file/download/{fileId}/{userId}").permitAll()
                        .requestMatchers( "/file/delete/{fieldId}/{userId}").permitAll()
                        .requestMatchers( "/file/write").permitAll()
                        .requestMatchers("POST", "/create").hasRole("ADMIN")
                        .requestMatchers("POST", "/createprojets/{email}").permitAll()
                                .anyRequest().authenticated()
                        )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        //Stanless veut dire sans état spring ne garde en mémoire la connaissance des anciens personnes connectées
                )
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider  authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
        return daoAuthenticationProvider;
    }


}
