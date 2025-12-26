package com.example.To_do_App.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.To_do_App.dto.request.LoginRequest;
import com.example.To_do_App.dto.request.SignupRequest;
import com.example.To_do_App.entity.User;
import com.example.To_do_App.exception.InvalidCredentialsException;
import com.example.To_do_App.exception.UserAlreadyExistsException;
import com.example.To_do_App.repository.UserRepository;
import com.example.To_do_App.security.JwtService;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UserRepository repo,
                       PasswordEncoder encoder,
                       JwtService jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public void signup(SignupRequest req) {
        if (repo.existsByEmail(req.getEmail()))
            throw new UserAlreadyExistsException("Email already exists");

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        repo.save(user);
    }

    public String login(LoginRequest req) {
        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid login"));

        if (!encoder.matches(req.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException("Invalid login");

        return jwt.generateToken(user.getEmail());
    }
}
