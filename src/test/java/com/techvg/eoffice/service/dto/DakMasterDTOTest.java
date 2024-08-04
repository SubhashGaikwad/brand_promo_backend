package com.techvg.eoffice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DakMasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DakMasterDTO.class);
        DakMasterDTO dakMasterDTO1 = new DakMasterDTO();
        dakMasterDTO1.setId(1L);
        DakMasterDTO dakMasterDTO2 = new DakMasterDTO();
        assertThat(dakMasterDTO1).isNotEqualTo(dakMasterDTO2);
        dakMasterDTO2.setId(dakMasterDTO1.getId());
        assertThat(dakMasterDTO1).isEqualTo(dakMasterDTO2);
        dakMasterDTO2.setId(2L);
        assertThat(dakMasterDTO1).isNotEqualTo(dakMasterDTO2);
        dakMasterDTO1.setId(null);
        assertThat(dakMasterDTO1).isNotEqualTo(dakMasterDTO2);
    }
}
