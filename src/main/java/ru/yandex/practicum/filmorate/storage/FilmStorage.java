package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film createFilm(Film film);
    Film updateFilm(Film film);
    Film deleteFilm(Integer id);
    Film getById(Integer id);
    List<Film> getAllFilms();
}
