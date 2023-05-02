package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendService;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        log.info("Получен GET-запрос /users для вывода списка друзей пользователя с Id = {}", id);
        return friendService.getFriends(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Получен PUT-запрос /users, чтобы добавить пользователя с ID={}", friendId);
        friendService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Получен PUT-запрос /users, чтобы удалить пользователя с ID={}", friendId);
        friendService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getOtherFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Получен GET-запрос /users для вывода списка общих друзей друзей пользователя с Id = {}", id);
        return friendService.getCommonFriends(id, otherId);
    }
}
