package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> findAll() {
        log.info("Получен GET-запрос /users для вывода списка всех пользователей");
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("Получен GET-запрос /users для вывода пользователя с Id = {}", id);
        return userService.getById(id);
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        log.info("Получен POST-запрос /users, чтобы добавить пользователя");
        return userService.createUser(user);
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) {
        log.info("Получен PUT-запрос /users, чтобы обновить пользователя с ID={}", user.getId());
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public User delete(@PathVariable Integer id) {
        log.info("Получен DELETE-запрос /users, чтобы удалить фильм с ID={}", id);
        return userService.deleteUser(id);
    }
}
