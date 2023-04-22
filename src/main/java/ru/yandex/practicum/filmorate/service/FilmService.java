package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundIdException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private LikeStorage likeStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeStorage;
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getById(Integer id) {
        return filmStorage.getById(id);
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film deleteFilm(Integer id) {
        return filmStorage.deleteFilm(id);
    }

    public void addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getById(filmId);
        if (film != null) {
            if (userStorage.getById(userId) != null) {
                likeStorage.addLike(filmId, userId);
            } else {
                throw new UserNotFoundIdException("Пользователь c ID=" + userId + " не найден");
            }
        } else {
            throw new FilmNotFoundIdException("Фильм c ID=" + filmId + " не найден");
        }
    }

    public void deleteLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getById(filmId);
        if (film != null) {
            if (film.getLikes().contains(userId)) {
                likeStorage.deleteLike(filmId, userId);
            } else {
                throw new UserNotFoundIdException("Лайк от пользователя c ID=" + userId + " не найден");
            }
        } else {
            throw new FilmNotFoundIdException("Фильм c ID=" + filmId + " не найден");
        }
    }

    public List<Film> getPopular(Integer count) {
        return likeStorage.getPopular(count);
    }
}
