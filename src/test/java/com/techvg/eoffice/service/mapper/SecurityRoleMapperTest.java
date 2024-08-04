package com.techvg.eoffice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecurityRoleMapperTest {

    private SecurityRoleMapper securityRoleMapper;

    @BeforeEach
    public void setUp() {
        securityRoleMapper = new SecurityRoleMapperImpl();
    }
}
