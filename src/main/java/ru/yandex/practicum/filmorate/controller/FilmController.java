package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@RestController
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

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

    @GetMapping("/films/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Получен GET-запрос /films/popular для вывода популярных фильмов");
        return filmService.getPopular(count);
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

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Получен PUT-запрос /films/{id}/like, чтобы поставить лайк от пользователей с ID={} и UserID={}",
                id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}")
    public Film delete(@PathVariable Integer id) {
        log.info("Получен DELETE-запрос /films, чтобы удалить фильм с ID={}", id);
        return filmService.deleteFilm(id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Получен PUT-запрос /films/{id}/like, чтобы удалить лайк от пользователей с ID={} и UserID={}",
                id, userId);
        filmService.deleteLike(id, userId);
    }
}
