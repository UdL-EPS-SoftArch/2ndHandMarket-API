package cat.udl.eps.softarch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("!Test")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
            .csrf().disable();
    }

    @Configuration
    @EnableResourceServer
    @Profile("!Test")
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers(HttpMethod.PUT, "/advertisements*/*").authenticated()
                    .antMatchers(HttpMethod.POST, "/advertisements*/*").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/advertisements*/*").authenticated()
                    .antMatchers(HttpMethod.PATCH, "/advertisements*/*").authenticated()
                    .antMatchers(HttpMethod.PUT, "/pictures*/**").authenticated()
                    .antMatchers(HttpMethod.POST, "/pictures*/**").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/pictures*/**").authenticated()
                    .antMatchers(HttpMethod.PATCH, "/pictures*/**").authenticated()
                    .anyRequest().permitAll();
        }
    }
}
