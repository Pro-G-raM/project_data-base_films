package com.example.demo.repository;

import com.example.demo.model.Actor;
import com.example.demo.model.ActorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query("SELECT a.fullName, COUNT(f) AS filmCount " +
            "FROM Actor a JOIN a.films f " +
            "GROUP BY a.id " +
            "HAVING COUNT(f) >= :minFilms")
    List<Object[]> findActorsWithFilmCounts(@Param("minFilms") int minFilms);

    @Query("SELECT a FROM Actor a JOIN a.films f WHERE f.id = :filmId")
    List<Actor> findByFilmId(@Param("filmId") Long filmId);

    @Query("SELECT DISTINCT a FROM Actor a " +
            "JOIN Director d ON d.fullName = a.fullName " +
            "AND d.birthDate = a.birthDate")
    List<Actor> findActorsWhoAreDirectorsWithSameNameAndBirthDate();

    @Query("SELECT new com.example.demo.model.ActorDTO(a.fullName, COUNT(f)) " +
            "FROM Actor a JOIN a.films f " +
            "GROUP BY a.fullName " +
            "HAVING COUNT(f) >= :minFilms")
    List<ActorDTO> findActorsByMinFilms(@Param("minFilms") int minFilms);





}
