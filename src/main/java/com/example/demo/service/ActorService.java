package com.example.demo.service;

import com.example.demo.model.Actor;
import com.example.demo.model.ActorDTO;
import com.example.demo.model.Film;
import com.example.demo.repository.ActorRepository;
import com.example.demo.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private FilmRepository filmRepository;

    // Получить актеров для фильма по ID
    public List<Actor> getActorsForFilm(Long filmId) {
        Film film = filmRepository.findById(filmId).orElse(null);
        if (film == null) {
            // Вы можете выбросить исключение или вернуть пустой список
            throw new RuntimeException("Film not found with ID: " + filmId);
        }
        return film.getActors();
    }

    // Получить актеров, снявшихся в минимум N фильмах
    public List<Map<String, Object>> getActorsWithFilmCounts(int minFilms) {
        // Получаем актеров и считаем их фильмы
        List<Object[]> results = actorRepository.findActorsWithFilmCounts(minFilms);
        List<Map<String, Object>> actorsWithFilmCounts = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("fullName", result[0]);
            map.put("filmCount", result[1]);
            actorsWithFilmCounts.add(map);
        }
        return actorsWithFilmCounts;
    }

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<ActorDTO> getActorsByMinFilms(int minFilms) {
        return actorRepository.findActorsByMinFilms(minFilms);
    }
    // Получить актеров, которые были режиссерами хотя бы одного фильма
    public List<Actor> getActorsWhoWereDirectors() {
        return actorRepository.findActorsWhoAreDirectorsWithSameNameAndBirthDate();
    }
}
