package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundIdException extends IllegalArgumentException {
    public UserNotFoundIdException(String message) {
        log.error(message);
    }
}
