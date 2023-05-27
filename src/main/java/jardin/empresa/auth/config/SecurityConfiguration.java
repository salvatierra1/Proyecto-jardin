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
                .antMatchers(HttpMethod.GET, "/employee/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/employee/all").permitAll()
                .antMatchers(HttpMethod.GET, "/company/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/company/all").permitAll()
                .antMatchers(HttpMethod.GET, "/project/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/project/all").permitAll()
                .antMatchers(HttpMethod.GET, "/publication/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/publication/all").permitAll()
                .antMatchers(HttpMethod.GET, "/publication/relevant").permitAll()
                .antMatchers(HttpMethod.GET, "/gallery/page").permitAll()
                .antMatchers(HttpMethod.POST, "/employee").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/employee/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/employee/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/company").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/company/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/company/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/project").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/project/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/project/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/publication").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/publication/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/publication/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/gallery").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/gallery/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/gallery/{id}").hasRole("ADMIN")
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