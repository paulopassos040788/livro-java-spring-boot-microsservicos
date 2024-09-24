package br.com.passos.user_api.service;

import br.com.passos.user_api.dto.UserDTO;
import br.com.passos.user_api.model.User;
import br.com.passos.user_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDTO::convert).collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        return this.userRepository.findById(id)
                .map(UserDTO::convert)
                .orElseThrow(RuntimeException::new);
    }

    public UserDTO save(UserDTO userDTO) {
        userDTO.setDataCadastro(LocalDateTime.now());
        User user = this.userRepository.save(User.convert(userDTO));
        return UserDTO.convert(user);
    }

    public void delete(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(RuntimeException::new);
        this.userRepository.delete(user);
    }

    public UserDTO findByCpf(String cpf) {
        User user = this.userRepository.findByCpf(cpf);
        if(user != null) {
            return UserDTO.convert(user);
        }
        return null;
    }

    public List<UserDTO> queryByName (String nome) {
        List<User> users = this.userRepository.queryByNomeLike(nome);
        return users.stream().map(UserDTO::convert).collect(Collectors.toList());
    }

    public UserDTO editUser(Long userId, UserDTO userDTO) {
        User user = this.userRepository.findById(userId).orElseThrow(RuntimeException::new);

        if (userDTO.getEmail() != null &&
                !user.getEmail().equals(userDTO.getEmail())) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getTelefone() != null &&
                !user.getTelefone().equals(userDTO.getTelefone())) {
            user.setTelefone(userDTO.getTelefone());
        }
        if (userDTO.getEndereco() != null &&
                !user.getEndereco().equals(userDTO.getEndereco())) {
            user.setEndereco(userDTO.getEndereco());
        }

        user = this.userRepository.save(user);
        return UserDTO.convert(user);
    }

    public Page<UserDTO> getAllPage(Pageable page) {
        Page<User> users = this.userRepository.findAll(page);
        return users.map(UserDTO::convert);
    }

}
