package com.techvg.eoffice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParameterLookupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParameterLookupDTO.class);
        ParameterLookupDTO parameterLookupDTO1 = new ParameterLookupDTO();
        parameterLookupDTO1.setId(1L);
        ParameterLookupDTO parameterLookupDTO2 = new ParameterLookupDTO();
        assertThat(parameterLookupDTO1).isNotEqualTo(parameterLookupDTO2);
        parameterLookupDTO2.setId(parameterLookupDTO1.getId());
        assertThat(parameterLookupDTO1).isEqualTo(parameterLookupDTO2);
        parameterLookupDTO2.setId(2L);
        assertThat(parameterLookupDTO1).isNotEqualTo(parameterLookupDTO2);
        parameterLookupDTO1.setId(null);
        assertThat(parameterLookupDTO1).isNotEqualTo(parameterLookupDTO2);
    }
}
