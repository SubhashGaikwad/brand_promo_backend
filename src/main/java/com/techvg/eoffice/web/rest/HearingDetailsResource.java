package com.techvg.eoffice.web.rest;

import com.techvg.eoffice.domain.enumeration.DakStatus;
import com.techvg.eoffice.repository.HearingDetailsRepository;
import com.techvg.eoffice.service.DakMasterService;
import com.techvg.eoffice.service.HearingDetailsQueryService;
import com.techvg.eoffice.service.HearingDetailsService;
import com.techvg.eoffice.service.OrganizationService;
import com.techvg.eoffice.service.criteria.DakMasterCriteria;
import com.techvg.eoffice.service.criteria.HearingDetailsCriteria;
import com.techvg.eoffice.service.criteria.HearingDetailsCriteria.DakStatusFilter;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import com.techvg.eoffice.service.dto.HearingDetailsDTO;
import com.techvg.eoffice.service.dto.OrganizationDTO;
import com.techvg.eoffice.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.techvg.eoffice.domain.HearingDetails}.
 */
@RestController
@RequestMapping("/api")
public class HearingDetailsResource {

    private final Logger log = LoggerFactory.getLogger(HearingDetailsResource.class);

    private static final String ENTITY_NAME = "hearingDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HearingDetailsService hearingDetailsService;

    private final HearingDetailsRepository hearingDetailsRepository;

    private final HearingDetailsQueryService hearingDetailsQueryService;

    @Autowired
    private DakMasterService dakMasterService;

    @Autowired
    private OrganizationService organizationService;

    public HearingDetailsResource(
        HearingDetailsService hearingDetailsService,
        HearingDetailsRepository hearingDetailsRepository,
        HearingDetailsQueryService hearingDetailsQueryService
    ) {
        this.hearingDetailsService = hearingDetailsService;
        this.hearingDetailsRepository = hearingDetailsRepository;
        this.hearingDetailsQueryService = hearingDetailsQueryService;
    }

