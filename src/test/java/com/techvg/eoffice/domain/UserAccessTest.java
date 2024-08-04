package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAccessTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAccess.class);
        UserAccess userAccess1 = new UserAccess();
        userAccess1.setId(1L);
        UserAccess userAccess2 = new UserAccess();
        userAccess2.setId(userAccess1.getId());
        assertThat(userAccess1).isEqualTo(userAccess2);
        userAccess2.setId(2L);
        assertThat(userAccess1).isNotEqualTo(userAccess2);
        userAccess1.setId(null);
        assertThat(userAccess1).isNotEqualTo(userAccess2);
    }
}
