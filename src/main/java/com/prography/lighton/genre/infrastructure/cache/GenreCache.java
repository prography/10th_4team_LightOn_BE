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
    private Map<String, Genre> nameCache;

    @PostConstruct
    public void init() {
        List<Genre> allGenres = genreRepository.findAll();

        this.cache = allGenres.stream()
                .collect(Collectors.toUnmodifiableMap(Genre::getId, Function.identity()));
        this.nameCache = allGenres.stream()
                .collect(Collectors.toUnmodifiableMap(Genre::getName, Function.identity()));

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

    public List<Genre> getGenresByNameOrThrow(List<String> genreNames) {
        return genreNames.stream()
                .map(name -> Optional.ofNullable(nameCache.get(name))
                        .orElseThrow(() -> new NoSuchGenreException("존재하지 않는 장르 명 입니다.")))
                .toList();
    }
}
