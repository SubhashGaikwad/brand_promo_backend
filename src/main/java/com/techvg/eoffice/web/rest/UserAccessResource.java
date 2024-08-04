package com.techvg.eoffice.web.rest;

import com.techvg.eoffice.repository.UserAccessRepository;
import com.techvg.eoffice.service.UserAccessQueryService;
import com.techvg.eoffice.service.UserAccessService;
import com.techvg.eoffice.service.criteria.UserAccessCriteria;
import com.techvg.eoffice.service.dto.UserAccessDTO;
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
 * REST controller for managing {@link com.techvg.eoffice.domain.UserAccess}.
 */
@RestController
@RequestMapping("/api")
public class UserAccessResource {

    private final Logger log = LoggerFactory.getLogger(UserAccessResource.class);

    private static final String ENTITY_NAME = "userAccess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAccessService userAccessService;

    private final UserAccessRepository userAccessRepository;

    private final UserAccessQueryService userAccessQueryService;

    public UserAccessResource(
        UserAccessService userAccessService,
        UserAccessRepository userAccessRepository,
        UserAccessQueryService userAccessQueryService
    ) {
        this.userAccessService = userAccessService;
        this.userAccessRepository = userAccessRepository;
        this.userAccessQueryService = userAccessQueryService;
    }

    /**
     * {@code POST  /user-accesses} : Create a new userAccess.
     *
     * @param userAccessDTO the userAccessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAccessDTO, or with status {@code 400 (Bad Request)} if the userAccess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-accesses")
    public ResponseEntity<UserAccessDTO> createUserAccess(@RequestBody UserAccessDTO userAccessDTO) throws URISyntaxException {
        log.debug("REST request to save UserAccess : {}", userAccessDTO);
        if (userAccessDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAccess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAccessDTO result = userAccessService.save(userAccessDTO);
        return ResponseEntity
            .created(new URI("/api/user-accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-accesses/:id} : Updates an existing userAccess.
     *
     * @param id the id of the userAccessDTO to save.
     * @param userAccessDTO the userAccessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAccessDTO,
     * or with status {@code 400 (Bad Request)} if the userAccessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAccessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-accesses/{id}")
    public ResponseEntity<UserAccessDTO> updateUserAccess(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserAccessDTO userAccessDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserAccess : {}, {}", id, userAccessDTO);
        if (userAccessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAccessDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAccessRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserAccessDTO result = userAccessService.update(userAccessDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAccessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-accesses/:id} : Partial updates given fields of an existing userAccess, field will ignore if it is null
     *
     * @param id the id of the userAccessDTO to save.
     * @param userAccessDTO the userAccessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAccessDTO,
     * or with status {@code 400 (Bad Request)} if the userAccessDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userAccessDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userAccessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-accesses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserAccessDTO> partialUpdateUserAccess(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserAccessDTO userAccessDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserAccess partially : {}, {}", id, userAccessDTO);
        if (userAccessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userAccessDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userAccessRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserAccessDTO> result = userAccessService.partialUpdate(userAccessDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAccessDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-accesses} : get all the userAccesses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAccesses in body.
     */
    @GetMapping("/user-accesses")
    public ResponseEntity<List<UserAccessDTO>> getAllUserAccesses(
        UserAccessCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserAccesses by criteria: {}", criteria);
        Page<UserAccessDTO> page = userAccessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-accesses/count} : count all the userAccesses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-accesses/count")
    public ResponseEntity<Long> countUserAccesses(UserAccessCriteria criteria) {
        log.debug("REST request to count UserAccesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(userAccessQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-accesses/:id} : get the "id" userAccess.
     *
     * @param id the id of the userAccessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAccessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-accesses/{id}")
    public ResponseEntity<UserAccessDTO> getUserAccess(@PathVariable Long id) {
        log.debug("REST request to get UserAccess : {}", id);
        Optional<UserAccessDTO> userAccessDTO = userAccessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAccessDTO);
    }

    /**
     * {@code DELETE  /user-accesses/:id} : delete the "id" userAccess.
     *
     * @param id the id of the userAccessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-accesses/{id}")
    public ResponseEntity<Void> deleteUserAccess(@PathVariable Long id) {
        log.debug("REST request to delete UserAccess : {}", id);
        userAccessService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
