package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MpaNotFoundIdException extends IllegalArgumentException {
    public MpaNotFoundIdException(String message) {
        log.error(message);
    }
}
