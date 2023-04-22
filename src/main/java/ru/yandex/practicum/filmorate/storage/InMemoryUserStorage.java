package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("inMemoryUserStorage")
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users;
    private Integer newId;

    public InMemoryUserStorage() {
        newId = 0;
        users = new HashMap<>();
    }

    @Override
    public User createUser(User user) {
        if (isValidUser(user)) {
            user.setId(++newId);
            users.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundIdException("Такого id - " + user.getId() + " не существует");
        }
        if (users.containsValue(user) || isValidUser(user)) {
            users.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public User deleteUser(Integer id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundIdException("Такого id - " + id + " не существует");
        }
        for (User user : users.values()) {
            user.getFriends().remove(id);
        }
        return users.remove(id);
    }

    @Override
    public User getById(Integer id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundIdException("Такого id - " + id + " не существует");
        }
        return users.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    private boolean isValidUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        return true;
    }
}
