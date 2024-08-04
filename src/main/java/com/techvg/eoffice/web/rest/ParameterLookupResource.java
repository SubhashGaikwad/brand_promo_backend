package com.techvg.eoffice.web.rest;

import com.techvg.eoffice.repository.ParameterLookupRepository;
import com.techvg.eoffice.service.ParameterLookupQueryService;
import com.techvg.eoffice.service.ParameterLookupService;
import com.techvg.eoffice.service.criteria.ParameterLookupCriteria;
import com.techvg.eoffice.service.dto.ParameterLookupDTO;
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
 * REST controller for managing {@link com.techvg.eoffice.domain.ParameterLookup}.
 */
@RestController
@RequestMapping("/api")
public class ParameterLookupResource {

    private final Logger log = LoggerFactory.getLogger(ParameterLookupResource.class);

    private static final String ENTITY_NAME = "parameterLookup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParameterLookupService parameterLookupService;

    private final ParameterLookupRepository parameterLookupRepository;

    private final ParameterLookupQueryService parameterLookupQueryService;

    public ParameterLookupResource(
        ParameterLookupService parameterLookupService,
        ParameterLookupRepository parameterLookupRepository,
        ParameterLookupQueryService parameterLookupQueryService
    ) {
        this.parameterLookupService = parameterLookupService;
        this.parameterLookupRepository = parameterLookupRepository;
        this.parameterLookupQueryService = parameterLookupQueryService;
    }

    /**
     * {@code POST  /parameter-lookups} : Create a new parameterLookup.
     *
     * @param parameterLookupDTO the parameterLookupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parameterLookupDTO, or with status {@code 400 (Bad Request)} if the parameterLookup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parameter-lookups")
    public ResponseEntity<ParameterLookupDTO> createParameterLookup(@Valid @RequestBody ParameterLookupDTO parameterLookupDTO)
        throws URISyntaxException {
        log.debug("REST request to save ParameterLookup : {}", parameterLookupDTO);
        if (parameterLookupDTO.getId() != null) {
            throw new BadRequestAlertException("A new parameterLookup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParameterLookupDTO result = parameterLookupService.save(parameterLookupDTO);
        return ResponseEntity
            .created(new URI("/api/parameter-lookups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parameter-lookups/:id} : Updates an existing parameterLookup.
     *
     * @param id the id of the parameterLookupDTO to save.
     * @param parameterLookupDTO the parameterLookupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parameterLookupDTO,
     * or with status {@code 400 (Bad Request)} if the parameterLookupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parameterLookupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parameter-lookups/{id}")
    public ResponseEntity<ParameterLookupDTO> updateParameterLookup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ParameterLookupDTO parameterLookupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ParameterLookup : {}, {}", id, parameterLookupDTO);
        if (parameterLookupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parameterLookupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parameterLookupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParameterLookupDTO result = parameterLookupService.update(parameterLookupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parameterLookupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parameter-lookups/:id} : Partial updates given fields of an existing parameterLookup, field will ignore if it is null
     *
     * @param id the id of the parameterLookupDTO to save.
     * @param parameterLookupDTO the parameterLookupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parameterLookupDTO,
     * or with status {@code 400 (Bad Request)} if the parameterLookupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parameterLookupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parameterLookupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parameter-lookups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParameterLookupDTO> partialUpdateParameterLookup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParameterLookupDTO parameterLookupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParameterLookup partially : {}, {}", id, parameterLookupDTO);
        if (parameterLookupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parameterLookupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parameterLookupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParameterLookupDTO> result = parameterLookupService.partialUpdate(parameterLookupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parameterLookupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /parameter-lookups} : get all the parameterLookups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parameterLookups in body.
     */
    @GetMapping("/parameter-lookups")
    public ResponseEntity<List<ParameterLookupDTO>> getAllParameterLookups(
        ParameterLookupCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ParameterLookups by criteria: {}", criteria);
        Page<ParameterLookupDTO> page = parameterLookupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parameter-lookups/count} : count all the parameterLookups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/parameter-lookups/count")
    public ResponseEntity<Long> countParameterLookups(ParameterLookupCriteria criteria) {
        log.debug("REST request to count ParameterLookups by criteria: {}", criteria);
        return ResponseEntity.ok().body(parameterLookupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /parameter-lookups/:id} : get the "id" parameterLookup.
     *
     * @param id the id of the parameterLookupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parameterLookupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parameter-lookups/{id}")
    public ResponseEntity<ParameterLookupDTO> getParameterLookup(@PathVariable Long id) {
        log.debug("REST request to get ParameterLookup : {}", id);
        Optional<ParameterLookupDTO> parameterLookupDTO = parameterLookupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parameterLookupDTO);
    }

    /**
     * {@code DELETE  /parameter-lookups/:id} : delete the "id" parameterLookup.
     *
     * @param id the id of the parameterLookupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parameter-lookups/{id}")
    public ResponseEntity<Void> deleteParameterLookup(@PathVariable Long id) {
        log.debug("REST request to delete ParameterLookup : {}", id);
        parameterLookupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
