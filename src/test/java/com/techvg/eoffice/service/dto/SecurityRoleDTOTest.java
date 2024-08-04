package com.techvg.eoffice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SecurityRoleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityRoleDTO.class);
        SecurityRoleDTO securityRoleDTO1 = new SecurityRoleDTO();
        securityRoleDTO1.setId(1L);
        SecurityRoleDTO securityRoleDTO2 = new SecurityRoleDTO();
        assertThat(securityRoleDTO1).isNotEqualTo(securityRoleDTO2);
        securityRoleDTO2.setId(securityRoleDTO1.getId());
        assertThat(securityRoleDTO1).isEqualTo(securityRoleDTO2);
        securityRoleDTO2.setId(2L);
        assertThat(securityRoleDTO1).isNotEqualTo(securityRoleDTO2);
        securityRoleDTO1.setId(null);
        assertThat(securityRoleDTO1).isNotEqualTo(securityRoleDTO2);
    }
}
