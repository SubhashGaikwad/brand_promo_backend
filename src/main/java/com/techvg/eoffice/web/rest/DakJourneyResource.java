package com.techvg.eoffice.web.rest;

import com.techvg.eoffice.repository.DakJourneyRepository;
import com.techvg.eoffice.service.DakJourneyQueryService;
import com.techvg.eoffice.service.DakJourneyService;
import com.techvg.eoffice.service.criteria.DakJourneyCriteria;
import com.techvg.eoffice.service.dto.DakJourneyDTO;
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
 * REST controller for managing {@link com.techvg.eoffice.domain.DakJourney}.
 */
@RestController
@RequestMapping("/api")
public class DakJourneyResource {

    private final Logger log = LoggerFactory.getLogger(DakJourneyResource.class);

    private static final String ENTITY_NAME = "dakJourney";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DakJourneyService dakJourneyService;

    private final DakJourneyRepository dakJourneyRepository;

    private final DakJourneyQueryService dakJourneyQueryService;

    public DakJourneyResource(
        DakJourneyService dakJourneyService,
        DakJourneyRepository dakJourneyRepository,
        DakJourneyQueryService dakJourneyQueryService
    ) {
        this.dakJourneyService = dakJourneyService;
        this.dakJourneyRepository = dakJourneyRepository;
        this.dakJourneyQueryService = dakJourneyQueryService;
    }

    /**
     * {@code POST  /dak-journeys} : Create a new dakJourney.
     *
     * @param dakJourneyDTO the dakJourneyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dakJourneyDTO, or with status {@code 400 (Bad Request)} if the dakJourney has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dak-journeys")
    public ResponseEntity<DakJourneyDTO> createDakJourney(@RequestBody DakJourneyDTO dakJourneyDTO) throws URISyntaxException {
        log.debug("REST request to save DakJourney : {}", dakJourneyDTO);
        if (dakJourneyDTO.getId() != null) {
            throw new BadRequestAlertException("A new dakJourney cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DakJourneyDTO result = dakJourneyService.save(dakJourneyDTO);
        return ResponseEntity
            .created(new URI("/api/dak-journeys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dak-journeys/:id} : Updates an existing dakJourney.
     *
     * @param id the id of the dakJourneyDTO to save.
     * @param dakJourneyDTO the dakJourneyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dakJourneyDTO,
     * or with status {@code 400 (Bad Request)} if the dakJourneyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dakJourneyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dak-journeys/{id}")
    public ResponseEntity<DakJourneyDTO> updateDakJourney(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DakJourneyDTO dakJourneyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DakJourney : {}, {}", id, dakJourneyDTO);
        if (dakJourneyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dakJourneyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dakJourneyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DakJourneyDTO result = dakJourneyService.update(dakJourneyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dakJourneyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dak-journeys/:id} : Partial updates given fields of an existing dakJourney, field will ignore if it is null
     *
     * @param id the id of the dakJourneyDTO to save.
     * @param dakJourneyDTO the dakJourneyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dakJourneyDTO,
     * or with status {@code 400 (Bad Request)} if the dakJourneyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dakJourneyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dakJourneyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dak-journeys/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DakJourneyDTO> partialUpdateDakJourney(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DakJourneyDTO dakJourneyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DakJourney partially : {}, {}", id, dakJourneyDTO);
        if (dakJourneyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dakJourneyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dakJourneyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DakJourneyDTO> result = dakJourneyService.partialUpdate(dakJourneyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dakJourneyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dak-journeys} : get all the dakJourneys.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dakJourneys in body.
     */
    @GetMapping("/dak-journeys")
    public ResponseEntity<List<DakJourneyDTO>> getAllDakJourneys(
        DakJourneyCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DakJourneys by criteria: {}", criteria);
        Page<DakJourneyDTO> page = dakJourneyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dak-journeys/count} : count all the dakJourneys.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dak-journeys/count")
    public ResponseEntity<Long> countDakJourneys(DakJourneyCriteria criteria) {
        log.debug("REST request to count DakJourneys by criteria: {}", criteria);
        return ResponseEntity.ok().body(dakJourneyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dak-journeys/:id} : get the "id" dakJourney.
     *
     * @param id the id of the dakJourneyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dakJourneyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dak-journeys/{id}")
    public ResponseEntity<DakJourneyDTO> getDakJourney(@PathVariable Long id) {
        log.debug("REST request to get DakJourney : {}", id);
        Optional<DakJourneyDTO> dakJourneyDTO = dakJourneyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dakJourneyDTO);
    }

    /**
     * {@code DELETE  /dak-journeys/:id} : delete the "id" dakJourney.
     *
     * @param id the id of the dakJourneyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dak-journeys/{id}")
    public ResponseEntity<Void> deleteDakJourney(@PathVariable Long id) {
        log.debug("REST request to delete DakJourney : {}", id);
        dakJourneyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
