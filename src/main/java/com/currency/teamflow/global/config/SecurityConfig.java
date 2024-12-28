package com.currency.teamflow.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * create on 2024. 12. 21. create by IntelliJ IDEA.
 *
 * <p> spring security 관련 구현체 클래스 설정. </p>
 *
 * @author Seokgyu Hwang (Chris)
 * @version 1.0
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
@Slf4j(topic = "Security::SecurityConfig")
public class SecurityConfig {

  /**
   * UserDetailsService.
   */
  private final UserDetailsService userDetailsService;

  /**
   * PasswordEncoder(암호 처리기).
   *
   * @return {@link BCryptPasswordEncoder}
   */
  @Bean
  BCryptPasswordEncoder passwordEncoderForSecurity() {
    return new BCryptPasswordEncoder();
  }

  /**
   * AuthenticationManager(인증 관리자).
   *
   * @param config {@link AuthenticationConfiguration}
   * @return 설정이 추가된 AuthenticationManager
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    log.info("AuthenticationManager에 위임.");
    return config.getAuthenticationManager();
  }

  /**
   * AuthenticationProvider(인증 공급자).
   *
   * @return {@link AuthenticationProvider}
   */
  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    log.info("AuthenticationProvider 설정. 구현체: {}", authProvider.getClass().getSimpleName());

    log.info("UserDetailsService에 사용자 관리 위임. 구현체: {}",
        this.userDetailsService.getClass().getSimpleName());
    authProvider.setUserDetailsService(this.userDetailsService);

    log.info("PasswordEncoder에 암호 검증 위임. 구현체: {}",
        this.passwordEncoderForSecurity().getClass().getSimpleName());
    authProvider.setPasswordEncoder(passwordEncoderForSecurity());

    return authProvider;
  }
}
