package com.welab.backend_user.remote.alim.dto;

import com.welab.backend_user.domain.SiteUser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlimSendSmsDto {
    @Getter
    @Setter
    public static class Request {
        private String userId;
        private String phoneNumber;
        private String title;
        private String message;

        public static Request fromEntity(SiteUser siteUser) {
            Request request = new Request();
            request.setUserId(siteUser.getUserId());
            request.setPhoneNumber(siteUser.getPhoneNumber());
            request.setTitle("가입축하 메시지 제목입니다.");
            request.setMessage("가입 축하 메시지 본문입니다.");
            return request;
        }
    }
    @Getter @Setter
    public static class Response {
        private String result;
    }
}
