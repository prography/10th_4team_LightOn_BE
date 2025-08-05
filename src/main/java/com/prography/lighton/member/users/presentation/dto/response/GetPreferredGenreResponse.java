package com.prography.lighton.member.users.presentation.dto.response;

import com.prography.lighton.genre.domain.entity.Genre;
import java.util.List;

public record GetPreferredGenreResponse(
        List<GenreDTO> genres
) {
    public static GetPreferredGenreResponse of(List<Genre> genres) {
        List<GenreDTO> genreDTOs = genres.stream()
                .map(genre -> GenreDTO.of(genre.getName(), genre.getImageUrl()))
                .toList();

        return new GetPreferredGenreResponse(genreDTOs);
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
