package com.prography.lighton.common.presentation;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @GetMapping("/health")
    public ApiResult<String> healthCheck() {
        return ApiUtils.success("ok");
    }
}
