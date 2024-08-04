package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SecurityRoleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityRole.class);
        SecurityRole securityRole1 = new SecurityRole();
        securityRole1.setId(1L);
        SecurityRole securityRole2 = new SecurityRole();
        securityRole2.setId(securityRole1.getId());
        assertThat(securityRole1).isEqualTo(securityRole2);
        securityRole2.setId(2L);
        assertThat(securityRole1).isNotEqualTo(securityRole2);
        securityRole1.setId(null);
        assertThat(securityRole1).isNotEqualTo(securityRole2);
    }
}
