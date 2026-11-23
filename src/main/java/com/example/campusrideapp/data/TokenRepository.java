package com.example.campusrideapp.data;

import com.example.campusrideapp.domains.ApiToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<ApiToken, UUID> {
    boolean existsByIdAndRevokedAtIsNullAndExpiresAtAfter(UUID id, Instant now);
}
