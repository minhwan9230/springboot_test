package com.example.springboot_test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final AuthenService authenService;

    @PostMapping(value = "/join")
    public ResponseEntity<String> join(@RequestBody AuthenDTO authenDTO) {
        if (this.authenService.join(authenDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("가입성공");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 있는 아이디 입니다.");
    }
}
