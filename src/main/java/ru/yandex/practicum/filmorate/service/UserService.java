package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;
    private FriendStorage friendStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User getById(Integer id) {
        return userStorage.getById(id);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User deleteUser(Integer id) {
        return userStorage.deleteUser(id);
    }

    public void addFriend(Integer userId, Integer friendId) {
        if (userId.equals(friendId)) {
            throw new ValidationException("Нельзя добавить самого себя в друзья!");
        }
        friendStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        if (userId.equals(friendId)) {
            throw new ValidationException("Нельзя удалить самого себя из друзей!");
        }
        friendStorage.deleteFriend(userId, friendId);
    }

    public List<User> getFriends(Integer userId) {
        List<User> friends = new ArrayList<>();
        if (userId != null) {
            friends = friendStorage.getFriends(userId);
        }
        return friends;
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        User user = userStorage.getById(userId);
        User other = userStorage.getById(otherId);
        Set<User> friends = null;
        if ((user != null) && (other != null)) {
            friends = new HashSet<>(friendStorage.getFriends(userId));
            friends.retainAll(friendStorage.getFriends(otherId));
        }
        return new ArrayList<User>(friends);
    }
}
