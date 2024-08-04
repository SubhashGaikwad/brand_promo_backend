package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DakHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DakHistory.class);
        DakHistory dakHistory1 = new DakHistory();
        dakHistory1.setId(1L);
        DakHistory dakHistory2 = new DakHistory();
        dakHistory2.setId(dakHistory1.getId());
        assertThat(dakHistory1).isEqualTo(dakHistory2);
        dakHistory2.setId(2L);
        assertThat(dakHistory1).isNotEqualTo(dakHistory2);
        dakHistory1.setId(null);
        assertThat(dakHistory1).isNotEqualTo(dakHistory2);
    }
}
