package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HearingDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HearingDetails.class);
        HearingDetails hearingDetails1 = new HearingDetails();
        hearingDetails1.setId(1L);
        HearingDetails hearingDetails2 = new HearingDetails();
        hearingDetails2.setId(hearingDetails1.getId());
        assertThat(hearingDetails1).isEqualTo(hearingDetails2);
        hearingDetails2.setId(2L);
        assertThat(hearingDetails1).isNotEqualTo(hearingDetails2);
        hearingDetails1.setId(null);
        assertThat(hearingDetails1).isNotEqualTo(hearingDetails2);
    }
}
