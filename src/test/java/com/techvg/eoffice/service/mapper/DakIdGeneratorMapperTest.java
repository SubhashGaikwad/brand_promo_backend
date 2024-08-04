package com.techvg.eoffice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DakIdGeneratorMapperTest {

    private DakIdGeneratorMapper dakIdGeneratorMapper;

    @BeforeEach
    public void setUp() {
        dakIdGeneratorMapper = new DakIdGeneratorMapperImpl();
    }
}
