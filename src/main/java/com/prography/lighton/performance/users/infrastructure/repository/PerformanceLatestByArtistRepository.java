package com.prography.lighton.performance.users.infrastructure.repository;

import java.util.List;

public interface PerformanceLatestByArtistRepository {

    List<Long> findLatestUpcomingIdsByArtists(List<Long> artistIds);
}
