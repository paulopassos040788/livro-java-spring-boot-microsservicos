package br.com.passos.user_api.controller;

import br.com.passos.user_api.dto.UserDTO;
import br.com.passos.user_api.model.User;
import br.com.passos.user_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody @Valid UserDTO userDTO) {
        User user = User.convert(this.userService.save(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.convert(user));
    }

    @GetMapping
    public  ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = this.userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = this.userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping("/cpf")
    public ResponseEntity<UserDTO> getUserByCpf(@RequestParam String cpf) {
        UserDTO userDTO = this.userService.findByCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUser(@RequestParam(name = "nome") String nome) {
        List<UserDTO> userDTOS = this.userService.queryByName(nome);
        return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
    }


}
