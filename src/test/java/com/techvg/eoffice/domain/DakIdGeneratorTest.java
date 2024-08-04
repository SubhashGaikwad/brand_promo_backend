package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DakIdGeneratorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DakIdGenerator.class);
        DakIdGenerator dakIdGenerator1 = new DakIdGenerator();
        dakIdGenerator1.setId(1L);
        DakIdGenerator dakIdGenerator2 = new DakIdGenerator();
        dakIdGenerator2.setId(dakIdGenerator1.getId());
        assertThat(dakIdGenerator1).isEqualTo(dakIdGenerator2);
        dakIdGenerator2.setId(2L);
        assertThat(dakIdGenerator1).isNotEqualTo(dakIdGenerator2);
        dakIdGenerator1.setId(null);
        assertThat(dakIdGenerator1).isNotEqualTo(dakIdGenerator2);
    }
}
