package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.StatusUsuario;
import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> listar() {
        return userRepository.findAll();
    }

    public User cadastrar(User user) {
        user.setStatusUsuario(StatusUsuario.ATIVO);
        return userRepository.save(user);
    }

    public User desativarUser(String login){
        Optional<User> user = userRepository.findByLogin(login);
        user.get().setStatusUsuario(StatusUsuario.INATIVO);
        return userRepository.save(user.get());
    }

    public void excluirUser(String login){
        Optional<User> user = userRepository.findByLogin(login);
        userRepository.deleteById(user.get().getId());
    }

    public User alterarSenha(String login, User user){
        Optional<User> oldUser = userRepository.findByLogin(login);
        user.setId(oldUser.get().getId());
        user.setLogin(oldUser.get().getLogin());
        user.setStatusUsuario(StatusUsuario.ATIVO);
        return userRepository.save(user);
    }
}
