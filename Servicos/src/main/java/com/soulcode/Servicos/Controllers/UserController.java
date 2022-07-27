package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("servicos")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios")
    public List<User> usuarios() {
        return userService.listar();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<User> inserirUsuario(@RequestBody User user) {
        String senhaCodificada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCodificada);
        user = userService.cadastrar(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/usuarios/desativarUser/{login}")
    public ResponseEntity<User> desativarUser(@PathVariable String login){
        User user = userService.desativarUser(login);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/usuarios/{login}")
    public ResponseEntity<Void> excluirUser(@PathVariable String login){
        userService.excluirUser(login);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/usuarios/alterarSenha/{login}")
    public ResponseEntity<User> alterarSenha(@PathVariable String login, @RequestBody User user){
        String senhaCodificada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCodificada);
        userService.alterarSenha(login, user);
        return ResponseEntity.ok().body(user);
    }
}