    /**
     * {@code POST  /hearing-details} : Create a new hearingDetails.
     *
     * @param hearingDetailsDTO the hearingDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new hearingDetailsDTO, or with status
     *         {@code 400 (Bad Request)} if the hearingDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hearing-details")
    public ResponseEntity<HearingDetailsDTO> createHearingDetails(@RequestBody HearingDetailsDTO hearingDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save HearingDetails : {}", hearingDetailsDTO);
        if (hearingDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new hearingDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HearingDetailsDTO result = hearingDetailsService.save(hearingDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/hearing-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hearing-details/:id} : Updates an existing hearingDetails.
     *
     * @param id                the id of the hearingDetailsDTO to save.
     * @param hearingDetailsDTO the hearingDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated hearingDetailsDTO, or with status
     *         {@code 400 (Bad Request)} if the hearingDetailsDTO is not valid, or
     *         with status {@code 500 (Internal Server Error)} if the
     *         hearingDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hearing-details/{id}")
    public ResponseEntity<HearingDetailsDTO> updateHearingDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HearingDetailsDTO hearingDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HearingDetails : {}, {}", id, hearingDetailsDTO);
        if (hearingDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hearingDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hearingDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HearingDetailsDTO result = hearingDetailsService.update(hearingDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hearingDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hearing-details/:id} : Partial updates given fields of an
     * existing hearingDetails, field will ignore if it is null
     *
     * @param id                the id of the hearingDetailsDTO to save.
     * @param hearingDetailsDTO the hearingDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated hearingDetailsDTO, or with status
     *         {@code 400 (Bad Request)} if the hearingDetailsDTO is not valid, or
     *         with status {@code 404 (Not Found)} if the hearingDetailsDTO is not
     *         found, or with status {@code 500 (Internal Server Error)} if the
     *         hearingDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hearing-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HearingDetailsDTO> partialUpdateHearingDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HearingDetailsDTO hearingDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HearingDetails partially : {}, {}", id, hearingDetailsDTO);
        if (hearingDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hearingDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hearingDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HearingDetailsDTO> result = hearingDetailsService.partialUpdate(hearingDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hearingDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hearing-details} : get all the hearingDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of hearingDetails in body.
     */
    @GetMapping("/hearing-details")
    public ResponseEntity<List<HearingDetailsDTO>> getAllHearingDetails(
        HearingDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get HearingDetails by criteria: {}", criteria);
        Page<HearingDetailsDTO> page = hearingDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hearing-details/count} : count all the hearingDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/hearing-details/count")
    public ResponseEntity<Long> countHearingDetails(HearingDetailsCriteria criteria) {
        log.debug("REST request to count HearingDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(hearingDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hearing-details/:id} : get the "id" hearingDetails.
     *
     * @param id the id of the hearingDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the hearingDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hearing-details/{id}")
    public ResponseEntity<HearingDetailsDTO> getHearingDetails(@PathVariable Long id) {
        log.debug("REST request to get HearingDetails : {}", id);
        Optional<HearingDetailsDTO> hearingDetailsDTO = hearingDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hearingDetailsDTO);
    }

    /**
     * {@code DELETE  /hearing-details/:id} : delete the "id" hearingDetails.
     *
     * @param id the id of the hearingDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hearing-details/{id}")
    public ResponseEntity<Void> deleteHearingDetails(@PathVariable Long id) {
        log.debug("REST request to delete HearingDetails : {}", id);
        hearingDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /hearing-details/todaysHearing/report} : get today's Hearing Pdf
     * report.
     *
     * @param HearingDetailsCriteria,dakCriteria the criteria which the requested
     *                                           hearing details ,dak Master
     *                                           entities should match
     * @param criteria                           the criteria which the requested
     *                                           entities should match.
     * @return the {@link ResponseEntity <byte[]>} with status {@code 200 (OK)}.
     */
    @GetMapping("/hearing-details/todaysHearing/report")
    public ResponseEntity<?> getTodaysHearingPdfReport(HearingDetailsCriteria criteria, DakMasterCriteria dakCriteria) {
        log.debug("REST request to get HearingDetails by criteria: {}", criteria);

        List<DakMasterDTO> dakList = new ArrayList<DakMasterDTO>();
        List<HearingDetailsDTO> hearingList3 = new ArrayList<HearingDetailsDTO>();
        Long dakId = null;
        Optional<DakMasterDTO> dakMasterDTO = null;
        ResponseEntity<byte[]> response = null;
        Optional<HearingDetailsDTO> hearingDetailsDTO = null;

        // For today midnight instant date
        Long time = new Date().getTime();
        Date startDate = new Date(time - time % (24 * 60 * 60 * 1000));
        Instant todayMidnight = startDate.toInstant();

        // For tomorrow midnight instant date
        Date endDate = new Date(startDate.getTime() + 23 * 50 * 59 * 1000);
        Instant nextMidnight = endDate.toInstant();

        InstantFilter dateFilter = new InstantFilter();
        dateFilter.setGreaterThanOrEqual(todayMidnight);
        dateFilter.setLessThanOrEqual(nextMidnight);

        Long organizationId = dakCriteria.getOrganizationId().getEquals();

        Optional<OrganizationDTO> organizationDTO = organizationService.findOne(organizationId);

        if (criteria.getStatus() != null) {
            criteria.setDate(dateFilter);
            List<HearingDetailsDTO> hearingList = hearingDetailsQueryService.findByCriteria(criteria);

            for (HearingDetailsDTO hearingObj : hearingList) {
                dakId = hearingObj.getDakMasterId();
                dakMasterDTO = dakMasterService.findOne(dakId);
                Long orgId = dakMasterDTO.get().getOrganization().getId();

                if (dakCriteria.getOrganizationId().getEquals() == orgId && dakMasterDTO.get().getCurrentStatus() == DakStatus.HEARING) {
                    if (!dakList.contains(dakMasterDTO.get())) {
                        dakList.add(dakMasterDTO.get());
                        /*
                         * HearingDetailsCriteria hearingDetailsCriteria=new HearingDetailsCriteria();
                         * LongFilter dakMasterId2=new LongFilter();
                         * dakMasterId2.setEquals(dakMasterId);
                         * hearingDetailsCriteria.setDakMasterId(dakMasterId2); List<HearingDetailsDTO>
                         * hearingList2 =
                         * hearingDetailsQueryService.findByCriteria(hearingDetailsCriteria);
                         *
                         */

                    }
                }
            }

            // -----------------create source file in html-------

            String htmlReport = hearingDetailsService.getTodaysHearingReport(
                organizationDTO.get().getOrganizationName(),
                hearingList,
                dakCriteria
            );

            // ---------------Create Pdf using Chrome -----------
            response = dakMasterService.createPdfFromHtml(htmlReport);
        }
        return response;
    }

    /**
     * {@code GET  /hearing-details/todaysHearing} : get today's Hearing report.
     *
     * @param HearingDetailsCriteria,dakCriteria the criteria which the requested
     *                                           hearing details ,dak Master
     *                                           entities should match
     * @param criteria                           the criteria which the requested
     *                                           entities should match.
     * @return the {@link ResponseEntity <List<DakMasterDTO>>} with status
     *         {@code 200 (OK)}.
     */
    @GetMapping("/hearing-details/todaysHearing")
    public ResponseEntity<List<DakMasterDTO>> getTodaysHearingS(
        HearingDetailsCriteria criteria,
        DakMasterCriteria dakCriteria,
        Pageable page
    ) {
        log.debug("REST request to get HearingDetails by criteria: {}", criteria);

        List<DakMasterDTO> dakList = new ArrayList<DakMasterDTO>();
        Long dakId = null;
        Optional<DakMasterDTO> dakMasterDTO = null;
        ResponseEntity<byte[]> response = null;

        // For today midnight instant date
        Long time = new Date().getTime();
        Date startDate = new Date(time - time % (24 * 60 * 60 * 1000));
        Instant todayMidnight = startDate.toInstant();

        // For tomorrow midnight instant date
        Date endDate = new Date(startDate.getTime() + 23 * 50 * 59 * 1000);
        Instant nextMidnight = endDate.toInstant();

        InstantFilter dateFilter = new InstantFilter();
        dateFilter.setGreaterThanOrEqual(todayMidnight);
        dateFilter.setLessThanOrEqual(nextMidnight);

        if (criteria.getStatus() != null) {
            criteria.setDate(dateFilter);
            List<HearingDetailsDTO> hearingList = hearingDetailsQueryService.findByCriteria(criteria);

            for (HearingDetailsDTO hearingObj : hearingList) {
                dakId = hearingObj.getDakMasterId();

                dakMasterDTO = dakMasterService.findOne(dakId);
                Long orgId = dakMasterDTO.get().getOrganization().getId();

                if (dakCriteria.getOrganizationId().getEquals() == orgId && dakMasterDTO.get().getCurrentStatus() == DakStatus.HEARING) {
                    if (!dakList.contains(dakMasterDTO.get())) {
                        dakList.add(dakMasterDTO.get());
                    }
                }
            }
        }
        Page<DakMasterDTO> dakPage = this.convertListIntoPagable(dakList, page);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), dakPage);
        return ResponseEntity.ok().headers(headers).body(dakPage.getContent());
    }

    public Page<DakMasterDTO> convertListIntoPagable(List<DakMasterDTO> dakList, Pageable page) {
        int startOfPage = page.getPageNumber() * page.getPageSize();
        if (startOfPage > dakList.size()) {
            return new PageImpl<>(new ArrayList<>(), page, 0);
        }

        int endOfPage = Math.min(startOfPage + page.getPageSize(), dakList.size());
        return new PageImpl<>(dakList.subList(startOfPage, endOfPage), page, dakList.size());
    }
}
