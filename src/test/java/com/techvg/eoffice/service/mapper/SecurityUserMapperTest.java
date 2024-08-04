package com.techvg.eoffice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecurityUserMapperTest {

    private SecurityUserMapper securityUserMapper;

    @BeforeEach
    public void setUp() {
        securityUserMapper = new SecurityUserMapperImpl();
    }
}
