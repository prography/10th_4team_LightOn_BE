package com.prography.lighton.genre.application.service;

import com.prography.lighton.genre.application.exception.NoSuchGenreException;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    private Map<Long, Genre> cache;

    @PostConstruct
    public void init() {
        this.cache = genreRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableMap(Genre::getId, Function.identity()));
    }

    public List<Genre> getGenresOrThrow(List<Long> genreIds) {
        return genreIds.stream()
                .map(id -> Optional.ofNullable(cache.get(id))
                        .orElseThrow(NoSuchGenreException::new))
                .toList();
    }
}
