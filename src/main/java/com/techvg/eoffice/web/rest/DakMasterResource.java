package com.techvg.eoffice.web.rest;

import com.techvg.eoffice.domain.Count;
import com.techvg.eoffice.domain.DashboardCount;
import com.techvg.eoffice.domain.EmployeeCount;
import com.techvg.eoffice.domain.MarkerAssignedLetter;
import com.techvg.eoffice.domain.OutwardNumber;
import com.techvg.eoffice.domain.enumeration.DakStatus;
import com.techvg.eoffice.repository.DakMasterRepository;
import com.techvg.eoffice.service.CommentMasterService;
import com.techvg.eoffice.service.DakMasterCoustomQueryService;
import com.techvg.eoffice.service.DakMasterQueryService;
import com.techvg.eoffice.service.DakMasterService;
import com.techvg.eoffice.service.SecurityUserQueryService;
import com.techvg.eoffice.service.criteria.DakMasterCriteria;
import com.techvg.eoffice.service.criteria.SecurityUserCriteria;
import com.techvg.eoffice.service.dto.CommentMasterDTO;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import com.techvg.eoffice.service.dto.SecurityRoleDTO;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import com.techvg.eoffice.web.rest.errors.BadRequestAlertException;
import com.techvg.eoffice.web.rest.vm.DakSearchCriteria;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.eoffice.domain.DakMaster}.
 */
@RestController
@RequestMapping("/api")
public class DakMasterResource {

    private final Logger log = LoggerFactory.getLogger(DakMasterResource.class);

    private static final String ENTITY_NAME = "dakMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DakMasterService dakMasterService;

    private final DakMasterRepository dakMasterRepository;

    private final DakMasterQueryService dakMasterQueryService;

    private final DakMasterCoustomQueryService dakMasterCoustomQueryService;

    private final CommentMasterService commentMasterService;

    @Autowired
    private SecurityUserQueryService securityUserQueryService;

    public DakMasterResource(
        DakMasterService dakMasterService,
        DakMasterRepository dakMasterRepository,
        DakMasterQueryService dakMasterQueryService,
        DakMasterCoustomQueryService dakMasterCoustomQueryService,
        CommentMasterService commentMasterService
    ) {
        this.dakMasterService = dakMasterService;
        this.dakMasterRepository = dakMasterRepository;
        this.dakMasterQueryService = dakMasterQueryService;
        this.dakMasterCoustomQueryService = dakMasterCoustomQueryService;
        this.commentMasterService = commentMasterService;
    }

