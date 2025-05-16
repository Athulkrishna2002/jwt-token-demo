package com.trading.tradingApplication.controller;

import com.trading.tradingApplication.config.JwtProvider;
import com.trading.tradingApplication.model.dao.UserDao;
import com.trading.tradingApplication.repository.UserRepository;
import com.trading.tradingApplication.response.AuthResponse;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDao userDao) throws Exception {


        UserDao isEmailExist = userRepository.findByEmail(userDao.getEmail());

        if(isEmailExist!=null){
            throw new Exception("email is already used with another account");
        }

        UserDao newUser = new UserDao();
        newUser.setEmail(userDao.getEmail());
        newUser.setPassword(userDao.getPassword());
        newUser.setFullName(userDao.getFullName());

        UserDao savedUser = userRepository.save(newUser);

        //Create an Authentication object (using email and password).
        Authentication auth = new UsernamePasswordAuthenticationToken(userDao.getEmail(),userDao.getPassword());

        //Put this Authentication into Spring Security's SecurityContextHolder.
        SecurityContextHolder.getContext().setAuthentication(auth);

        //Generate a JWT token for this authentication (JwtProvider.generateToken()).
        String jwt = JwtProvider.generateToken(auth);

        //Return the JWT token inside an AuthResponse to the client.
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("register success");

        System.out.println("Trying to register: " + userDao.getEmail());

        System.out.println("Registered user with ID: " + savedUser.getId());


        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
