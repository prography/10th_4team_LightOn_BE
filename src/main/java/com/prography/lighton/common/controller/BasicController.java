package com.prography.lighton.common.controller;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BasicController {
    @GetMapping("/health")
    public ApiResult<String> healthCheck() {
        return ApiUtils.success("ok");
    }
}
