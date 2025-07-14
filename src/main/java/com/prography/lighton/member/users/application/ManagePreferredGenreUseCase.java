package com.prography.lighton.member.users.application;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.users.presentation.dto.request.EditMemberGenreRequestDTO;
import com.prography.lighton.member.users.presentation.dto.response.GetPreferredGenreResponseDTO;

public interface ManagePreferredGenreUseCase {

    void editMemberGenre(final Member member, final EditMemberGenreRequestDTO request);

    GetPreferredGenreResponseDTO getPreferredGenre(final Member member);
}
