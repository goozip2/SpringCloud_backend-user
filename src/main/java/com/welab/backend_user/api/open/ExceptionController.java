package com.welab.backend_user.api.open;

import com.welab.backend_user.common.exception.BadParameter;
import com.welab.backend_user.common.exception.ClientError;
import com.welab.backend_user.common.exception.NotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/error", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ExceptionController {
    @GetMapping("/not-found")
    public String throwNotFound() {
        throw new NotFound("요청한 리소스를 찾을 수 없습니다.");
    }

    @GetMapping("/bad-parameter")
    public String throwBadParameter() {
        throw new BadParameter("요청 파라미터가 잘못되었습니다.");
    }

    @GetMapping("/server-error")
    public String throwException() {
        throw new RuntimeException("서버 내부 오류 테스트");
    }

}
