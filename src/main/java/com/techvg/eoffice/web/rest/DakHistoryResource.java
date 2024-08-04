package com.techvg.eoffice.web.rest;

import com.techvg.eoffice.repository.DakHistoryRepository;
import com.techvg.eoffice.service.DakHistoryQueryService;
import com.techvg.eoffice.service.DakHistoryService;
import com.techvg.eoffice.service.criteria.DakHistoryCriteria;
import com.techvg.eoffice.service.dto.DakHistoryDTO;
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
 * REST controller for managing {@link com.techvg.eoffice.domain.DakHistory}.
 */
@RestController
@RequestMapping("/api")
public class DakHistoryResource {

    private final Logger log = LoggerFactory.getLogger(DakHistoryResource.class);

    private static final String ENTITY_NAME = "dakHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DakHistoryService dakHistoryService;

    private final DakHistoryRepository dakHistoryRepository;

    private final DakHistoryQueryService dakHistoryQueryService;

    public DakHistoryResource(
        DakHistoryService dakHistoryService,
        DakHistoryRepository dakHistoryRepository,
        DakHistoryQueryService dakHistoryQueryService
    ) {
        this.dakHistoryService = dakHistoryService;
        this.dakHistoryRepository = dakHistoryRepository;
        this.dakHistoryQueryService = dakHistoryQueryService;
    }

    /**
     * {@code POST  /dak-histories} : Create a new dakHistory.
     *
     * @param dakHistoryDTO the dakHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dakHistoryDTO, or with status {@code 400 (Bad Request)} if the dakHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dak-histories")
    public ResponseEntity<DakHistoryDTO> createDakHistory(@RequestBody DakHistoryDTO dakHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save DakHistory : {}", dakHistoryDTO);
        if (dakHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new dakHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DakHistoryDTO result = dakHistoryService.save(dakHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/dak-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dak-histories/:id} : Updates an existing dakHistory.
     *
     * @param id the id of the dakHistoryDTO to save.
     * @param dakHistoryDTO the dakHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dakHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the dakHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dakHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dak-histories/{id}")
    public ResponseEntity<DakHistoryDTO> updateDakHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DakHistoryDTO dakHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DakHistory : {}, {}", id, dakHistoryDTO);
        if (dakHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dakHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dakHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DakHistoryDTO result = dakHistoryService.update(dakHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dakHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dak-histories/:id} : Partial updates given fields of an existing dakHistory, field will ignore if it is null
     *
     * @param id the id of the dakHistoryDTO to save.
     * @param dakHistoryDTO the dakHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dakHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the dakHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dakHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dakHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dak-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DakHistoryDTO> partialUpdateDakHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DakHistoryDTO dakHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DakHistory partially : {}, {}", id, dakHistoryDTO);
        if (dakHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dakHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dakHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DakHistoryDTO> result = dakHistoryService.partialUpdate(dakHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dakHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dak-histories} : get all the dakHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dakHistories in body.
     */
    @GetMapping("/dak-histories")
    public ResponseEntity<List<DakHistoryDTO>> getAllDakHistories(
        DakHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DakHistories by criteria: {}", criteria);
        Page<DakHistoryDTO> page = dakHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dak-histories/count} : count all the dakHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dak-histories/count")
    public ResponseEntity<Long> countDakHistories(DakHistoryCriteria criteria) {
        log.debug("REST request to count DakHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(dakHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dak-histories/:id} : get the "id" dakHistory.
     *
     * @param id the id of the dakHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dakHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dak-histories/{id}")
    public ResponseEntity<DakHistoryDTO> getDakHistory(@PathVariable Long id) {
        log.debug("REST request to get DakHistory : {}", id);
        Optional<DakHistoryDTO> dakHistoryDTO = dakHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dakHistoryDTO);
    }

    /**
     * {@code DELETE  /dak-histories/:id} : delete the "id" dakHistory.
     *
     * @param id the id of the dakHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dak-histories/{id}")
    public ResponseEntity<Void> deleteDakHistory(@PathVariable Long id) {
        log.debug("REST request to delete DakHistory : {}", id);
        dakHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
