package com.techvg.eoffice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HearingDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HearingDetailsDTO.class);
        HearingDetailsDTO hearingDetailsDTO1 = new HearingDetailsDTO();
        hearingDetailsDTO1.setId(1L);
        HearingDetailsDTO hearingDetailsDTO2 = new HearingDetailsDTO();
        assertThat(hearingDetailsDTO1).isNotEqualTo(hearingDetailsDTO2);
        hearingDetailsDTO2.setId(hearingDetailsDTO1.getId());
        assertThat(hearingDetailsDTO1).isEqualTo(hearingDetailsDTO2);
        hearingDetailsDTO2.setId(2L);
        assertThat(hearingDetailsDTO1).isNotEqualTo(hearingDetailsDTO2);
        hearingDetailsDTO1.setId(null);
        assertThat(hearingDetailsDTO1).isNotEqualTo(hearingDetailsDTO2);
    }
}