    /**
     * {@code POST  /dak-masters} : Create a new dakMaster.
     *
     * @param dakMasterDTO the dakMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new dakMasterDTO, or with status {@code 400 (Bad Request)}
     *         if the dakMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dak-masters")
    public ResponseEntity<DakMasterDTO> createDakMaster(@RequestBody DakMasterDTO dakMasterDTO) throws URISyntaxException {
        log.debug("REST request to save DakMaster : {}", dakMasterDTO);
        if (dakMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new dakMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dakMasterDTO.getInwardNumber() != null) {
            throw new BadRequestAlertException("A new dakMaster cannot already have an Inward Number", ENTITY_NAME, "inwardNumberexists");
        }
        DakMasterDTO result = dakMasterService.save(dakMasterDTO);
        return ResponseEntity
            .created(new URI("/api/dak-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dak-masters/:id} : Updates an existing dakMaster.
     *
     * @param id           the id of the dakMasterDTO to save.
     * @param dakMasterDTO the dakMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated dakMasterDTO, or with status {@code 400 (Bad Request)} if
     *         the dakMasterDTO is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the dakMasterDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dak-masters/{id}")
    public ResponseEntity<DakMasterDTO> updateDakMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DakMasterDTO dakMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DakMaster : {}, {}", id, dakMasterDTO);
        if (dakMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dakMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dakMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        if (dakMasterDTO.getCurrentStatus().equals(DakStatus.CLEARED)) {
            if (dakMasterDTO.isHasOutward()) {
                OutwardNumber outwardNumber = dakMasterService.getOutwardNumber(dakMasterDTO.getOrganization().getId());
                int nextOutwardId = dakMasterRepository.findNextDakOutward(dakMasterDTO.getOrganization().getId());
                dakMasterRepository.updateNextDakOutward(nextOutwardId, nextOutwardId + 1, dakMasterDTO.getOrganization().getId());
                dakMasterDTO.setOutwardNumber(outwardNumber.getOutWardNumber());
            }

            // ------------Save comment for cleared letter -----

            if (dakMasterDTO.getComment() != null) {
                CommentMasterDTO commentMasterDTO = new CommentMasterDTO();
                commentMasterDTO.setDescription(dakMasterDTO.getComment());
                commentMasterDTO.setDakMaster(dakMasterDTO);
                commentMasterDTO.setSecurityUser(dakMasterDTO.getDakAssignee());
                commentMasterDTO.setStatus(true);
                commentMasterDTO.setCreatedOn(Instant.now());
                commentMasterDTO.setCreatedBy(dakMasterDTO.getDakAssignee().getFirstName());
                commentMasterDTO.setLastModified(Instant.now());

                commentMasterService.save(commentMasterDTO);
            }
        }

        DakMasterDTO result = dakMasterService.update(dakMasterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dakMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dak-masters/:id} : Partial updates given fields of an existing
     * dakMaster, field will ignore if it is null
     *
     * @param id           the id of the dakMasterDTO to save.
     * @param dakMasterDTO the dakMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated dakMasterDTO, or with status {@code 400 (Bad Request)} if
     *         the dakMasterDTO is not valid, or with status {@code 404 (Not Found)}
     *         if the dakMasterDTO is not found, or with status
     *         {@code 500 (Internal Server Error)} if the dakMasterDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dak-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DakMasterDTO> partialUpdateDakMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DakMasterDTO dakMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DakMaster partially : {}, {}", id, dakMasterDTO);
        if (dakMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dakMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dakMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DakMasterDTO> result = dakMasterService.partialUpdate(dakMasterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dakMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dak-masters} : get all the dakMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of dakMasters in body.
     */
    @GetMapping("/dak-masters")
    public ResponseEntity<List<DakMasterDTO>> getAllDakMasters(
        DakMasterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DakMasters by criteria: {}", criteria);
        Page<DakMasterDTO> page = dakMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dak-masters/count} : count all the dakMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/dak-masters/count")
    public ResponseEntity<Count> countDakMasters(DakMasterCriteria criteria) {
        log.debug("REST request to count DakMasters by criteria: {}", criteria);
        Count count = new Count();

        count.count = dakMasterQueryService.countByCriteria(criteria);

        return ResponseEntity.ok().body(count);
    }

    /**
     * {@code GET  /dak-masters/:id} : get the "id" dakMaster.
     *
     * @param id the id of the dakMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the dakMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dak-masters/{id}")
    public ResponseEntity<DakMasterDTO> getDakMaster(@PathVariable Long id) {
        log.debug("REST request to get DakMaster : {}", id);
        Optional<DakMasterDTO> dakMasterDTO = dakMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dakMasterDTO);
    }

    /**
     * {@code DELETE  /dak-masters/:id} : delete the "id" dakMaster.
     *
     * @param id the id of the dakMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dak-masters/{id}")
    public ResponseEntity<Void> deleteDakMaster(@PathVariable Long id) {
        log.debug("REST request to delete DakMaster : {}", id);
        dakMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /dak-masters/nondwahiReport} : get Pdf report of employee and
     * clerk.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of dakMasters in body.
     */
    @GetMapping("/dak-masters/nondwahiReport")
    public ResponseEntity<?> getDakMastersPdf(DakMasterCriteria criteria) {
        log.debug("REST request to get DakMasters by criteria: {}", criteria);
        List<DakMasterDTO> dakList = null;
        Instant fromDate = null;
        Instant toDate = null;
        ResponseEntity<byte[]> response = null;
        if (criteria.getDakAssignee() != null || criteria.getDispatchedBy() != null) {
            dakList = dakMasterQueryService.findByCriteria(criteria);
        }

        if (
            criteria.getLetterReceivedDate().getGreaterThanOrEqual() != null &&
            criteria.getLetterReceivedDate().getLessThanOrEqual() != null
        ) {
            fromDate = criteria.getLetterReceivedDate().getGreaterThanOrEqual();
            toDate = criteria.getLetterReceivedDate().getLessThanOrEqual();
        }

        SecurityUserCriteria userCriteria = new SecurityUserCriteria();
        if (criteria.getDakAssignee() != null) {
            userCriteria.setId(criteria.getDakAssignee());
        } else {
            userCriteria.setId(criteria.getDispatchedBy());
        }
        // Ask to raksha
        List<SecurityUserDTO> usersList = securityUserQueryService.findByCriteria(userCriteria);
        SecurityUserDTO user = usersList.get(0);

        SecurityRoleDTO userRole = user.getSecurityRoles().iterator().next();
        String orgName = user.getOrganization().getOrganizationName();

        // -----------------create source file in html-------
        String htmlReport = dakMasterService.getNondWahiReport(user, fromDate, toDate, dakList, userRole, orgName);

        // ---------------Create Pdf using Chrome ---------
        response = dakMasterService.createPdfFromHtml(htmlReport);

        return response;
    }

