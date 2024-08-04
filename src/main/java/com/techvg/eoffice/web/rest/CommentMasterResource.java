package com.techvg.eoffice.web.rest;

import com.techvg.eoffice.repository.CommentMasterRepository;
import com.techvg.eoffice.service.CommentMasterQueryService;
import com.techvg.eoffice.service.CommentMasterService;
import com.techvg.eoffice.service.criteria.CommentMasterCriteria;
import com.techvg.eoffice.service.dto.CommentMasterDTO;
import com.techvg.eoffice.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.eoffice.domain.CommentMaster}.
 */
@RestController
@RequestMapping("/api")
public class CommentMasterResource {

    private final Logger log = LoggerFactory.getLogger(CommentMasterResource.class);

    private static final String ENTITY_NAME = "commentMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentMasterService commentMasterService;

    private final CommentMasterRepository commentMasterRepository;

    private final CommentMasterQueryService commentMasterQueryService;

    public CommentMasterResource(
        CommentMasterService commentMasterService,
        CommentMasterRepository commentMasterRepository,
        CommentMasterQueryService commentMasterQueryService
    ) {
        this.commentMasterService = commentMasterService;
        this.commentMasterRepository = commentMasterRepository;
        this.commentMasterQueryService = commentMasterQueryService;
    }

    /**
     * {@code POST  /comment-masters} : Create a new commentMaster.
     *
     * @param commentMasterDTO the commentMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentMasterDTO, or with status {@code 400 (Bad Request)} if the commentMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comment-masters")
    public ResponseEntity<CommentMasterDTO> createCommentMaster(@RequestBody CommentMasterDTO commentMasterDTO) throws URISyntaxException {
        log.debug("REST request to save CommentMaster : {}", commentMasterDTO);
        if (commentMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new commentMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentMasterDTO result = commentMasterService.save(commentMasterDTO);
        return ResponseEntity
            .created(new URI("/api/comment-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comment-masters/:id} : Updates an existing commentMaster.
     *
     * @param id the id of the commentMasterDTO to save.
     * @param commentMasterDTO the commentMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentMasterDTO,
     * or with status {@code 400 (Bad Request)} if the commentMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comment-masters/{id}")
    public ResponseEntity<CommentMasterDTO> updateCommentMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommentMasterDTO commentMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CommentMaster : {}, {}", id, commentMasterDTO);
        if (commentMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommentMasterDTO result = commentMasterService.update(commentMasterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comment-masters/:id} : Partial updates given fields of an existing commentMaster, field will ignore if it is null
     *
     * @param id the id of the commentMasterDTO to save.
     * @param commentMasterDTO the commentMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentMasterDTO,
     * or with status {@code 400 (Bad Request)} if the commentMasterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commentMasterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commentMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comment-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommentMasterDTO> partialUpdateCommentMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommentMasterDTO commentMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommentMaster partially : {}, {}", id, commentMasterDTO);
        if (commentMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommentMasterDTO> result = commentMasterService.partialUpdate(commentMasterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /comment-masters} : get all the commentMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commentMasters in body.
     */
    @GetMapping("/comment-masters")
    public ResponseEntity<List<CommentMasterDTO>> getAllCommentMasters(
        CommentMasterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CommentMasters by criteria: {}", criteria);
        Page<CommentMasterDTO> page = commentMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comment-masters/count} : count all the commentMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comment-masters/count")
    public ResponseEntity<Long> countCommentMasters(CommentMasterCriteria criteria) {
        log.debug("REST request to count CommentMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(commentMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comment-masters/:id} : get the "id" commentMaster.
     *
     * @param id the id of the commentMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comment-masters/{id}")
    public ResponseEntity<CommentMasterDTO> getCommentMaster(@PathVariable Long id) {
        log.debug("REST request to get CommentMaster : {}", id);
        Optional<CommentMasterDTO> commentMasterDTO = commentMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentMasterDTO);
    }

    /**
     * {@code DELETE  /comment-masters/:id} : delete the "id" commentMaster.
     *
     * @param id the id of the commentMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comment-masters/{id}")
    public ResponseEntity<Void> deleteCommentMaster(@PathVariable Long id) {
        log.debug("REST request to delete CommentMaster : {}", id);
        commentMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
