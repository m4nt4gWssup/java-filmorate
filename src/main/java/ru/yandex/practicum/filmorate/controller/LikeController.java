package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.LikeService;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Получен PUT-запрос /films/{id}/like, чтобы поставить лайк от пользователей с ID={} и UserID={}",
                id, userId);
        likeService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Получен PUT-запрос /films/{id}/like, чтобы удалить лайк от пользователей с ID={} и UserID={}",
                id, userId);
        likeService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Получен GET-запрос /films/popular для вывода популярных фильмов");
        return likeService.getPopular(count);
    }
}
