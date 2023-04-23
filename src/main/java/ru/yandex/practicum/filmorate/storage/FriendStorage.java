package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
@AllArgsConstructor
public class FriendStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserStorage userStorage;

    public void addFriend(Integer userId, Integer friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if ((user != null) && (friend != null) && !isFriend(userId, friendId)) {
            String sql = "INSERT INTO friends (user_id, friend_id, status) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, userId, friendId, false);
        }
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if ((user != null) && (friend != null) && isFriend(userId, friendId)) {
            String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
            jdbcTemplate.update(sql, userId, friendId);
            sql = "UPDATE friends SET user_id = ? AND friend_id = ? AND status = ? " +
                    "WHERE user_id = ? AND friend_id = ?";
            jdbcTemplate.update(sql, friendId, userId, false, friendId, userId);
        }
    }

    public List<User> getFriends(Integer userId) {
        User user = userStorage.getById(userId);
        if (user != null) {
            String sql = "SELECT friend_id, email, login, name, birthday FROM friends" +
                    " INNER JOIN users ON friends.friend_id = users.id WHERE friends.user_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                            rs.getInt("friend_id"),
                            rs.getString("email"),
                            rs.getString("login"),
                            rs.getString("name"),
                            rs.getDate("birthday").toLocalDate()),
                    userId
            );
        } else {
            return null;
        }
    }

    public boolean isFriend(Integer userId, Integer friendId) {
        List<User> friends = getFriends(userId);
        if (friends != null) {
            for (User friend : friends) {
                if (friend.getId().equals(friendId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
