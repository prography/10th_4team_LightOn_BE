package com.prography.lighton.member.application;

import com.prography.lighton.member.presentation.dto.request.EditMemberGenreRequestDTO;
import com.prography.lighton.member.presentation.dto.response.GetPreferredGenreResponseDTO;

public interface ManagePreferredGenreUseCase {

    void editMemberGenre(final Long memberId, final EditMemberGenreRequestDTO request);

    GetPreferredGenreResponseDTO getPreferredGenre(final Long memberId);
}
