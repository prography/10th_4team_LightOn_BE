package com.prography.lighton.genre.application.service;

import com.prography.lighton.genre.application.exception.NoSuchGenreException;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.repository.GenreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<Genre> getGenresOrThrow(List<Long> genreIds) {
        List<Genre> genres = genreRepository.findAllById(genreIds);

        if (genres.size() != genreIds.size()) {
            throw new NoSuchGenreException();
        }

        return genres;
    }
}
