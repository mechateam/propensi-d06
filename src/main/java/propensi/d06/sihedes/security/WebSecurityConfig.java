package propensi.d06.sihedes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/vendor/**").permitAll()
                // .antMatchers("/user/add").hasAuthority("Admin")
                .antMatchers("/sla/daftar/tambah").hasAuthority("Admin")
                .antMatchers("/sla/daftar/update/**").hasAuthority("Admin")
                .antMatchers("/sla/daftar/delete/**").hasAuthority("Admin")
                .antMatchers("/request/individual/return/**").hasAuthority("Kepala Departemen IT")
                .antMatchers("/problem/individual/return/**").hasAuthority("Kepala Departemen IT")
                .antMatchers("/problem/individual/**").hasAnyAuthority("Kepala Departemen IT" , "Staff IT", "Helpdesk")
                .antMatchers("/problem/resolver/**").hasAuthority("Helpdesk")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll()
                .and()
                .cors()
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public BCryptPasswordEncoder encoder() { return new BCryptPasswordEncoder(); }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(encoder())
                .withUser("sfkadmin").password(encoder().encode("sfkadmin"))
                .roles("USER");
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}
