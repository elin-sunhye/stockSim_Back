package com.elin.stocksim_back.controller;


import com.elin.stocksim_back.dto.request.auth.ReqSignInDto;
import com.elin.stocksim_back.dto.request.auth.ReqSignUpDto;
import com.elin.stocksim_back.dto.response.auth.RespAuthDto;
import com.elin.stocksim_back.dto.response.auth.RespSignUpDto;
import com.elin.stocksim_back.entity.User;
import com.elin.stocksim_back.service.AuthService;
import com.elin.stocksim_back.service.MailService;
import com.elin.stocksim_back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "auth", description = "권한 관련 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @Operation(
            summary = "회원가입",
            description = "유저 등록"
    )
    @PostMapping("/signup")
    public ResponseEntity<RespSignUpDto> signUp(@RequestBody ReqSignUpDto dto) throws Exception {
        RespSignUpDto newUserId = authService.signUp(dto);

        if (newUserId.getUserId() > 0) {
            User foundUser = userService.getUserByUserId(newUserId.getUserId());
            mailService.sendAuthenticateEmail(foundUser.getEmail());
        }

        return ResponseEntity.ok().body(authService.signUp(dto));
    }

    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<RespAuthDto> signIn(@RequestBody ReqSignInDto dto) {
        return ResponseEntity.ok().body(authService.signIn(dto));
    }

    @Operation(summary = "refreshToken", description = "accessToken 만료 시 refreshToken 만료 확인 후 newAccessToken 생성")
    @PostMapping("/token/refresh")
    public ResponseEntity<RespAuthDto> refresh(@RequestBody RespAuthDto reqRefreshDto) {
        return ResponseEntity.ok().body(authService.refresh(reqRefreshDto.getRefreshToken()));
    }

    @Operation(summary = "인증메일 보내기")
    @PostMapping("/email/{userId}")
    public ResponseEntity<String> sendAuthenticateEmail(@PathVariable int userId) throws Exception {
        User foundUser = userService.getUserByUserId(userId);
        mailService.sendAuthenticateEmail(foundUser.getEmail());
        return ResponseEntity.ok().body("전송 완료");
    }

    @Operation(summary = "메일 인증 확인")
    @GetMapping("/email")
    public ResponseEntity<String> authenticateEmail(
            @RequestParam String email,
            @RequestParam String token
    ) {
        //        인증 버튼 클릭 시 창이 열리는데 인증 절차 다 마치고나면 그 창 닫기
        String script = String.format("""
                    <script>
                        alert("%s");
                        window.close();
                    </script>
                """, mailService.authenticateEmail(email, token));
        return ResponseEntity.ok().header("Content-Type", "text/html; charset=utf-8").body(script);
    }

    @Operation(summary = "전화번호 인증")
    @PostMapping("/phone")
    public ResponseEntity<?> authenticatePhoneNum() {
        return ResponseEntity.ok().build();
    }
}
