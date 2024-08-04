package com.techvg.eoffice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DakHistoryMapperTest {

    private DakHistoryMapper dakHistoryMapper;

    @BeforeEach
    public void setUp() {
        dakHistoryMapper = new DakHistoryMapperImpl();
    }
}
