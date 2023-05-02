package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {
    private User user;
    private UserController userController;
    private FriendStorage friendStorage;
    UserStorage userStorage;
    UserService userService = new UserService(userStorage);

    @BeforeEach
    public void beforeEach() {
        userController = new UserController(userService);

        user.setEmail("123@ya.ru");
        user.setLogin("wolf");
        user.setName("Petr");
        user.setBirthday(LocalDate.of(1999, 9, 9));
    }

    @Test
    public void shouldAddUserCorrect() {
        User user1 = userController.create(user);
        assertEquals(user, user1, "Пользователи должны совпадать");
        assertEquals(1, userController.findAll().size(), "В списке больше одного пользователя");
    }

    @Test
    public void shouldAddUserNoEmail() {
        user.setEmail("");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.findAll().size(), "В списке не должно быть пользователей");
    }

    @Test
    public void shouldAddUserFailedEmail() {
        user.setEmail("123ya.ru");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.findAll().size(), "В списке не должно быть пользователей");
    }

    @Test
    public void shouldAddUserNoLogin() {
        user.setLogin("");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.findAll().size(), "В списке не должно быть пользователей");
    }

    @Test
    public void shouldAddUserSpaceLogin() {
        user.setLogin("wolf 123");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.findAll().size(), "В списке не должно быть пользователей");
    }

    @Test
    public void shouldAddUserFailedName() {
        user.setName("");
        User user1 = userController.create(user);
        assertEquals(user.getLogin(), user1.getName(), "Логин и Имя должны совпадать");
        assertEquals(1, userController.findAll().size(), "В списке не должно быть пользователей");
    }

    @Test
    public void shouldAddUserFailedBirthday() {
        user.setBirthday(LocalDate.of(2024, 2, 2));
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.findAll().size(), "В списке не должно быть пользователей");
    }
}