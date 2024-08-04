package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GhoshwaraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ghoshwara.class);
        Ghoshwara ghoshwara1 = new Ghoshwara();
        ghoshwara1.setId(1L);
        Ghoshwara ghoshwara2 = new Ghoshwara();
        ghoshwara2.setId(ghoshwara1.getId());
        assertThat(ghoshwara1).isEqualTo(ghoshwara2);
        ghoshwara2.setId(2L);
        assertThat(ghoshwara1).isNotEqualTo(ghoshwara2);
        ghoshwara1.setId(null);
        assertThat(ghoshwara1).isNotEqualTo(ghoshwara2);
    }
}
