package com.welab.backend_user.service;

import com.welab.backend_user.domain.SiteUser;
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
}
