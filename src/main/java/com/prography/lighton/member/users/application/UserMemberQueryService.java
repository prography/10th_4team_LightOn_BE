package com.prography.lighton.member.users.application;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.member.users.presentation.dto.response.CheckDuplicateEmailResponse;
import com.prography.lighton.member.users.presentation.dto.response.GetPreferredGenreResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserMemberQueryService {

    private final MemberRepository memberRepository;
    private final PreferredGenreRepository preferredGenreRepository;

    public CheckDuplicateEmailResponse checkEmailExists(String email) {
        return CheckDuplicateEmailResponse.of(memberRepository.existsByEmail(email));
    }

    public GetPreferredGenreResponse getPreferredGenre(Member member) {
        List<Genre> genres = preferredGenreRepository.findAllByMember(member).stream()
                .map(PreferredGenre::getGenre)
                .toList();

        return GetPreferredGenreResponse.of(genres);
    }
}
