package com.welab.backend_user.api.open;

import com.welab.backend_user.common.dto.ApiResponseDto;
import com.welab.backend_user.domain.dto.SiteUserLoginDto;
import com.welab.backend_user.domain.dto.SiteUserRegisterDto;
import com.welab.backend_user.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserAuthController {
    private final SiteUserService siteUserService;

    @PostMapping(value = "/register")
    public ApiResponseDto<String> sms(@RequestBody @Valid SiteUserRegisterDto registerDto) {
        siteUserService.registerUser(registerDto);
        return ApiResponseDto.defaultOk();
    }

    @PostMapping(value = "/login")
    public ApiResponseDto<String> login(@RequestBody SiteUserLoginDto siteUserLoginDto) {
        boolean loginSuccess = siteUserService.loginUser(siteUserLoginDto);
        if(loginSuccess) {
            return ApiResponseDto.defaultOk();
        } else {
            return ApiResponseDto.createError("FAIL", "로그인 실패");
        }
    }

}
