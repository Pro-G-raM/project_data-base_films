    package com.example.demo.service;

    import com.example.demo.model.Actor;
    import com.example.demo.model.Film;
    import com.example.demo.repository.FilmRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;


    import java.time.LocalDate;
    import java.util.Date;
    import java.util.List;

    @Service
    public class FilmService {

        @Autowired
        private FilmRepository filmRepository;

        // Получить фильмы, выпущенные в последние два года
        public List<Film> getFilmsByYears(int years) {
            LocalDate cutoffDate = LocalDate.now().minusYears(years);
            return filmRepository.findByReleaseDateAfter(cutoffDate);
        }

        public FilmService(FilmRepository filmRepository) {
            this.filmRepository = filmRepository;
        }
        public List<Film> getAllFilms() {
            return filmRepository.findAll();
        }

        public List<Actor> getActorsByFilm(Long filmId) {
            return filmRepository.findById(filmId)
                    .map(Film::getActors)
                    .orElse(List.of());
        }
    }
