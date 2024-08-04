package com.techvg.eoffice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DakJourneyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DakJourneyDTO.class);
        DakJourneyDTO dakJourneyDTO1 = new DakJourneyDTO();
        dakJourneyDTO1.setId(1L);
        DakJourneyDTO dakJourneyDTO2 = new DakJourneyDTO();
        assertThat(dakJourneyDTO1).isNotEqualTo(dakJourneyDTO2);
        dakJourneyDTO2.setId(dakJourneyDTO1.getId());
        assertThat(dakJourneyDTO1).isEqualTo(dakJourneyDTO2);
        dakJourneyDTO2.setId(2L);
        assertThat(dakJourneyDTO1).isNotEqualTo(dakJourneyDTO2);
        dakJourneyDTO1.setId(null);
        assertThat(dakJourneyDTO1).isNotEqualTo(dakJourneyDTO2);
    }
}
