package com.techvg.eoffice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HearingDetailsMapperTest {

    private HearingDetailsMapper hearingDetailsMapper;

    @BeforeEach
    public void setUp() {
        hearingDetailsMapper = new HearingDetailsMapperImpl();
    }
}
