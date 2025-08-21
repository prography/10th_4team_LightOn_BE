package com.prography.lighton.common.fixture;

import static org.mockito.Mockito.mock;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.TemporaryMember;
import com.prography.lighton.member.common.domain.entity.vo.Email;
import com.prography.lighton.member.common.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.common.domain.entity.vo.Password;
import com.prography.lighton.member.common.domain.entity.vo.Phone;
import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.domain.entity.SubRegion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberTestFixture {

    public static final Email TEST_EMAIL = Email.of("test@example.com");
    public static final String TEST_NAME = "홍길동";
    public static final Phone TEST_PHONE = Phone.of("01012345678");
    public static final Password TEST_PASSWORD = Password.encodeAndCreate("Password123!@", mock(PasswordEncoder.class));
    public static final String TEST_PASSWORD_STRING = "Password123!@";
    public static final String ID = "id";
    public static final MarketingAgreement TEST_AGREEMENT = MarketingAgreement.of(true, true, true);
    public static final SocialLoginType TEST_LOGIN_TYPE = SocialLoginType.KAKAO;
    public static final RegionInfo TEST_REGION = RegionInfo.of(mock(Region.class), mock(SubRegion.class));

    public static Member createNormalMember() {
        Member normalMember = createNormalMember(mock(PasswordEncoder.class));
        ReflectionTestUtils.setField(normalMember, ID, 1L);
        return normalMember;
    }

    public static Member createNormalMember(long id) {
        Member normalMember = createNormalMember(mock(PasswordEncoder.class));
        ReflectionTestUtils.setField(normalMember, ID, id);
        return normalMember;
    }

    public static Member createNormalMember(PasswordEncoder encoder) {
        Password password = Password.encodeAndCreate(TEST_PASSWORD_STRING, encoder);
        return Member.toNormalMember(TEST_EMAIL, password, TEST_REGION, TEST_NAME, TEST_PHONE, TEST_LOGIN_TYPE,
                TEST_AGREEMENT);
    }

    public static Member createNormalMemberWith(Email email, Password password, RegionInfo region,
                                                String name, Phone phone, SocialLoginType loginType,
                                                MarketingAgreement agreement) {
        return Member.toNormalMember(email, password, region, name, phone, loginType, agreement);
    }

    public static TemporaryMember createTemporaryMember() {
        return TemporaryMember.of(TEST_EMAIL, TEST_PASSWORD);
    }
}
