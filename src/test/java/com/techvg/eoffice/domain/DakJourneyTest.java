package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DakJourneyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DakJourney.class);
        DakJourney dakJourney1 = new DakJourney();
        dakJourney1.setId(1L);
        DakJourney dakJourney2 = new DakJourney();
        dakJourney2.setId(dakJourney1.getId());
        assertThat(dakJourney1).isEqualTo(dakJourney2);
        dakJourney2.setId(2L);
        assertThat(dakJourney1).isNotEqualTo(dakJourney2);
        dakJourney1.setId(null);
        assertThat(dakJourney1).isNotEqualTo(dakJourney2);
    }
}
