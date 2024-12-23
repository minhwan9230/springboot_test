package com.example.springboot_test;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenRepository authenRepository;

    public boolean join(AuthenDTO authenDTO) {
        if (!this.authenRepository.existsByUsername(authenDTO.getUsername())) {
            String password = this.passwordEncoder.encode(authenDTO.getPassword());
            AuthenEntity authenEntity = AuthenEntity.builder()
                    .username(authenDTO.getUsername())
                    .password(password)
                    .role("ROLE_USER").build();
            this.authenRepository.save(authenEntity);
            return true;
        }
        return false;
    }
}
