package cat.udl.eps.softarch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/advertisements*/*").authenticated()
                .antMatchers(HttpMethod.POST, "/advertisements*/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/advertisements*/*").authenticated()
                .antMatchers(HttpMethod.PATCH, "/advertisements*/*").authenticated()
                .antMatchers(HttpMethod.PUT, "/pictures*/**").authenticated()
                .antMatchers(HttpMethod.POST, "/pictures*/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/pictures*/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/pictures*/**").authenticated()
                .anyRequest().permitAll()
                .and()
            .httpBasic().realmName("SoftArch 2016-17")
                .and()
            .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
            .csrf().disable();
    }
}
