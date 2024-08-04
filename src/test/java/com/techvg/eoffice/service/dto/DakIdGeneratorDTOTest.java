package com.techvg.eoffice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DakIdGeneratorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DakIdGeneratorDTO.class);
        DakIdGeneratorDTO dakIdGeneratorDTO1 = new DakIdGeneratorDTO();
        dakIdGeneratorDTO1.setId(1L);
        DakIdGeneratorDTO dakIdGeneratorDTO2 = new DakIdGeneratorDTO();
        assertThat(dakIdGeneratorDTO1).isNotEqualTo(dakIdGeneratorDTO2);
        dakIdGeneratorDTO2.setId(dakIdGeneratorDTO1.getId());
        assertThat(dakIdGeneratorDTO1).isEqualTo(dakIdGeneratorDTO2);
        dakIdGeneratorDTO2.setId(2L);
        assertThat(dakIdGeneratorDTO1).isNotEqualTo(dakIdGeneratorDTO2);
        dakIdGeneratorDTO1.setId(null);
        assertThat(dakIdGeneratorDTO1).isNotEqualTo(dakIdGeneratorDTO2);
    }
}
