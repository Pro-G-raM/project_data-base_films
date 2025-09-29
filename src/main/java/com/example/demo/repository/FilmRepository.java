package com.example.demo.repository;

import com.example.demo.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    // Найти фильмы, выпущенные между двумя датами
    // Кастомный SQL-запрос через @Query
    @Query("SELECT f FROM Film f WHERE f.releaseDate BETWEEN :startDate AND :endDate")
    List<Film> findFilmsByReleaseDateRange(@Param("startDate") Date startDate,
                                           @Param("endDate") Date endDate);

    @Query("SELECT f FROM Film f WHERE f.releaseDate < :date")
    List<Film> findFilmsOlderThan(@Param("date") LocalDate date);

    List<Film> findByReleaseDateAfter(LocalDate releaseDate);
}
