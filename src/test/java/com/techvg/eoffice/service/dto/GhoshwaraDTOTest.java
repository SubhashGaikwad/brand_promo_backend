package com.techvg.eoffice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GhoshwaraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GhoshwaraDTO.class);
        GhoshwaraDTO ghoshwaraDTO1 = new GhoshwaraDTO();
        ghoshwaraDTO1.setId(1L);
        GhoshwaraDTO ghoshwaraDTO2 = new GhoshwaraDTO();
        assertThat(ghoshwaraDTO1).isNotEqualTo(ghoshwaraDTO2);
        ghoshwaraDTO2.setId(ghoshwaraDTO1.getId());
        assertThat(ghoshwaraDTO1).isEqualTo(ghoshwaraDTO2);
        ghoshwaraDTO2.setId(2L);
        assertThat(ghoshwaraDTO1).isNotEqualTo(ghoshwaraDTO2);
        ghoshwaraDTO1.setId(null);
        assertThat(ghoshwaraDTO1).isNotEqualTo(ghoshwaraDTO2);
    }
}
