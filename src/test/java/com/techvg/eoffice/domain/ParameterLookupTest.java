package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParameterLookupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParameterLookup.class);
        ParameterLookup parameterLookup1 = new ParameterLookup();
        parameterLookup1.setId(1L);
        ParameterLookup parameterLookup2 = new ParameterLookup();
        parameterLookup2.setId(parameterLookup1.getId());
        assertThat(parameterLookup1).isEqualTo(parameterLookup2);
        parameterLookup2.setId(2L);
        assertThat(parameterLookup1).isNotEqualTo(parameterLookup2);
        parameterLookup1.setId(null);
        assertThat(parameterLookup1).isNotEqualTo(parameterLookup2);
    }
}
