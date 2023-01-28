// package konex.innovation.medicine_administration.config;

// import java.util.Arrays;
// import java.util.List;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// //@EnableWebSecurity
// @Configuration
// public class SecurityConfig {

// @Bean
// CorsConfigurationSource corsConfigurationSource() {
// CorsConfiguration configuration = new CorsConfiguration();
// configuration.setAllowedOrigins(Arrays.asList("*"));
// configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
// "DELETE"));
// configuration.setAllowedHeaders(List.of("Origin", "Content-Type",
// "Authorization"));

// UrlBasedCorsConfigurationSource source = new
// UrlBasedCorsConfigurationSource();
// source.registerCorsConfiguration("/**", configuration);
// return source;
// }

// }
