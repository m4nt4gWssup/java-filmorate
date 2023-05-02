package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FriendService;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTest {

    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;
    private final FilmService filmService;
    private final UserService userService;
    private final LikeService likeService;
    private final FriendService friendService;
    private User firstUser;
    private User secondUser;
    private User thirdUser;
    private Film firstFilm;
    private Film secondFilm;
    private Film thirdFilm;

    @BeforeEach
    public void beforeEach() {
        firstUser = User.builder()
                .name("First")
                .login("First")
                .email("first@mail.ru")
                .birthday(LocalDate.of(2000, 11, 11))
                .build();

        secondUser = User.builder()
                .name("Second")
                .login("Second")
                .email("second@mail.ru")
                .birthday(LocalDate.of(2000, 11, 12))
                .build();

        thirdUser = User.builder()
                .name("third")
                .login("third")
                .email("third@mail.ru")
                .birthday(LocalDate.of(2000, 11, 13))
                .build();

        firstFilm = Film.builder()
                .name("Первый")
                .description("описание")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(111)
                .build();
        firstFilm.setMpa(new Mpa(1, "G"));
        firstFilm.setGenres(new HashSet<>(Arrays.asList(new Genre(2, "Драма"),
                new Genre(1, "Комедия"))));

        secondFilm = Film.builder()
                .name("Второй")
                .description("описание")
                .releaseDate(LocalDate.of(2000, 2, 10))
                .duration(100)
                .build();
        secondFilm.setMpa(new Mpa(3, "PG-13"));;
        secondFilm.setGenres(new HashSet<>(Arrays.asList(new Genre(6, "Боевик"))));

        thirdFilm = Film.builder()
                .name("Третий")
                .description("описание")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(105)
                .build();
        thirdFilm.setMpa(new Mpa(4, "R"));
        thirdFilm.setGenres(new HashSet<>(Arrays.asList(new Genre(2, "Драма"))));
    }

    @Test
    public void shouldCreateUserAndGetUserById() {
        firstUser = userStorage.createUser(firstUser);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(firstUser.getId()));
        assertThat(userOptional)
                .hasValueSatisfying(user ->
                        assertThat(user)
                                .hasFieldOrPropertyWithValue("id", firstUser.getId())
                                .hasFieldOrPropertyWithValue("name", "First"));
    }

    @Test
    public void shouldGetUsers() {
        firstUser = userStorage.createUser(firstUser);
        secondUser = userStorage.createUser(secondUser);
        List<User> listUsers = userStorage.getAllUsers();
        assertThat(listUsers).contains(firstUser);
        assertThat(listUsers).contains(secondUser);
    }

    @Test
    public void shouldUpdateUser() {
        firstUser = userStorage.createUser(firstUser);
        User updateUser = User.builder()
                .id(firstUser.getId())
                .name("UpdateMisterFirst")
                .login("First")
                .email("1@ya.ru")
                .birthday(LocalDate.of(1980, 12, 23))
                .build();
        Optional<User> testUpdateUser = Optional.ofNullable(userStorage.updateUser(updateUser));
        assertThat(testUpdateUser)
                .hasValueSatisfying(user -> assertThat(user)
                        .hasFieldOrPropertyWithValue("name", "UpdateMisterFirst")
                );
    }

    @Test
    public void shoulddeleteUser() {
        firstUser = userStorage.createUser(firstUser);
        userStorage.deleteUser(firstUser.getId());
        assertThrows(EntityNotFoundException.class, () -> userStorage.getById(firstUser.getId()));
    }

    @Test
    public void shouldCreateFilmAndGetFilmById() {
        firstFilm = filmStorage.createFilm(firstFilm);
        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getById(firstFilm.getId()));
        assertThat(filmOptional)
                .hasValueSatisfying(film -> assertThat(film)
                        .hasFieldOrPropertyWithValue("id", firstFilm.getId())
                        .hasFieldOrPropertyWithValue("name", "Первый")
                );
    }

    @Test
    public void shouldGetFilms() {
        firstFilm = filmStorage.createFilm(firstFilm);
        secondFilm = filmStorage.createFilm(secondFilm);
        thirdFilm = filmStorage.createFilm(thirdFilm);
        List<Film> listFilms = filmStorage.getAllFilms();
        assertThat(listFilms).contains(firstFilm);
        assertThat(listFilms).contains(secondFilm);
        assertThat(listFilms).contains(thirdFilm);
    }

    @Test
    public void shouldUpdateFilm() {
        firstFilm = filmStorage.createFilm(firstFilm);
        Film updateFilm = Film.builder()
                .id(firstFilm.getId())
                .name("Новый первый")
                .description("Новое описание")
                .releaseDate(LocalDate.of(1975, 11, 19))
                .duration(133)
                .build();
        updateFilm.setMpa(new Mpa(1, "G"));
        Optional<Film> testUpdateFilm = Optional.ofNullable(filmStorage.updateFilm(updateFilm));
        assertThat(testUpdateFilm)
                .hasValueSatisfying(film ->
                        assertThat(film)
                                .hasFieldOrPropertyWithValue("name", "Новый первый")
                                .hasFieldOrPropertyWithValue("description", "Новое описание")
                );
    }

    @Test
    public void shoulddeleteFilm() {
        firstFilm = filmStorage.createFilm(firstFilm);
        filmStorage.deleteFilm(firstFilm.getId());
        assertThrows(EntityNotFoundException.class, () -> filmStorage.getById(firstFilm.getId()));
    }

    @Test
    public void shouldAddLike() {
        firstUser = userStorage.createUser(firstUser);
        firstFilm = filmStorage.createFilm(firstFilm);
        likeService.addLike(firstFilm.getId(), firstUser.getId());
        firstFilm = filmStorage.getById(firstFilm.getId());
        assertEquals(1, likeService.getLikes(firstFilm.getId()).size());
    }

    @Test
    public void shouldDeleteLike() {
        firstUser = userStorage.createUser(firstUser);
        secondUser = userStorage.createUser(secondUser);
        firstFilm = filmStorage.createFilm(firstFilm);
        likeService.addLike(firstFilm.getId(), firstUser.getId());
        likeService.addLike(firstFilm.getId(), secondUser.getId());
        likeService.deleteLike(firstFilm.getId(), firstUser.getId());
        firstFilm = filmStorage.getById(firstFilm.getId());
        assertEquals(1, likeService.getLikes(firstFilm.getId()).size());
    }

    @Test
    public void shouldGetPopularFilms() {

        firstUser = userStorage.createUser(firstUser);
        secondUser = userStorage.createUser(secondUser);
        thirdUser = userStorage.createUser(thirdUser);

        firstFilm = filmStorage.createFilm(firstFilm);
        likeService.addLike(firstFilm.getId(), firstUser.getId());

        secondFilm = filmStorage.createFilm(secondFilm);
        likeService.addLike(secondFilm.getId(), firstUser.getId());
        likeService.addLike(secondFilm.getId(), secondUser.getId());
        likeService.addLike(secondFilm.getId(), thirdUser.getId());

        thirdFilm = filmStorage.createFilm(thirdFilm);
        likeService.addLike(thirdFilm.getId(), firstUser.getId());
        likeService.addLike(thirdFilm.getId(), secondUser.getId());

        List<Film> listFilms = likeService.getPopular(3);

        assertThat(listFilms).hasSize(3);

        assertThat(Optional.of(listFilms.get(0)))
                .hasValueSatisfying(film ->
                        AssertionsForClassTypes.assertThat(film)
                                .hasFieldOrPropertyWithValue("name", "Второй"));

        assertThat(Optional.of(listFilms.get(1)))
                .hasValueSatisfying(film ->
                        AssertionsForClassTypes.assertThat(film)
                                .hasFieldOrPropertyWithValue("name", "Третий"));

        assertThat(Optional.of(listFilms.get(2)))
                .hasValueSatisfying(film ->
                        AssertionsForClassTypes.assertThat(film)
                                .hasFieldOrPropertyWithValue("name", "Первый"));
    }

    @Test
    public void shouldAddFriend() {
        firstUser = userStorage.createUser(firstUser);
        secondUser = userStorage.createUser(secondUser);
        friendService.addFriend(firstUser.getId(), secondUser.getId());
        assertThat(friendService.getFriends(firstUser.getId())).hasSize(1);
        assertThat(friendService.getFriends(firstUser.getId())).contains(secondUser);
    }

    @Test
    public void shouldDeleteFriend() {
        firstUser = userStorage.createUser(firstUser);
        secondUser = userStorage.createUser(secondUser);
        thirdUser = userStorage.createUser(thirdUser);
        friendService.addFriend(firstUser.getId(), secondUser.getId());
        friendService.addFriend(firstUser.getId(), thirdUser.getId());
        friendService.deleteFriend(firstUser.getId(), secondUser.getId());
        assertThat(friendService.getFriends(firstUser.getId())).hasSize(1);
        assertThat(friendService.getFriends(firstUser.getId())).contains(thirdUser);
    }

    @Test
    public void shouldGetFriends() {
        firstUser = userStorage.createUser(firstUser);
        secondUser = userStorage.createUser(secondUser);
        thirdUser = userStorage.createUser(thirdUser);
        friendService.addFriend(firstUser.getId(), secondUser.getId());
        friendService.addFriend(firstUser.getId(), thirdUser.getId());
        assertThat(friendService.getFriends(firstUser.getId())).hasSize(2);
        assertThat(friendService.getFriends(firstUser.getId())).contains(secondUser, thirdUser);
    }

    @Test
    public void shouldGetCommonFriends() {
        firstUser = userStorage.createUser(firstUser);
        secondUser = userStorage.createUser(secondUser);
        thirdUser = userStorage.createUser(thirdUser);
        friendService.addFriend(firstUser.getId(), secondUser.getId());
        friendService.addFriend(firstUser.getId(), thirdUser.getId());
        friendService.addFriend(secondUser.getId(), firstUser.getId());
        friendService.addFriend(secondUser.getId(), thirdUser.getId());
        assertThat(friendService.getCommonFriends(firstUser.getId(), secondUser.getId())).hasSize(1);
        assertThat(friendService.getCommonFriends(firstUser.getId(), secondUser.getId()))
                .contains(thirdUser);
    }
}