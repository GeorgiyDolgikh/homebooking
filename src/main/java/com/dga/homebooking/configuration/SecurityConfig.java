package com.dga.homebooking.configuration;

import com.dga.homebooking.models.User;
import com.dga.homebooking.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
//@EnableWebMvc
public class SecurityConfig {

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
   try {

       http
               .authorizeHttpRequests((requests) -> requests
                               .requestMatchers("/personalbooking","/groupbooking").authenticated()

                               .anyRequest().permitAll()
               )
               .formLogin((form) -> form
                       .loginPage("/login")
                       .permitAll()
               )
               .logout(LogoutConfigurer::permitAll)
               .csrf(Customizer.withDefaults()
               );
               //.csrf().disable();
       return http.build();
   }catch (Exception e){
       System.out.println(e.toString());
       throw e;
   }
}
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username -> {
          User user=userRepository.findByEmail(username);
          if(user!=null){
              return user;
          }
          throw new UsernameNotFoundException("Пользователь "+username+" не найден");
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
