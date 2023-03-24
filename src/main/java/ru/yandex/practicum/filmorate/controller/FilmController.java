package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {

    private final Map<Integer, Film> films;
    private Integer newId;

    public FilmController() {
        newId = 0;
        films = new HashMap<>();
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        log.info("Получен GET-запрос /films для вывода списка всех фильмов");
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {
        log.info("Получен POST-запрос /films, чтобы добавить фильм с ID={}", newId + 1);
        if (isValidFilm(film)) {
            film.setId(++newId);
            films.put(film.getId(), film);
        }
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) {
        log.info("Получен PUT-запрос /films, чтобы обновить фильм с ID={}", film.getId());
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Такого id не существует");
        }
        if (films.containsValue(film) || isValidFilm(film)) {
            films.put(film.getId(), film);
        }
        return film;
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
