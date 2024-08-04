package com.techvg.eoffice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DakMasterMapperTest {

    private DakMasterMapper dakMasterMapper;

    @BeforeEach
    public void setUp() {
        dakMasterMapper = new DakMasterMapperImpl();
    }
}
