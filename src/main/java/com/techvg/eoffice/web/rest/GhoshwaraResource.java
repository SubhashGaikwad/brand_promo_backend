package com.techvg.eoffice.web.rest;

import com.techvg.eoffice.repository.GhoshwaraRepository;
import com.techvg.eoffice.service.DakMasterService;
import com.techvg.eoffice.service.GhoshwaraQueryService;
import com.techvg.eoffice.service.GhoshwaraService;
import com.techvg.eoffice.service.SecurityUserQueryService;
import com.techvg.eoffice.service.criteria.GhoshwaraCriteria;
import com.techvg.eoffice.service.criteria.SecurityUserCriteria;
import com.techvg.eoffice.service.dto.GhoshwaraDTO;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import com.techvg.eoffice.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.techvg.eoffice.domain.Ghoshwara}.
 */
@RestController
@RequestMapping("/api")
public class GhoshwaraResource {

    private final Logger log = LoggerFactory.getLogger(GhoshwaraResource.class);

    private static final String ENTITY_NAME = "ghoshwara";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GhoshwaraService ghoshwaraService;

    private final GhoshwaraRepository ghoshwaraRepository;

    private final GhoshwaraQueryService ghoshwaraQueryService;

    @Autowired
    private SecurityUserQueryService securityUserQueryService;

    @Autowired
    private DakMasterService dakMasterService;

    public GhoshwaraResource(
        GhoshwaraService ghoshwaraService,
        GhoshwaraRepository ghoshwaraRepository,
        GhoshwaraQueryService ghoshwaraQueryService
    ) {
        this.ghoshwaraService = ghoshwaraService;
        this.ghoshwaraRepository = ghoshwaraRepository;
        this.ghoshwaraQueryService = ghoshwaraQueryService;
    }

    /**
     * {@code POST  /ghoshwaras} : Create a new ghoshwara.
     *
     * @param ghoshwaraDTO the ghoshwaraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ghoshwaraDTO, or with status {@code 400 (Bad Request)} if the ghoshwara has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ghoshwaras")
    public ResponseEntity<GhoshwaraDTO> createGhoshwara(@RequestBody GhoshwaraDTO ghoshwaraDTO) throws URISyntaxException {
        log.debug("REST request to save Ghoshwara : {}", ghoshwaraDTO);
        if (ghoshwaraDTO.getId() != null) {
            throw new BadRequestAlertException("A new ghoshwara cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GhoshwaraDTO result = ghoshwaraService.save(ghoshwaraDTO);
        return ResponseEntity
            .created(new URI("/api/ghoshwaras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ghoshwaras/:id} : Updates an existing ghoshwara.
     *
     * @param id the id of the ghoshwaraDTO to save.
     * @param ghoshwaraDTO the ghoshwaraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ghoshwaraDTO,
     * or with status {@code 400 (Bad Request)} if the ghoshwaraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ghoshwaraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ghoshwaras/{id}")
    public ResponseEntity<GhoshwaraDTO> updateGhoshwara(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GhoshwaraDTO ghoshwaraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Ghoshwara : {}, {}", id, ghoshwaraDTO);
        if (ghoshwaraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ghoshwaraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ghoshwaraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GhoshwaraDTO result = ghoshwaraService.update(ghoshwaraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ghoshwaraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ghoshwaras/:id} : Partial updates given fields of an existing ghoshwara, field will ignore if it is null
     *
     * @param id the id of the ghoshwaraDTO to save.
     * @param ghoshwaraDTO the ghoshwaraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ghoshwaraDTO,
     * or with status {@code 400 (Bad Request)} if the ghoshwaraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ghoshwaraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ghoshwaraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ghoshwaras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GhoshwaraDTO> partialUpdateGhoshwara(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GhoshwaraDTO ghoshwaraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ghoshwara partially : {}, {}", id, ghoshwaraDTO);
        if (ghoshwaraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ghoshwaraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ghoshwaraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GhoshwaraDTO> result = ghoshwaraService.partialUpdate(ghoshwaraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ghoshwaraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ghoshwaras} : get all the ghoshwaras.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ghoshwaras in body.
     */
    @GetMapping("/ghoshwaras")
    public ResponseEntity<List<GhoshwaraDTO>> getAllGhoshwaras(
        GhoshwaraCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Ghoshwaras by criteria: {}", criteria);
        Page<GhoshwaraDTO> page = ghoshwaraQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ghoshwaras/count} : count all the ghoshwaras.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ghoshwaras/count")
    public ResponseEntity<Long> countGhoshwaras(GhoshwaraCriteria criteria) {
        log.debug("REST request to count Ghoshwaras by criteria: {}", criteria);
        return ResponseEntity.ok().body(ghoshwaraQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ghoshwaras/:id} : get the "id" ghoshwara.
     *
     * @param id the id of the ghoshwaraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ghoshwaraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ghoshwaras/{id}")
    public ResponseEntity<GhoshwaraDTO> getGhoshwara(@PathVariable Long id) {
        log.debug("REST request to get Ghoshwara : {}", id);
        Optional<GhoshwaraDTO> ghoshwaraDTO = ghoshwaraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ghoshwaraDTO);
    }

    /**
     * {@code DELETE  /ghoshwaras/:id} : delete the "id" ghoshwara.
     *
     * @param id the id of the ghoshwaraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ghoshwaras/{id}")
    public ResponseEntity<Void> deleteGhoshwara(@PathVariable Long id) {
        log.debug("REST request to delete Ghoshwara : {}", id);
        ghoshwaraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /ghoshwaras} : get all the ghoshwaras.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ghoshwaras in body.
     */
    @GetMapping("/ghoshwara/report/pdf")
    public ResponseEntity<?> getGhoshwaraPdfReport(GhoshwaraCriteria criteria) {
        log.debug("REST request to get Ghoshwaras by criteria: {}", criteria);
        List<GhoshwaraDTO> ghoshwaraList = null;
        SecurityUserDTO user;
        if (criteria.getSecurityUserId() != null) {
            ghoshwaraList = ghoshwaraQueryService.findByCriteria(criteria);
        }

        SecurityUserCriteria userCriteria = new SecurityUserCriteria();
        userCriteria.setId(criteria.getSecurityUserId());
        List<SecurityUserDTO> usersList = securityUserQueryService.findByCriteria(userCriteria);
        user = usersList.get(0);
        String orgName = user.getOrganization().getOrganizationName();

        // -----------------create source file in html-------
        String htmlReport = ghoshwaraService.getGoshwaraHtmlReport(criteria, ghoshwaraList, orgName, user);

        // ---------------Create Pdf using Chrome ---------
        ResponseEntity<byte[]> response = dakMasterService.createPdfFromHtml(htmlReport);

        return response;
    }
}
