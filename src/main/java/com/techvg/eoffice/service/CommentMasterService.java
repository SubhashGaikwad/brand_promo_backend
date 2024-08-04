package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.CommentMaster;
import com.techvg.eoffice.repository.CommentMasterRepository;
import com.techvg.eoffice.service.dto.CommentMasterDTO;
import com.techvg.eoffice.service.mapper.CommentMasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommentMaster}.
 */
@Service
@Transactional
public class CommentMasterService {

    private final Logger log = LoggerFactory.getLogger(CommentMasterService.class);

    private final CommentMasterRepository commentMasterRepository;

    private final CommentMasterMapper commentMasterMapper;

    public CommentMasterService(CommentMasterRepository commentMasterRepository, CommentMasterMapper commentMasterMapper) {
        this.commentMasterRepository = commentMasterRepository;
        this.commentMasterMapper = commentMasterMapper;
    }

    /**
     * Save a commentMaster.
     *
     * @param commentMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentMasterDTO save(CommentMasterDTO commentMasterDTO) {
        log.debug("Request to save CommentMaster : {}", commentMasterDTO);
        CommentMaster commentMaster = commentMasterMapper.toEntity(commentMasterDTO);
        commentMaster = commentMasterRepository.save(commentMaster);
        return commentMasterMapper.toDto(commentMaster);
    }

    /**
     * Update a commentMaster.
     *
     * @param commentMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentMasterDTO update(CommentMasterDTO commentMasterDTO) {
        log.debug("Request to save CommentMaster : {}", commentMasterDTO);
        CommentMaster commentMaster = commentMasterMapper.toEntity(commentMasterDTO);
        commentMaster = commentMasterRepository.save(commentMaster);
        return commentMasterMapper.toDto(commentMaster);
    }

    /**
     * Partially update a commentMaster.
     *
     * @param commentMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommentMasterDTO> partialUpdate(CommentMasterDTO commentMasterDTO) {
        log.debug("Request to partially update CommentMaster : {}", commentMasterDTO);

        return commentMasterRepository
            .findById(commentMasterDTO.getId())
            .map(existingCommentMaster -> {
                commentMasterMapper.partialUpdate(existingCommentMaster, commentMasterDTO);

                return existingCommentMaster;
            })
            .map(commentMasterRepository::save)
            .map(commentMasterMapper::toDto);
    }

    /**
     * Get all the commentMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommentMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommentMasters");
        return commentMasterRepository.findAll(pageable).map(commentMasterMapper::toDto);
    }

    /**
     * Get all the commentMasters with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CommentMasterDTO> findAllWithEagerRelationships(Pageable pageable) {
        return commentMasterRepository.findAllWithEagerRelationships(pageable).map(commentMasterMapper::toDto);
    }

    /**
     * Get one commentMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommentMasterDTO> findOne(Long id) {
        log.debug("Request to get CommentMaster : {}", id);
        return commentMasterRepository.findOneWithEagerRelationships(id).map(commentMasterMapper::toDto);
    }

    /**
     * Delete the commentMaster by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommentMaster : {}", id);
        commentMasterRepository.deleteById(id);
    }
}
