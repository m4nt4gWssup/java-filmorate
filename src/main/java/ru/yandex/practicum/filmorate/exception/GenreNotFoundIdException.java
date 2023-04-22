package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenreNotFoundIdException extends IllegalArgumentException{
    public GenreNotFoundIdException(String message) {
        log.error(message);
    }
}
