package com.prography.lighton.performance.users.infrastructure.repository;

import java.util.Optional;

public interface PerformanceLatestByArtistRepository {

    Optional<Long> findLatestUpcomingIdByArtist(long artistId);
}
