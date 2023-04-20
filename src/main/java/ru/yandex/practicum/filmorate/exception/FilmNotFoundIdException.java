package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilmNotFoundIdException extends IllegalArgumentException {
    public FilmNotFoundIdException(String message) {
        log.error(message);
    }
}
