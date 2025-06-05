package com.welab.backend_user.service.aop;

import com.welab.backend_user.common.type.ActionAndId;
import com.welab.backend_user.domain.SiteUser;
import com.welab.backend_user.domain.event.SiteUserInfoEvent;
import com.welab.backend_user.domain.repository.SiteUserRepository;
import com.welab.backend_user.event.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
// SiteUserService 내에서 *AndNotify로 끝나는 메서드가 실행된 후,
// 결과로 반환된 ActionAndId를 기반으로 이벤트를 발행하는 AOP 클래스.
public class UserUpdateEventAop {
    private final SiteUserRepository siteUserRepository;
    private final KafkaMessageProducer kafkaMessageProducer;
    // @AfterReturning: *AndNotify(..) 형식의 메소드가 성공적으로 실행된 후 동작
    // 리턴된 ActionAndId 값을 받아 이벤트 전송 함수(publishUserUpdateEvent) 실행.
    @AfterReturning(
            value = "execution(* com.welab.backend_user.service.SiteUserService.*AndNotify(..))",returning = "actionAndId"
    )
    public void publishUserUpdateEvent(JoinPoint joinPoint, ActionAndId actionAndId) {
        publishUserUpdateEvent(actionAndId);
    }

    // 내부에서 사용자 정보를 DB에서 조회한 후(SiteUserRepository),
    // Kafka 이벤트 객체(SiteUserInfoEvent)를 만들어 Kafka로 전송.
    public void publishUserUpdateEvent(ActionAndId actionAndId) {
        try {
            SiteUser siteUser = siteUserRepository.findById(actionAndId.getId())
                    .orElse(null);
            if (siteUser == null) {
                return;
            }
            SiteUserInfoEvent event = SiteUserInfoEvent.fromEntity(actionAndId.getAction(), siteUser);
            kafkaMessageProducer.send(SiteUserInfoEvent.Topic, event);
        } catch (Exception e) {
            log.warn("사용자 정보 업데이트 이벤트를 전송하지 못하였습니다. id={}", actionAndId.getId());
        }
    }
}