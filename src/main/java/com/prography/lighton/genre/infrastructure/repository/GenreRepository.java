package com.prography.lighton.genre.infrastructure.repository;

import com.prography.lighton.genre.domain.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
