package com.prography.lighton.performance.common.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prography.lighton.performance.common.domain.entity.vo.Info;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InfoTest {

    @Test
    @DisplayName("Info 객체를 정상적으로 생성할 수 있다.")
    void should_create_info() {
        Info info = Info.of("제목", "설명", "장소", "주의사항", "포스터");
        assertEquals("제목", info.getTitle());
        assertEquals("설명", info.getDescription());
        assertEquals("장소", info.getPlace());
        assertEquals("주의사항", info.getNotice());
        assertEquals("포스터", info.getPosterUrl());
    }

    @Test
    @DisplayName("Info 생성 시 제목이 비어 있으면 예외가 발생한다.")
    void should_throw_exception_when_title_is_blank() {
        assertThrows(IllegalArgumentException.class, () ->
                Info.of("", "설명", "장소", "주의사항", "포스터")
        );
    }

    @Test
    @DisplayName("Info 생성 시 설명이 비어 있으면 예외가 발생한다.")
    void should_throw_exception_when_description_is_blank() {
        assertThrows(IllegalArgumentException.class, () ->
                Info.of("제목", "", "장소", "주의사항", "포스터")
        );
    }

    @Test
    @DisplayName("Info 생성 시 장소가 비어 있으면 예외가 발생한다.")
    void should_throw_exception_when_place_is_blank() {
        assertThrows(IllegalArgumentException.class, () ->
                Info.of("제목", "설명", "", "주의사항", "포스터")
        );
    }

    @Test
    @DisplayName("Info 생성 시 주의사항이 비어 있어도 생성된다.")
    void should_create_info_when_notice_is_blank() {
        Info info = Info.of("제목", "설명", "장소", "", "포스터");
        assertEquals("", info.getNotice());
    }

    @Test
    @DisplayName("Info 생성 시 포스터 URL이 비어 있어도 생성된다.")
    void should_create_info_when_poster_url_is_blank() {
        Info info = Info.of("제목", "설명", "장소", "주의사항", "");
        assertEquals("", info.getPosterUrl());
    }
}
