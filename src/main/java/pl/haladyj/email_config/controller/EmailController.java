package pl.haladyj.email_config.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.haladyj.email_config.configuration.EmailConfig;
import pl.haladyj.email_config.constants.Constants;
import pl.haladyj.email_config.domain.User;
import pl.haladyj.email_config.model.SignUpRequest;
import pl.haladyj.email_config.repository.UserRepository;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth/")
public class EmailController {

    private final UserRepository repository;
    private final JavaMailSenderImpl mailSender;

    public EmailController(UserRepository repository, JavaMailSenderImpl mailSender) {
        this.repository = repository;
        this.mailSender = mailSender;
    }

    @PostMapping("signup/")
    public ResponseEntity<User> signUp(@RequestBody @Valid SignUpRequest request) {

        User user = new User();
        user.setLogin(request.getLogin());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        String activationKey = RandomStringUtils.randomNumeric(Constants.ACTIVATION_KEY_LENGTH);

        user.setActivationKey(activationKey);
        user.setActive(false);

        User result = repository.save(user);
        EmailConfig.sendActivationEmail(mailSender, activationKey, user.getEmail());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/user/{login}")
                .buildAndExpand(result.getLogin()).toUri();

        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("activate/{activationKey}")
    public ResponseEntity<User> activateUser(@PathVariable String activationKey){
        return repository
                .findByActivationKey(activationKey)
                .map(result->{
                    String secretKey = RandomStringUtils.randomNumeric(Constants.ACTIVATION_KEY_LENGTH);
                    result.setActive(true);
                    result.setActivationKey(secretKey);
                    repository.save(result);
                    return ResponseEntity.ok().body(result);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
