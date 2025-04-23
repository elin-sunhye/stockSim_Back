package com.elin.stocksim_back.service;

import com.elin.stocksim_back.entity.User;
import com.elin.stocksim_back.exception.FieldError;
import com.elin.stocksim_back.exception.UserAlreadyAuthenticatedException;
import com.elin.stocksim_back.repository.UserRepository;
import com.elin.stocksim_back.security.jwt.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String FROM_EMAIL;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    //    인증메일 보내기
    public void sendAuthenticateEmail(String to) throws Exception {
//        Date expires = new Date(new Date().getTime() + (1000l * 60 * 5));
//        메일 인증 토큰 생성
        String emailToken = jwtUtil.generateToken(null, null, "mailTokenExpire");
//        String href = "https://stocksim.store/api/auth/email?email=" + to + "&token=" + emailToken;
        String href = "http://localhost:8080/api/auth/email?email=" + to + "&token=" + emailToken;

        final String SUBJECT = "[stockSim] 계정 활성화 인증 메일입니다.";
        String cont = String.format("""
                    <html lang="ko">
                    <head>
                        <meta charset="UTF-8">
                    </head>
                    <body>
                      <div style="display: flex; flex-direction: column; justify-content: center; align-items: center">
                        <h1>계정 활성화</h1>
                        <p>계정 활성화를 하시려면 아래의 인증 버튼을 클릭하세요.</p>
                        <a href="%s" target="_blank" style="box-sizing: border-box; padding: 7px 15px; border: none; border-radius: 8px; color: #fff; text-decoration: none; background-color: #2383e2;">
                          인증하기
                        </a>
                      </div>
                    </body>
                    </html>
                """, href);

//        StringBuilder builder = new StringBuilder();
//        builder.append("<html>");
//            builder.append("<body>");
//                builder.append("""
//
//                        """);
//            builder.append("</body>");
//        builder.append("</html>");

        sendMail(to, SUBJECT, cont);
    }

    //    메일 인증 확인
    @Transactional(rollbackFor = Exception.class)
    public String authenticateEmail(String email, String token) {
        String respMsg = "";

        try {
//            jwtUtil.parseToken(token);

            Optional<User> userOptional = userRepository.getUserByEmail(email);

            if (userOptional.isEmpty()) {
                throw new UsernameNotFoundException("[인증실패] 존재하지 않는 사용자입니다.");
            } else {
                User user = userOptional.get();
                if (user.getAccountVerified() == 1) {
                    throw new UserAlreadyAuthenticatedException(List.of(FieldError.builder()
                            .field("[인증실패]")
                            .message("이미 인증된 사용자입니다.")
                            .build()));
                } else {
                    userRepository.updateAccountVerified(email);
                    respMsg = "[인증성공] 인증에 성공하였습니다.";
                }
            }
        } catch (Exception e) {
            throw new InsufficientAuthenticationException("[인증실패] 토큰이 유효하지 않거나 인증 시간을 초과하였습니다.");
        }

        return respMsg;
    }

    //    메일 전송 공통 메서드
    public void sendMail(String to, String subject, String cont) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());

        mimeMessageHelper.setFrom(FROM_EMAIL);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);

        mimeMessage.setText(cont, StandardCharsets.UTF_8.name(), "html");

        javaMailSender.send(mimeMessage);
    }
}
