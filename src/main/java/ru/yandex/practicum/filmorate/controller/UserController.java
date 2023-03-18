package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    private final Map<Integer, User> users;
    private Integer newId;

    public UserController() {
        newId = 0;
        users = new HashMap<>();
    }

    @GetMapping("/users")
    public List<User> findAll() {
        log.info("Получен GET-запрос /users для вывода списка всех пользователей");
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        log.info("Получен POST-запрос /users, чтобы добавить пользователя с ID={}", user.getId());
        if (isValidUser(user)) {
            user.setId(++newId);
            users.put(user.getId(), user);
        }
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) {
        log.info("Получен PUT-запрос /users, чтобы обновить пользователя с ID={}", user.getId());
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Такого id не существует");
        }
        if (users.containsValue(user) || isValidUser(user)) {
            users.put(user.getId(), user);
        }
        return user;
    }

    private boolean isValidUser(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if ((user.getLogin().isEmpty()) || (user.getLogin().contains(" "))) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        return true;
    }
}
