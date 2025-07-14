package com.prography.lighton.member.users.presentation.dto.request;

import java.util.List;

public record EditMemberGenreRequestDTO(
        List<String> genres
) {
}
