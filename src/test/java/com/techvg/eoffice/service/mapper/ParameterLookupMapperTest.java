package com.techvg.eoffice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParameterLookupMapperTest {

    private ParameterLookupMapper parameterLookupMapper;

    @BeforeEach
    public void setUp() {
        parameterLookupMapper = new ParameterLookupMapperImpl();
    }
}
