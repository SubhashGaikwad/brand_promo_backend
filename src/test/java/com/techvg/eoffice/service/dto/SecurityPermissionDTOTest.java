package com.techvg.eoffice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SecurityPermissionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityPermissionDTO.class);
        SecurityPermissionDTO securityPermissionDTO1 = new SecurityPermissionDTO();
        securityPermissionDTO1.setId(1L);
        SecurityPermissionDTO securityPermissionDTO2 = new SecurityPermissionDTO();
        assertThat(securityPermissionDTO1).isNotEqualTo(securityPermissionDTO2);
        securityPermissionDTO2.setId(securityPermissionDTO1.getId());
        assertThat(securityPermissionDTO1).isEqualTo(securityPermissionDTO2);
        securityPermissionDTO2.setId(2L);
        assertThat(securityPermissionDTO1).isNotEqualTo(securityPermissionDTO2);
        securityPermissionDTO1.setId(null);
        assertThat(securityPermissionDTO1).isNotEqualTo(securityPermissionDTO2);
    }
}
