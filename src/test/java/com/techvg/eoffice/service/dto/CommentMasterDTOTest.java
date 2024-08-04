package com.techvg.eoffice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.eoffice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentMasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentMasterDTO.class);
        CommentMasterDTO commentMasterDTO1 = new CommentMasterDTO();
        commentMasterDTO1.setId(1L);
        CommentMasterDTO commentMasterDTO2 = new CommentMasterDTO();
        assertThat(commentMasterDTO1).isNotEqualTo(commentMasterDTO2);
        commentMasterDTO2.setId(commentMasterDTO1.getId());
        assertThat(commentMasterDTO1).isEqualTo(commentMasterDTO2);
        commentMasterDTO2.setId(2L);
        assertThat(commentMasterDTO1).isNotEqualTo(commentMasterDTO2);
        commentMasterDTO1.setId(null);
        assertThat(commentMasterDTO1).isNotEqualTo(commentMasterDTO2);
    }
}
