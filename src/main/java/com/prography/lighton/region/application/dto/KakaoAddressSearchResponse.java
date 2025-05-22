package com.prography.lighton.region.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record KakaoAddressSearchResponse(List<Document> documents) {

    public record Document(@JsonProperty("x") double x,
                           @JsonProperty("y") double y) {
    }
}