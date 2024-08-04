package com.techvg.eoffice.web.rest;

import com.techvg.eoffice.repository.SecurityPermissionRepository;
import com.techvg.eoffice.service.SecurityPermissionQueryService;
import com.techvg.eoffice.service.SecurityPermissionService;
import com.techvg.eoffice.service.criteria.SecurityPermissionCriteria;
import com.techvg.eoffice.service.dto.SecurityPermissionDTO;
import com.techvg.eoffice.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.techvg.eoffice.domain.SecurityPermission}.
 */
@RestController
@RequestMapping("/api")
public class SecurityPermissionResource {

    private final Logger log = LoggerFactory.getLogger(SecurityPermissionResource.class);

    private static final String ENTITY_NAME = "securityPermission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecurityPermissionService securityPermissionService;

    private final SecurityPermissionRepository securityPermissionRepository;

    private final SecurityPermissionQueryService securityPermissionQueryService;

    public SecurityPermissionResource(
        SecurityPermissionService securityPermissionService,
        SecurityPermissionRepository securityPermissionRepository,
        SecurityPermissionQueryService securityPermissionQueryService
    ) {
        this.securityPermissionService = securityPermissionService;
        this.securityPermissionRepository = securityPermissionRepository;
        this.securityPermissionQueryService = securityPermissionQueryService;
    }

    /**
     * {@code POST  /security-permissions} : Create a new securityPermission.
     *
     * @param securityPermissionDTO the securityPermissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new securityPermissionDTO, or with status {@code 400 (Bad Request)} if the securityPermission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/security-permissions")
    public ResponseEntity<SecurityPermissionDTO> createSecurityPermission(@Valid @RequestBody SecurityPermissionDTO securityPermissionDTO)
        throws URISyntaxException {
        log.debug("REST request to save SecurityPermission : {}", securityPermissionDTO);
        if (securityPermissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new securityPermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecurityPermissionDTO result = securityPermissionService.save(securityPermissionDTO);
        return ResponseEntity
            .created(new URI("/api/security-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /security-permissions/:id} : Updates an existing securityPermission.
     *
     * @param id the id of the securityPermissionDTO to save.
     * @param securityPermissionDTO the securityPermissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityPermissionDTO,
     * or with status {@code 400 (Bad Request)} if the securityPermissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the securityPermissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/security-permissions/{id}")
    public ResponseEntity<SecurityPermissionDTO> updateSecurityPermission(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SecurityPermissionDTO securityPermissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SecurityPermission : {}, {}", id, securityPermissionDTO);
        if (securityPermissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityPermissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SecurityPermissionDTO result = securityPermissionService.update(securityPermissionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityPermissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /security-permissions/:id} : Partial updates given fields of an existing securityPermission, field will ignore if it is null
     *
     * @param id the id of the securityPermissionDTO to save.
     * @param securityPermissionDTO the securityPermissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityPermissionDTO,
     * or with status {@code 400 (Bad Request)} if the securityPermissionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the securityPermissionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the securityPermissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/security-permissions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SecurityPermissionDTO> partialUpdateSecurityPermission(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SecurityPermissionDTO securityPermissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SecurityPermission partially : {}, {}", id, securityPermissionDTO);
        if (securityPermissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityPermissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecurityPermissionDTO> result = securityPermissionService.partialUpdate(securityPermissionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityPermissionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /security-permissions} : get all the securityPermissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of securityPermissions in body.
     */
    @GetMapping("/security-permissions")
    public ResponseEntity<List<SecurityPermissionDTO>> getAllSecurityPermissions(
        SecurityPermissionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SecurityPermissions by criteria: {}", criteria);
        Page<SecurityPermissionDTO> page = securityPermissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /security-permissions/count} : count all the securityPermissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/security-permissions/count")
    public ResponseEntity<Long> countSecurityPermissions(SecurityPermissionCriteria criteria) {
        log.debug("REST request to count SecurityPermissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(securityPermissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /security-permissions/:id} : get the "id" securityPermission.
     *
     * @param id the id of the securityPermissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the securityPermissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/security-permissions/{id}")
    public ResponseEntity<SecurityPermissionDTO> getSecurityPermission(@PathVariable Long id) {
        log.debug("REST request to get SecurityPermission : {}", id);
        Optional<SecurityPermissionDTO> securityPermissionDTO = securityPermissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(securityPermissionDTO);
    }

    /**
     * {@code DELETE  /security-permissions/:id} : delete the "id" securityPermission.
     *
     * @param id the id of the securityPermissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/security-permissions/{id}")
    public ResponseEntity<Void> deleteSecurityPermission(@PathVariable Long id) {
        log.debug("REST request to delete SecurityPermission : {}", id);
        securityPermissionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
