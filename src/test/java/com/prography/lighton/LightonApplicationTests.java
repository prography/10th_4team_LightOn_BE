package com.prography.lighton;

import com.prography.lighton.auth.application.impl.GoogleOauth;
import com.prography.lighton.auth.application.impl.KaKaoOauth;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class LightonApplicationTests {

    @Test
    void contextLoads() {
    }

    @MockBean
    private RegionCache regionCache;

    @MockBean
    private GenreCache genreCache;

    @MockBean
    protected GoogleOauth googleOauth;

    @MockBean
    protected KaKaoOauth kaKaoOauth;


}
