package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@RestController
@Slf4j
@AllArgsConstructor
public class GenreController {
    private GenreService genreService;

    @GetMapping("/genres")
    public Collection<Genre> getGenres() {
        log.info("Получен GET-запрос /genres на получение всех жанров");
        return genreService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable Integer id) {
        log.info("Получен GET-запрос /genres на получение жанра с ID={}", id);
        return genreService.getGenreById(id);
    }
}
