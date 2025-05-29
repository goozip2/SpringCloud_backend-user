package com.welab.backend_user.remote.alim;

import com.welab.backend_user.remote.alim.dto.AlimSendSmsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "backend-alim", path = "/backend/alim/v1")
public interface RemoteAlimService {
    // alim 프로젝트의 BackendAlimController와 형식 통일
    @GetMapping(value = "/hello")
    public String hello();
    @PostMapping(value = "/sms")
    public AlimSendSmsDto.Response sendSms(@RequestBody AlimSendSmsDto.Request request);
}
