package com.welab.backend_user.domain.repository;

import com.welab.backend_user.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    //SiteUser 반환 대신, Optional 사용으로 null 처리 안정화
    //Optional<SiteUser> findByUserId(String userId);
    SiteUser findByUserId(String userId);
}
