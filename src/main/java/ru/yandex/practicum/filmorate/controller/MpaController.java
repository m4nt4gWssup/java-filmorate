package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@RestController
@Slf4j
@AllArgsConstructor
public class MpaController {
    private MpaService mpaService;

    @GetMapping("/mpa")
    public Collection<Mpa> getAllMpa() {
        log.info("Получен GET-запрос /mpa на получение всех рейтингов");
        return mpaService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpaById(@PathVariable Integer id) {
        log.info("Получен GET-запрос /mpa на получение рейтинга с ID={}", id);
        return mpaService.getMpaById(id);
    }
}
