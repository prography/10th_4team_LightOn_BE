package com.prography.lighton.member.presentation.dto.response;

import com.prography.lighton.genre.domain.entity.Genre;
import java.util.List;

public record GetPreferredGenreResponseDTO(
        List<GenreDTO> genres
) {
    public static GetPreferredGenreResponseDTO of(List<Genre> genres) {
        List<GenreDTO> genreDTOs = genres.stream()
                .map(genre -> GenreDTO.of(genre.getName(), genre.getImageUrl()))
                .toList();

        return new GetPreferredGenreResponseDTO(genreDTOs);
    }

    public record GenreDTO(
            String name,
            String imageUrl
    ) {
        public static GenreDTO of(String name, String imageUrl) {
            return new GenreDTO(name, imageUrl);
        }
    }
}
