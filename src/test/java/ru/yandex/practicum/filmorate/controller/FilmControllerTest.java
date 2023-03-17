package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    private Film film;
    private FilmController filmController;

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController();
        film = new Film();
        film.setName("Джентельмены удачи");
        film.setDescription("Классный фильм");
        film.setDuration(135);
        film.setReleaseDate(LocalDate.of(1999, 9, 9));
    }

    @Test
    public void shouldAddFilmCorrect() {
        Film film1 = filmController.create(film);
        assertEquals(film, film1, "Фильмы должны совпадать");
        assertEquals(1, filmController.findAll().size(), "В списке больше одного фильма");
    }

    @Test
    public void shouldAddFilmNoName() {
        film.setName("");
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.findAll().size(), "В списке не должно быть фильмов");
    }

    @Test
    public void shouldAddFilmFailedDescription() {
        film.setDescription("\"Джентельмены удачи\" - культовая советская комедия о несчастном продавце лотерейных " +
                "билетов, который выиграл в карточной игре крупную сумму денег. Он решает переехать в " +
                "Ленинград и стать " +
                "господином своей жизни, но вместо этого попадает втянутым в серию комичных ситуаций. Главная роль в " +
                "фильме исполнена Юрием Никулиным, а сценарий написал Эльдар Рязанов. \"Джентельмены удачи\" стали " +
                "одними из самых любимых фильмов советского кинематографа благодаря своему чудесному юмору, ярким " +
                "персонажам и незабываемым музыкальным номерам.");
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.findAll().size(), "В списке не должно быть фильмов");
    }

    @Test
    public void shouldAddFilmFailedData() {
        film.setReleaseDate(LocalDate.of(1777, 2, 2));
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.findAll().size(), "В списке не должно быть фильмов");
    }

    @Test
    public void shouldAddFilmFailedDuration() {
        film.setDuration(-10);
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.findAll().size(), "В списке не должно быть фильмов");
    }
}