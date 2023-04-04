package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
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
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getFriends(Integer userId) {
        User user = userStorage.getById(userId);
        List<User> friends = new ArrayList<>();
        if (user.getFriends() != null) {
            for (Integer currentId : user.getFriends()) {
                friends.add(userStorage.getById(currentId));
            }
        }
        return friends;
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        User user = userStorage.getById(userId);
        User other = userStorage.getById(otherId);
        Set<Integer> friends = new HashSet<>(user.getFriends());
        friends.retainAll(other.getFriends());
        return friends.stream().
                map(userStorage::getById)
                .collect(Collectors.toList());
    }
}
