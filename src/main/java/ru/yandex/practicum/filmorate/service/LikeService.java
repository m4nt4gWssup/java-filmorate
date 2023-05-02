package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LikeService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;

    public void addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getById(filmId);
        if (film != null) {
            if (userStorage.getById(userId) != null) {
                likeStorage.addLike(filmId, userId);
            } else {
                throw new EntityNotFoundException("Пользователь c ID=" + userId + " не найден");
            }
        } else {
            throw new EntityNotFoundException("Фильм c ID=" + filmId + " не найден");
        }
    }

    public void deleteLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getById(filmId);
        if (film != null) {
            if (userStorage.getById(userId) != null) {
                likeStorage.deleteLike(filmId, userId);
            } else {
                throw new EntityNotFoundException("Лайк от пользователя c ID=" + userId + " не найден");
            }
        } else {
            throw new EntityNotFoundException("Фильм c ID=" + filmId + " не найден");
        }
    }

    public List<Film> getPopular(Integer count) {
        return likeStorage.getPopular(count);
    }

    public List<Integer> getLikes(Integer filmId) {
        return likeStorage.getLikes(filmId);
    }
}
