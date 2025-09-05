package com.example.demo.Services;

import com.example.demo.Config.jwtTokenCreation;
import com.example.demo.Entity.LoginEntity;
import com.example.demo.Entity.RegisterEntity;
import com.example.demo.Repositry.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private jwtTokenCreation jwtTokenCreation;

    private Map<String,otpData> otpMap = new HashMap<>();

    public ResponseEntity<?> login(LoginEntity login)
    {

        RegisterEntity user = userRepo.findByUsername(login.getUsername());
        if(user != null)
        {
            if(passwordEncoder.matches(login.getPassword(),user.getPassword()))
            {
                String otp = otpGenerater();
                otpMap.put(user.getUsername(),
                        new otpData(otp,System.currentTimeMillis() + 60_000));
                return sendEmail(user.getEmail(),user.getUsername(),user.getAccno(),otp);
            }
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Retry login");
    }

    public ResponseEntity<?> sendEmail(String email, String username, String accno, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("ujavalsavaliya@gmail.com", "YourBank"); // Branded Sender Name
            helper.setTo(email);
            helper.setSubject("YourBank - OTP Verification");

            String body = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; color: #333; }"
                    + ".container { padding: 20px; border: 1px solid #ddd; border-radius: 8px; }"
                    + ".otp { font-size: 22px; font-weight: bold; color: #2c7be5; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='container'>"
                    + "<h2>Dear " + username + ",</h2>"
                    + "<h2>Account no " + accno + ",</h2>"
                    + "<p>Your OTP for login verification is:</p>"
                    + "<p class='otp'>" + otp + "</p>"
                    + "<p>This OTP is valid for <b>1 minute</b>. Do not share it with anyone.</p>"
                    + "<br><p>Regards,<br>YourBank Team</p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            helper.setText(body, true); // Send as HTML
            message.addHeader("X-Priority", "1");
            message.addHeader("Importance", "high");

            mailSender.send(message);
            return ResponseEntity.ok("OTP sent successfully to " + email);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP: " + e.getMessage());
        }
    }

    public ResponseEntity<?> verifyOtp(String username,String otp){
        otpData otpData = otpMap.get(username);
        if(otpData != null){
            if(System.currentTimeMillis() <= otpData.expiryTime() && otp.equals(otpData.otp()) ){
                otpMap.remove(username);
                String token = jwtTokenCreation.makeToken(username);
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP not found");
    }

    public String otpGenerater(){
        StringBuilder sb = new StringBuilder();
        String no = "0123456789";
        for(int i = 0; i < 6; i++){
            sb.append(no.charAt((int) (Math.random() * no.length())));
        }
        return sb.toString();
    }

    private static class otpData{
        private final String otp;
        private final long expiryTime;

        public otpData(String otp,long expiryTime){
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        private String otp(){
            return otp;
        }
        private long expiryTime(){
            return expiryTime;
        }
    }

    public ResponseEntity<?> ResendOtp(String username){
        RegisterEntity registerEntity = userRepo.findByUsername(username);
        if(registerEntity != null){
            String otp = otpGenerater();
            otpMap.remove(registerEntity.getUsername());
            otpMap.put(registerEntity.getUsername(),new otpData(otp,System.currentTimeMillis() + 60_000));
            return sendEmail(registerEntity.getEmail(),registerEntity.getUsername(),registerEntity.getAccno(),otp);
        }
        return ResponseEntity.badRequest().body("Retry Login");
    }
}
