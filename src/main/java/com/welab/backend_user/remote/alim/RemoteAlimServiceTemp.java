//package com.welab.backend_user.remote.alim;
//
//import com.welab.backend_user.remote.alim.dto.AlimSendSmsDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//@RequiredArgsConstructor
//public class RemoteAlimServiceTemp {
//    private final RestTemplate restTemplate;
//
//    public String callAlimHello() {
//        // ip 주소 대신 서비스명 사용 (application-loca.yml 파일에 작성됨)
//        //return restTemplate.getForObject("http://alim-service/backend/alim/v1/hello", String.class);
//        return restTemplate.getForObject("http://backend-alim/backend/alim/v1/hello", String.class);
//    }
//
//    public AlimSendSmsDto.Response sendSms(AlimSendSmsDto.Request request) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<AlimSendSmsDto.Request> httpRequest = new HttpEntity<>(request, headers);
//        ResponseEntity<AlimSendSmsDto.Response> httpResponse = restTemplate.exchange(
//          "http://backend-alim/backend/alim/v1/sms",
//                HttpMethod.POST,
//                httpRequest,
//                AlimSendSmsDto.Response.class
//        );
//
//        return httpResponse.getBody();
//    }
//
//}
