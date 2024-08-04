package com.techvg.eoffice.web.rest;

import com.techvg.eoffice.repository.DakIdGeneratorRepository;
import com.techvg.eoffice.service.DakIdGeneratorQueryService;
import com.techvg.eoffice.service.DakIdGeneratorService;
import com.techvg.eoffice.service.criteria.DakIdGeneratorCriteria;
import com.techvg.eoffice.service.dto.DakIdGeneratorDTO;
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
 * REST controller for managing {@link com.techvg.eoffice.domain.DakIdGenerator}.
 */
@RestController
@RequestMapping("/api")
public class DakIdGeneratorResource {

    private final Logger log = LoggerFactory.getLogger(DakIdGeneratorResource.class);

    private static final String ENTITY_NAME = "dakIdGenerator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DakIdGeneratorService dakIdGeneratorService;

    private final DakIdGeneratorRepository dakIdGeneratorRepository;

    private final DakIdGeneratorQueryService dakIdGeneratorQueryService;

    public DakIdGeneratorResource(
        DakIdGeneratorService dakIdGeneratorService,
        DakIdGeneratorRepository dakIdGeneratorRepository,
        DakIdGeneratorQueryService dakIdGeneratorQueryService
    ) {
        this.dakIdGeneratorService = dakIdGeneratorService;
        this.dakIdGeneratorRepository = dakIdGeneratorRepository;
        this.dakIdGeneratorQueryService = dakIdGeneratorQueryService;
    }

    /**
     * {@code POST  /dak-id-generators} : Create a new dakIdGenerator.
     *
     * @param dakIdGeneratorDTO the dakIdGeneratorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dakIdGeneratorDTO, or with status {@code 400 (Bad Request)} if the dakIdGenerator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dak-id-generators")
    public ResponseEntity<DakIdGeneratorDTO> createDakIdGenerator(@RequestBody DakIdGeneratorDTO dakIdGeneratorDTO)
        throws URISyntaxException {
        log.debug("REST request to save DakIdGenerator : {}", dakIdGeneratorDTO);
        if (dakIdGeneratorDTO.getId() != null) {
            throw new BadRequestAlertException("A new dakIdGenerator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DakIdGeneratorDTO result = dakIdGeneratorService.save(dakIdGeneratorDTO);
        return ResponseEntity
            .created(new URI("/api/dak-id-generators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dak-id-generators/:id} : Updates an existing dakIdGenerator.
     *
     * @param id the id of the dakIdGeneratorDTO to save.
     * @param dakIdGeneratorDTO the dakIdGeneratorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dakIdGeneratorDTO,
     * or with status {@code 400 (Bad Request)} if the dakIdGeneratorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dakIdGeneratorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dak-id-generators/{id}")
    public ResponseEntity<DakIdGeneratorDTO> updateDakIdGenerator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DakIdGeneratorDTO dakIdGeneratorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DakIdGenerator : {}, {}", id, dakIdGeneratorDTO);
        if (dakIdGeneratorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dakIdGeneratorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dakIdGeneratorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DakIdGeneratorDTO result = dakIdGeneratorService.update(dakIdGeneratorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dakIdGeneratorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dak-id-generators/:id} : Partial updates given fields of an existing dakIdGenerator, field will ignore if it is null
     *
     * @param id the id of the dakIdGeneratorDTO to save.
     * @param dakIdGeneratorDTO the dakIdGeneratorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dakIdGeneratorDTO,
     * or with status {@code 400 (Bad Request)} if the dakIdGeneratorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dakIdGeneratorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dakIdGeneratorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dak-id-generators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DakIdGeneratorDTO> partialUpdateDakIdGenerator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DakIdGeneratorDTO dakIdGeneratorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DakIdGenerator partially : {}, {}", id, dakIdGeneratorDTO);
        if (dakIdGeneratorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dakIdGeneratorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dakIdGeneratorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DakIdGeneratorDTO> result = dakIdGeneratorService.partialUpdate(dakIdGeneratorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dakIdGeneratorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dak-id-generators} : get all the dakIdGenerators.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dakIdGenerators in body.
     */
    @GetMapping("/dak-id-generators")
    public ResponseEntity<List<DakIdGeneratorDTO>> getAllDakIdGenerators(
        DakIdGeneratorCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DakIdGenerators by criteria: {}", criteria);
        Page<DakIdGeneratorDTO> page = dakIdGeneratorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dak-id-generators/count} : count all the dakIdGenerators.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dak-id-generators/count")
    public ResponseEntity<Long> countDakIdGenerators(DakIdGeneratorCriteria criteria) {
        log.debug("REST request to count DakIdGenerators by criteria: {}", criteria);
        return ResponseEntity.ok().body(dakIdGeneratorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dak-id-generators/:id} : get the "id" dakIdGenerator.
     *
     * @param id the id of the dakIdGeneratorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dakIdGeneratorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dak-id-generators/{id}")
    public ResponseEntity<DakIdGeneratorDTO> getDakIdGenerator(@PathVariable Long id) {
        log.debug("REST request to get DakIdGenerator : {}", id);
        Optional<DakIdGeneratorDTO> dakIdGeneratorDTO = dakIdGeneratorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dakIdGeneratorDTO);
    }

    /**
     * {@code DELETE  /dak-id-generators/:id} : delete the "id" dakIdGenerator.
     *
     * @param id the id of the dakIdGeneratorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dak-id-generators/{id}")
    public ResponseEntity<Void> deleteDakIdGenerator(@PathVariable Long id) {
        log.debug("REST request to delete DakIdGenerator : {}", id);
        dakIdGeneratorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
