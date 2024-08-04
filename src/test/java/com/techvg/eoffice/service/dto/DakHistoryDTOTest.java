package com.techvg.eoffice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DakHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DakHistoryDTO.class);
        DakHistoryDTO dakHistoryDTO1 = new DakHistoryDTO();
        dakHistoryDTO1.setId(1L);
        DakHistoryDTO dakHistoryDTO2 = new DakHistoryDTO();
        assertThat(dakHistoryDTO1).isNotEqualTo(dakHistoryDTO2);
        dakHistoryDTO2.setId(dakHistoryDTO1.getId());
        assertThat(dakHistoryDTO1).isEqualTo(dakHistoryDTO2);
        dakHistoryDTO2.setId(2L);
        assertThat(dakHistoryDTO1).isNotEqualTo(dakHistoryDTO2);
        dakHistoryDTO1.setId(null);
        assertThat(dakHistoryDTO1).isNotEqualTo(dakHistoryDTO2);
    }
}
