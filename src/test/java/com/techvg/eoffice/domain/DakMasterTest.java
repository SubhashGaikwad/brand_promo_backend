package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DakMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DakMaster.class);
        DakMaster dakMaster1 = new DakMaster();
        dakMaster1.setId(1L);
        DakMaster dakMaster2 = new DakMaster();
        dakMaster2.setId(dakMaster1.getId());
        assertThat(dakMaster1).isEqualTo(dakMaster2);
        dakMaster2.setId(2L);
        assertThat(dakMaster1).isNotEqualTo(dakMaster2);
        dakMaster1.setId(null);
        assertThat(dakMaster1).isNotEqualTo(dakMaster2);
    }
}
