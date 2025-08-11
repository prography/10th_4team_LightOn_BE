package com.prography.lighton.performance.common.domain.exception;

import com.prography.lighton.common.exception.base.BusinessConflictException;

public class MasterArtistCannotBeRemovedException extends BusinessConflictException {
    private static final String message = "공연 등록 아티스트는 삭제할 수 없습니다.";

    public MasterArtistCannotBeRemovedException() {
        super(message);
    }
}
