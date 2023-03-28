package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films;
    private Integer newId;

    public InMemoryFilmStorage() {
        newId = 0;
        films = new HashMap<>();
    }

    @Override
    public Film createFilm(Film film) {
        if (isValidFilm(film)) {
            film.setId(++newId);
            films.put(film.getId(), film);
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundIdException("Такого id - " + film.getId() + " не существует");
        }
        if (films.containsValue(film) || isValidFilm(film)) {
            films.put(film.getId(), film);
        }
        return film;
    }

    @Override
    public Film deleteFilm(Integer id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundIdException("Такого id - " + id + " не существует");
        }
        for (Film film : films.values()) {
            film.getLikes().remove(id);
        }
        return films.remove(id);
    }

    @Override
    public Film getById(Integer id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundIdException("Такого id - " + id + " не существует");
        }
        return films.get(id);
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    private boolean isValidFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription() == null || (film.getDescription().length()) > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не должна быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() == null || film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        return true;
    }
}