    /**
     * {@code GET  /dak-masters/assignedCount} : count all the dakMasters for
     * Employees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the List
     *         of EmployeeCount in body.
     */

    @GetMapping("/dak-masters/assignedCount")
    public ResponseEntity<List<EmployeeCount>> employeeAssignedDakcount(
        DakMasterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to count DakMasters by criteria: {}", criteria);

        Page<EmployeeCount> page;
        if (criteria.getOrganizationId() != null) {
            page = dakMasterService.getEmployeeDakCount(criteria, pageable);
        } else {
            throw new BadRequestAlertException("Please select organization in criteria", ENTITY_NAME, "criteria");
        }
        // Page<DakMasterDTO> page = dakMasterQueryService.findByCriteria(criteria,
        // pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET /dak-masters/handedover/report/pdf/{date}} : get Pdf report of
     * handedOver letters by marker to employees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of Pdf in body.
     */
    @GetMapping("/dak-masters/handedover/report/pdf/{date}")
    public ResponseEntity<?> getHandedOverReportByDate(DakMasterCriteria criteria, @PathVariable("date") Instant date) {
        log.debug("REST request to get DakMasters by criteria: {}", criteria);

        Set<MarkerAssignedLetter> assignedLetters = null;

        if (criteria.getDakAssignedFrom() != null && criteria.getAssignedDate() != null && criteria.getOrganizationId() != null) {
            assignedLetters = dakMasterService.getAssinedListCountByDate(criteria);
        }

        SecurityUserCriteria userCriteria = new SecurityUserCriteria();
        userCriteria.setId(criteria.getDakAssignedFrom());
        List<SecurityUserDTO> usersList = securityUserQueryService.findByCriteria(userCriteria);
        SecurityUserDTO user = usersList.get(0);
        String orgName = user.getOrganization().getOrganizationName();

        // -----------------create source file in html-------
        String htmlReport = dakMasterService.getHandedOverReport(date, assignedLetters, orgName);

        // ---------------Create Pdf using Chrome ---------
        ResponseEntity<byte[]> response = dakMasterService.createPdfFromHtml(htmlReport);

        return response;
    }

    /**
     * {@code GET  /dak-masters/assignedLetters/report} : get Pdf report of employee
     * Assigned letters by marker.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of Pdf in body.
     */
    @GetMapping("/dak-masters/assignedLetters/report/pdf")
    public ResponseEntity<?> getDakAssignedListByMarker(DakMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DakMasters by criteria: {}", criteria);
        Page<DakMasterDTO> dakList = null;
        Instant fromDate = null;
        Instant toDate = null;
        if (criteria.getDakAssignedFrom() != null) {
            dakList = dakMasterQueryService.findByCriteria(criteria, pageable);
        }

        SecurityUserCriteria userCriteria = new SecurityUserCriteria();
        userCriteria.setId(criteria.getDakAssignedFrom());
        List<SecurityUserDTO> usersList = securityUserQueryService.findByCriteria(userCriteria);
        SecurityUserDTO user = usersList.get(0);
        String orgName = user.getOrganization().getOrganizationName();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String todaysdate = dateFormat.format(date);

        // -----------------create source file in html-------
        String htmlReport = dakMasterService.getMarkerAssignedReport(todaysdate, dakList.getContent(), orgName);

        // ---------------Create Pdf using Chrome ---------
        ResponseEntity<byte[]> response = dakMasterService.createPdfFromHtml(htmlReport);

        return response;
    }

    @PutMapping("/dak-masters/transfer/{id}")
    public ResponseEntity<DakMasterDTO> transferDakMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DakMasterDTO dakMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DakMaster : {}, {}", id, dakMasterDTO);
        if (dakMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dakMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dakMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DakMasterDTO result = dakMasterService.transferLetter(dakMasterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dakMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET /dak-masters/{organizationId}} : Get outward number.
     *
     * @param organization id .
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/dak-masters/outward/{organizationId}")
    public ResponseEntity<OutwardNumber> getOutWardNumber(@PathVariable Long organizationId) {
        log.debug("REST request to get outward number by organization: {}", organizationId);
        OutwardNumber outwardNumber = dakMasterService.getOutwardNumber(organizationId);
        return ResponseEntity.ok().body(outwardNumber);
    }

    /**
     * {@code GET /dak-masters/handedover/report} : get report of handedOver letters
     * by marker to employees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the set
     *         of MarkerAssignedLetter in body.
     */
    @GetMapping("/dak-masters/handedover/report")
    public ResponseEntity<List<MarkerAssignedLetter>> getHandedOverReport(DakMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DakMasters by criteria: {}", criteria, pageable);

        Set<MarkerAssignedLetter> assignedLetters = null;

        if (criteria.getDakAssignedFrom() != null && criteria.getAssignedDate() != null && criteria.getOrganizationId() != null) {
            assignedLetters = dakMasterService.getAssinedListCountByDate(criteria);
        }

        List<MarkerAssignedLetter> list = this.convertSetToList(assignedLetters);

        Page<MarkerAssignedLetter> assignedListPage = this.convertListIntoPagable(list, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            assignedListPage
        );
        return ResponseEntity.ok().headers(headers).body(assignedListPage.getContent());
    }

    /**
     * {@code GET  /dak-masters/searchDak} : get all the dakMasters with
     * dakSearchCriteria.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of dakMasters in body.
     */
    @GetMapping("/dak-masters/searchDak")
    public ResponseEntity<List<DakMasterDTO>> getDakMastersBetweenInwards(DakSearchCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DakMasters by criteria: {}", criteria);

        Page<DakMasterDTO> page = dakMasterCoustomQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/dak-master/dashboard")
    public ResponseEntity<List<DashboardCount>> dashbordDakcount(
        DakMasterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to count DakMasters by criteria: {}", criteria);

        Page<DashboardCount> page;
        if (criteria.getOrganizationId() != null) {
            page = dakMasterService.getDashboardCount(criteria, pageable);
        } else {
            throw new BadRequestAlertException("Please select organization in criteria", ENTITY_NAME, "criteria");
        }
        // Page<DakMasterDTO> page = dakMasterQueryService.findByCriteria(criteria,
        // pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    public List<MarkerAssignedLetter> convertSetToList(Set<MarkerAssignedLetter> set) {
        // create an empty list
        List<MarkerAssignedLetter> list = new ArrayList<>();

        // push each element in the set into the list
        for (MarkerAssignedLetter markerAssignedLetter : set) list.add(markerAssignedLetter);

        // return the list
        return list;
    }

    public Page<MarkerAssignedLetter> convertListIntoPagable(List<MarkerAssignedLetter> dakList, Pageable page) {
        int startOfPage = page.getPageNumber() * page.getPageSize();
        if (startOfPage > dakList.size()) {
            return new PageImpl<>(new ArrayList<>(), page, 0);
        }

        int endOfPage = Math.min(startOfPage + page.getPageSize(), dakList.size());
        return new PageImpl<>(dakList.subList(startOfPage, endOfPage), page, dakList.size());
    }
}
