package com.welab.backend_user.service;

import com.welab.backend_user.domain.SiteUser;
import com.welab.backend_user.domain.dto.SiteUserLoginDto;
import com.welab.backend_user.domain.dto.SiteUserRegisterDto;
import com.welab.backend_user.domain.repository.SiteUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;

    @Transactional
    public void registerUser(SiteUserRegisterDto registerDto) {
        SiteUser siteUser = registerDto.toEntity();
        siteUserRepository.save(siteUser);
    }

    public boolean loginUser(SiteUserLoginDto siteUserLoginDto) {

        // toEntity 사용 X: SiteUser Entity nullable false 필드 채워줘야 하는 번거로움
        String userId = siteUserLoginDto.getUserId();
        String password = siteUserLoginDto.getPassword();

        return siteUserRepository.findByUserId(userId)
                //  map: Optional에 값이 있을 경우, 값 변환 (SiteUser → boolean)
                .map(user -> user.getPassword().equals(password))
                // orElse: Optional에 값이 없을 경우, 기본값(false) 반환
                .orElse(false);
    }
}
