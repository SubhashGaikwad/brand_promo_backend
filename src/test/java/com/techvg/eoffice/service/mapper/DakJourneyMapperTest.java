package com.techvg.eoffice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DakJourneyMapperTest {

    private DakJourneyMapper dakJourneyMapper;

    @BeforeEach
    public void setUp() {
        dakJourneyMapper = new DakJourneyMapperImpl();
    }
}
