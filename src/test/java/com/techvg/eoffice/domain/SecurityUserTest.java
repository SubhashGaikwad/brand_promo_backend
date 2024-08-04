package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SecurityUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityUser.class);
        SecurityUser securityUser1 = new SecurityUser();
        securityUser1.setId(1L);
        SecurityUser securityUser2 = new SecurityUser();
        securityUser2.setId(securityUser1.getId());
        assertThat(securityUser1).isEqualTo(securityUser2);
        securityUser2.setId(2L);
        assertThat(securityUser1).isNotEqualTo(securityUser2);
        securityUser1.setId(null);
        assertThat(securityUser1).isNotEqualTo(securityUser2);
    }
}
