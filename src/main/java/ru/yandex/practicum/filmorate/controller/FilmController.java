package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/films")
    public List<Film> findAll() {
        log.info("Получен GET-запрос /films для вывода списка всех фильмов");
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        log.info("Получен GET-запрос /films для вывода фильма с Id = {}", id);
        return filmService.getById(id);
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {
        log.info("Получен POST-запрос /films, чтобы добавить фильм");
        return filmService.createFilm(film);
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) {
        log.info("Получен PUT-запрос /films, чтобы обновить фильм с ID={}", film.getId());
        return filmService.updateFilm(film);
    }

    @DeleteMapping("/films/{id}")
    public Film delete(@PathVariable Integer id) {
        log.info("Получен DELETE-запрос /films, чтобы удалить фильм с ID={}", id);
        return filmService.deleteFilm(id);
    }
}
