package com.techvg.eoffice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommentMasterMapperTest {

    private CommentMasterMapper commentMasterMapper;

    @BeforeEach
    public void setUp() {
        commentMasterMapper = new CommentMasterMapperImpl();
    }
}
