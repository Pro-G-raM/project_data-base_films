package com.example.demo.controller;
import com.example.demo.model.Actor;
import com.example.demo.model.Film;
import com.example.demo.service.FilmService;
import com.example.demo.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class FilmController {

    @GetMapping("/films")
    public String showMainMenu() {
        return "index"; // Возвращаем страницу главного меню
    }

    @Autowired
    private FilmService filmService;

    @Autowired
    private ActorService actorService;

    @GetMapping("/filmsby")
    public String getFilmsByYears(@RequestParam("years") int years, Model model) {
        List<Film> films = filmService.getFilmsByYears(years);
        model.addAttribute("films", films);
        return "films";
    }


    @GetMapping("/film_act")
    public String getActorsByFilm(@RequestParam(required = false) Long filmId, Model model) {
        List<Film> films = filmService.getAllFilms();
        model.addAttribute("films", films);

        if (filmId != null) {
            List<Actor> actors = filmService.getActorsByFilm(filmId);
            model.addAttribute("actors", actors);
            model.addAttribute("selectedFilmId", filmId);
        } else {
            model.addAttribute("actors", List.of()); // Если фильм не выбран, пустой список
        }

        return "actors_for_film"; // Имя HTML-файла без ".html"
    }

    // Получить актеров, снявшихся в минимум N фильмах
    @GetMapping("/actors/min-films/{minFilms}")
    public String getActorsByFilmCount(@PathVariable int minFilms, Model model) {
        List<Map<String, Object>> actorsWithFilmCounts = actorService.getActorsWithFilmCounts(minFilms);
        model.addAttribute("actors", actorsWithFilmCounts);
        model.addAttribute("minFilms", minFilms); // Передаем N
        return "actors_by_film_count"; // Имя HTML шаблона
    }

    // Получить актеров, которые были режиссерами хотя бы одного фильма
    @GetMapping("/actors/directors")
    public String getActorsWhoWereDirectors(Model model) {
        List<Actor> actorsWhoWereDirectors = actorService.getActorsWhoWereDirectors();
        model.addAttribute("actors", actorsWhoWereDirectors);
        return "actors_who_are_directors"; // Имя HTML-шаблона
    }
}