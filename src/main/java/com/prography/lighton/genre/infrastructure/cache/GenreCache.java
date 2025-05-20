package com.prography.lighton.genre.infrastructure.cache;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreCache {

    private final GenreRepository genreRepository;

    private Map<Long, Genre> cache;

    @PostConstruct
    public void init() {
        this.cache = genreRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableMap(Genre::getId, Function.identity()));

        if (cache.isEmpty()) {
            throw new IllegalStateException("GenreCache 초기화 실패: 캐시 비어 있음");
        }

        log.info("장르 엔티티 캐시 초기화 성공 - size: {}", cache.size());
    }

    public List<Genre> getGenresOrThrow(List<Long> genreIds) {
        return genreIds.stream()
                .map(id -> Optional.ofNullable(cache.get(id))
                        .orElseThrow(NoSuchGenreException::new))
                .toList();
    }
}
