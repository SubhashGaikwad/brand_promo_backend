package com.techvg.eoffice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserAccessMapperTest {

    private UserAccessMapper userAccessMapper;

    @BeforeEach
    public void setUp() {
        userAccessMapper = new UserAccessMapperImpl();
    }
}
