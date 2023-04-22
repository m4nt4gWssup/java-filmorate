package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class Film {

    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Set<Integer> likes;
    private Mpa mpa;
    private Set<Genre> genres;

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration,
                Set<Integer> likes, Mpa mpa, Set<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = likes;
        this.mpa = mpa;
        if (genres == null) {
            this.genres = new HashSet<>();
        } else {
            this.genres = genres;
        }
    }

    public Integer rate() {
        return likes.size();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("release_Date", releaseDate);
        values.put("duration", duration);
        values.put("rating_id", mpa.getId());
        return values;
    }
}
