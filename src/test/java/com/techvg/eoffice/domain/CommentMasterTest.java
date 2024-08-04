package com.techvg.eoffice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentMaster.class);
        CommentMaster commentMaster1 = new CommentMaster();
        commentMaster1.setId(1L);
        CommentMaster commentMaster2 = new CommentMaster();
        commentMaster2.setId(commentMaster1.getId());
        assertThat(commentMaster1).isEqualTo(commentMaster2);
        commentMaster2.setId(2L);
        assertThat(commentMaster1).isNotEqualTo(commentMaster2);
        commentMaster1.setId(null);
        assertThat(commentMaster1).isNotEqualTo(commentMaster2);
    }
}
