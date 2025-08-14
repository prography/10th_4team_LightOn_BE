package com.prography.lighton.member.users.application.query;

import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_EMAIL;
import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_NAME;
import static com.prography.lighton.common.fixture.MemberTestFixture.createNormalMember;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.prography.lighton.member.users.presentation.dto.response.GetMemberInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetMemberInfoServiceTest {

    GetMemberInfoService service;

    @Test
    @DisplayName("회원 정보를 조회할 수 있다.")
    void should_get_member_info() {
        // Given
        service = new GetMemberInfoService();

        // When
        GetMemberInfoResponse response = service.handle(createNormalMember());

        // Then
        assertNotNull(response);
        assertEquals(TEST_NAME, response.name());
        assertEquals(TEST_EMAIL.getValue(), response.email());
    }

}
