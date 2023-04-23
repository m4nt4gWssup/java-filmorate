package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        log.error(message);
    }
}
