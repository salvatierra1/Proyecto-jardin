package jardin.empresa.auth.config;


import jardin.empresa.auth.filter.JwtRequestFilter;
import jardin.empresa.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsCustomService userDetailsCustomService;

    public SecurityConfiguration(@Autowired @Lazy UserDetailsCustomService userDetailsCustomService) {
        this.userDetailsCustomService = userDetailsCustomService;
    }
    @Autowired
    @Lazy
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsCustomService);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors();
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/signup", "/auth/signin", "/auth/recuperar/{name}").permitAll()
                .antMatchers(HttpMethod.GET, "/empleado/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/empleado/all").permitAll()
                .antMatchers(HttpMethod.GET, "/empresa/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/empresa/all").permitAll()
                .antMatchers(HttpMethod.GET, "/proyecto/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/proyecto/all").permitAll()
                .antMatchers(HttpMethod.GET, "/publicacion/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/publicacion/all").permitAll()
                .antMatchers(HttpMethod.GET, "/publicacion/relevant").permitAll()
                .antMatchers(HttpMethod.GET, "/galeria/page").permitAll()
                .antMatchers(HttpMethod.POST, "/empleado").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/empleado/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/emepleado/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/empresa").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/empresa/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/empresa/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/proyecto").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/proyecto/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/proyecto/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/publicacion").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/publicacion/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/publicacion/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/galeria").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/galeria/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/galeria/{id}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
    /*@Override
    protected void configure(HttpSecurity http) throws  Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().anyRequest().permitAll();
//                .authorizeRequests().antMatchers("/authenticate").permitAll().
//                anyRequest().authenticated()
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }*/

}