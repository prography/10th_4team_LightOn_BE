package com.prography.lighton.member.users.application;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import com.prography.lighton.member.presentation.dto.request.EditMemberGenreRequestDTO;
import com.prography.lighton.member.users.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.users.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.member.users.presentation.dto.response.GetPreferredGenreResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManagePreferredGenreUseCaseImpl implements ManagePreferredGenreUseCase {

    private final MemberRepository memberRepository;
    private final PreferredGenreRepository preferredGenreRepository;

    private final GenreCache genreCache;

    @Override
    @Transactional
    public void editMemberGenre(Long memberId, EditMemberGenreRequestDTO request) {
        Member member = memberRepository.getMemberById(memberId);
        deletePreviousPreferredGenres(memberId);

        List<PreferredGenre> preferredGenres = genreCache.getGenresByNameOrThrow(request.genres()).stream()
                .map((genre -> PreferredGenre.of(member, genre)))
                .toList();
        member.editPreferredGenres(preferredGenres);

        preferredGenreRepository.saveAll(preferredGenres);
    }

    @Override
    public GetPreferredGenreResponseDTO getPreferredGenre(Long memberId) {
        List<Genre> genres = preferredGenreRepository.findAllByMemberId(memberId).stream()
                .map(PreferredGenre::getGenre)
                .toList();

        return GetPreferredGenreResponseDTO.of(genres);
    }

    private void deletePreviousPreferredGenres(Long memberId) {
        preferredGenreRepository.deleteAllByMemberId(memberId);
    }
}
