package com.Demo.Example.Controller;

import com.Demo.Example.Model.PasswordChangeRequest;
import com.Demo.Example.Model.ResetPassword;
import com.Demo.Example.Model.User;
import com.Demo.Example.Record.Mailbody;
import com.Demo.Example.Repository.ResetPasswordRepository;
import com.Demo.Example.Repository.UserRepository;
import com.Demo.Example.Service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RequestMapping("/Newer")
@RestController
public class ResetPasswordController {

    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final  EmailService emailService;
    private final ResetPasswordRepository resetPasswordRepository;


    public ResetPasswordController(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, ResetPasswordRepository resetPasswordRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.resetPasswordRepository = resetPasswordRepository;
    }

    Integer otp = otpGenerator();

    @PostMapping("verifymail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email!" + email));
        Mailbody mailbody = Mailbody.builder()
                .to(email)
                .text("This is the otp for reset password request:" + otp)
                .subject("Otp for reset password")
                .build();
        ResetPassword rp = new ResetPassword().builder().otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 80 * 1000))
                .user(user)
                .build();
        emailService.sendSimpleMessage(mailbody);
        resetPasswordRepository.save(rp);
        return ResponseEntity.ok("Email has been sent for verification");
    }

    @PostMapping("/verify/{otp}/{email}")
    public ResponseEntity<String> verifyotp(@PathVariable Integer otp, String email) {
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("Please Provide Valid Email Address"));

        ResetPassword rp = resetPasswordRepository.findbyOtpandUser(otp, user)
                .orElseThrow(() -> new RuntimeException("Invalid otp for email"));
        if (rp.getExpirationTime().before(Date.from(Instant.now()))) {
            resetPasswordRepository.deleteAllByRid(rp.getRid());
            return new ResponseEntity<>("Otp has expired", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(" Otp Verified ");
    }

    @PostMapping("/reset/{email}")
    public ResponseEntity<String> changepasswordhandler(@PathVariable String email, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        if (!Objects.equals(passwordChangeRequest.password(), passwordChangeRequest.repeatpassword())) {
            return new ResponseEntity<>("Please enter correct password", HttpStatus.BAD_REQUEST);
        }

    String encodedpassword = passwordEncoder.encode(passwordChangeRequest.password());
        userRepository.updatePassword(email,encodedpassword);
        return  ResponseEntity.ok("Password has been reset");
}

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }

}
