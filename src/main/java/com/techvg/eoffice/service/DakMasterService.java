package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.DakMaster;
import com.techvg.eoffice.domain.DashboardCount;
import com.techvg.eoffice.domain.EmployeeCount;
import com.techvg.eoffice.domain.MarkerAssignedLetter;
import com.techvg.eoffice.domain.OutwardNumber;
import com.techvg.eoffice.domain.Pdf;
import com.techvg.eoffice.domain.enumeration.DakStatus;
import com.techvg.eoffice.domain.enumeration.LetterType;
import com.techvg.eoffice.repository.DakMasterRepository;
import com.techvg.eoffice.repository.HearingDetailsRepository;
import com.techvg.eoffice.service.criteria.DakMasterCriteria;
import com.techvg.eoffice.service.criteria.DakMasterCriteria.DakStatusFilter;
import com.techvg.eoffice.service.criteria.HearingDetailsCriteria;
import com.techvg.eoffice.service.criteria.SecurityRoleCriteria;
import com.techvg.eoffice.service.criteria.SecurityUserCriteria;
import com.techvg.eoffice.service.dto.CommentMasterDTO;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import com.techvg.eoffice.service.dto.HearingDetailsDTO;
import com.techvg.eoffice.service.dto.SecurityRoleDTO;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import com.techvg.eoffice.service.mapper.DakMasterMapper;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.RangeFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing {@link DakMaster}.
 */
@Service
@Transactional
public class DakMasterService {

    private final Logger log = LoggerFactory.getLogger(DakMasterService.class);

    private final DakMasterRepository dakMasterRepository;

    private final DakMasterMapper dakMasterMapper;

    private final DakMasterQueryService dakMasterQueryService;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private SecurityUserQueryService securityUserQueryService;

    @Autowired
    private SecurityRoleQueryService securityRoleQueryService;

    @Autowired
    private CommentMasterService commentMasterService;

    @Autowired
    private HearingDetailsQueryService hearingDetailsQueryService;

    @Value("${pdf.output.chrome}")
    private String pdf_output_chrome;

    @Value("${source.file.path}")
    private String source_file_path;

    public DakMasterService(
        DakMasterRepository dakMasterRepository,
        DakMasterMapper dakMasterMapper,
        DakMasterQueryService dakMasterQueryService
    ) {
        this.dakMasterRepository = dakMasterRepository;
        this.dakMasterMapper = dakMasterMapper;
        this.dakMasterQueryService = dakMasterQueryService;
    }

    /**
     * Save a dakMaster.
     *
     * @param dakMasterDTO the entity to save.
     * @return the persisted entity.
     */

    public DakMasterDTO save(DakMasterDTO dakMasterDTO) {
        log.debug("Request to save DakMaster : {}", dakMasterDTO);

        // -------create Inward Number for Letter Type Self and other
        if (dakMasterDTO.getInwardNumber() == null) {
            if (dakMasterDTO.getLetterType() != null && dakMasterDTO.getLetterType().equals(LetterType.SELF)) {
                SecurityUserDTO user = dakMasterDTO.getDakAssignee();
                Optional<SecurityUserDTO> userObj = securityUserService.findOne(user.getId());

                String inwardForSelfGeneretedDak = this.getSelfGenratedId(userObj.get(), dakMasterDTO.getOrganization().getId());
                dakMasterDTO.setInwardNumber(inwardForSelfGeneretedDak);
                dakMasterDTO.setCurrentStatus(DakStatus.ASSIGNED);
            } else {
                String inwardForDak = this.getOrgInwardNumber(dakMasterDTO.getOrganization().getId());
                dakMasterDTO.setInwardNumber(inwardForDak);
                dakMasterDTO.setCurrentStatus(DakStatus.CREATED);
            }
        }

        DakMaster dakMaster = dakMasterMapper.toEntity(dakMasterDTO);
        dakMaster = dakMasterRepository.save(dakMaster);
        return dakMasterMapper.toDto(dakMaster);
    }

    /**
     * Update a dakMaster.
     *
     * @param dakMasterDTO the entity to save.
     * @return the persisted entity.
     */

    public DakMasterDTO update(DakMasterDTO dakMasterDTO) {
        log.debug("Request to save DakMaster : {}", dakMasterDTO);
        DakMaster dakMaster = dakMasterMapper.toEntity(dakMasterDTO);
        dakMaster = dakMasterRepository.save(dakMaster);
        return dakMasterMapper.toDto(dakMaster);
    }

    /**
     * Partially update a dakMaster.
     *
     * @param dakMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DakMasterDTO> partialUpdate(DakMasterDTO dakMasterDTO) {
        log.debug("Request to partially update DakMaster : {}", dakMasterDTO);

        return dakMasterRepository
            .findById(dakMasterDTO.getId())
            .map(existingDakMaster -> {
                dakMasterMapper.partialUpdate(existingDakMaster, dakMasterDTO);

                return existingDakMaster;
            })
            .map(dakMasterRepository::save)
            .map(dakMasterMapper::toDto);
    }

    /**
     * Get all the dakMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DakMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DakMasters");
        return dakMasterRepository.findAll(pageable).map(dakMasterMapper::toDto);
    }

    /**
     * Get all the dakMasters with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DakMasterDTO> findAllWithEagerRelationships(Pageable pageable) {
        return dakMasterRepository.findAllWithEagerRelationships(pageable).map(dakMasterMapper::toDto);
    }

    /**
     * Get one dakMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DakMasterDTO> findOne(Long id) {
        log.debug("Request to get DakMaster : {}", id);
        return dakMasterRepository.findOneWithEagerRelationships(id).map(dakMasterMapper::toDto);
    }

    /**
     * Delete the dakMaster by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DakMaster : {}", id);
        dakMasterRepository.deleteById(id);
    }

    /**
     * Get WorkDescription report of Employee and clerk.
     *
     * @return string.
     */

    public String getNondWahiReport(
        SecurityUserDTO user,
        Instant fromDate,
        Instant toDate,
        List<DakMasterDTO> dakList,
        SecurityRoleDTO userRole,
        String orgName
    ) {
        StringBuilder data = new StringBuilder();

        int count = 1;

        for (DakMasterDTO newDak : dakList) {
            String senderOutward = newDak.getSenderOutward() != null ? newDak.getSenderOutward() : "";

            String senderName = newDak.getSenderName() != null ? newDak.getSenderName() : "";

            String subject = newDak.getSubject() != null ? newDak.getSubject() : "";

            data.append(
                "<tr> " +
                "<td>" +
                count +
                "</td>" +
                "" +
                "    <td>" +
                newDak.getInwardNumber() +
                "</td>" +
                "<td>" +
                convertInstantToString(newDak.getLetterReceivedDate()) +
                "</td>" +
                " <td>" +
                senderOutward +
                "</td>" +
                "  " +
                "  <td>" +
                convertInstantToString(newDak.getLetterDate()) +
                "</td>" +
                "<td>" +
                senderName +
                "</td>" +
                "    <td>" +
                subject +
                "</td>" +
                "    <td>" +
                newDak.getCurrentStatus() +
                "</td>" +
                "</tr>"
            );
            count++;
        }

        String employeeName = user.getFirstName() + " " + user.getLastName();

        // Inserting title for Assistant_Role
        String title;

        if ("ROLE_CLERK".equalsIgnoreCase(userRole.getRoleName())) {
            title = "कार्यविवरण आवक  नोंदवही";
        } else {
            title = "कार्यविवरण  नोंदवही";
        }

        // ------------------HTML structure for work description
        // report-------------------

        String html =
            "<!DOCTYPE html>\r\n" +
            "<html>\r\n" +
            "<head>\r\n" +
            "<meta charset=\"UTF-8\">\r\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\">\r\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n" +
            "</head>\r\n" +
            "<body>\r\n" +
            "<h3 style=\"text-align: center\">" +
            orgName +
            "</h3>\r\n" +
            "<h5 style=\"text-align: center\">" +
            title +
            "</h5>\r\n" +
            "<h4 style=\"text-align: left\">कर्मचाऱ्याचे नाव : " +
            employeeName +
            " </h4>\r\n" +
            "<style >\r\n" +
            "\r\n" +
            "	\r\n" +
            "@page {\r\n" +
            "   size: landscape; \r\n" +
            " }\r\n" +
            "table {\r\n" +
            "	 font-family: arial, sans-serif;\r\n" +
            "	 border-collapse: collapse;\r\n" +
            "	 width: 100%;\r\n" +
            "      }\r\n" +
            "	th {\r\n" +
            "		background-color:#f2f2f2; \r\n" +
            "		border: 1px solid #dddddd;\r\n" +
            "		text-align: center;\r\n" +
            "		padding: 8px;\r\n" +
            "		}\r\n" +
            "		td {\r\n" +
            "		border: 1px solid #dddddd; \r\n" +
            "		text-align: left; \r\n" +
            "		padding: 8px; \r\n" +
            "		}\r\n" +
            "				\r\n" +
            "		</style>\r\n" +
            "				\r\n" +
            "<table>\r\n" +
            "\r\n" +
            "  <tr>\r\n" +
            "  <th></th>\r\n" +
            "  <th colspan=3>आवक नोंदवही</th>\r\n" +
            "    <th colspan=5>दिनांक:" +
            convertInstantToString(fromDate) +
            "  ते " +
            convertInstantToString(toDate) +
            "</th>\r\n" +
            "    \r\n" +
            "  </tr>\r\n" +
            "  \r\n" +
            "  <tr>\r\n" +
            "    <th> </th>\r\n" +
            "    <th colspan=2>नोंदवहीतील आवक</th>\r\n" +
            "    <th colspan=2>आवक संदर्भाचा</th>\r\n" +
            "    <th> </th>\r\n" +
            "	<th> </th>\r\n" +
            "	<th> </th>\r\n" +
            "  </tr>\r\n" +
            "  <tr>\r\n" +
            "    <th>अ.क्र.</th>\r\n" +
            "    <th style=\"width:7rem\">आवक क्रमांक</th>\r\n" +
            "    <th style=\"width:6rem\">प्राप्त दिनांक</th>\r\n" +
            "    <th style=\"width:3rem\">जावक.क्र</th>\r\n" +
            "	<th style=\"width:6rem\">पत्राचा दिनांक</th>\r\n" +
            "	<th>पाठविणार</th>\r\n" +
            "	<th>विषय​</th>\r\n" +
            "	<th>शेरा</th>\r\n" +
            "  </tr>\r\n" +
            data.toString() +
            " \r\n" +
            "    \r\n" +
            " </table>\r\n" +
            "\r\n" +
            "</body>\r\n" +
            "</html>\r\n" +
            "";

        return html;
    }

    /**
     * Get Assigned Letter report of Employee for Marker.
     *
     * @return Html string.
     */

    public String getMarkerAssignedReport(String date, List<DakMasterDTO> assignedLetters, String orgDescription) {
        StringBuilder builder = new StringBuilder();

        Iterator<DakMasterDTO> iterator = assignedLetters.iterator();

        int count = 1;
        while (iterator.hasNext()) {
            DakMasterDTO markerAssignedLetter = iterator.next();

            Optional<SecurityUserDTO> securityUserDTO = securityUserService.findOne(markerAssignedLetter.getDakAssignee().getId());

            builder.append(
                "<tr>\r\n" +
                "<td>" +
                count +
                "</td>\r\n" +
                "<td>" +
                markerAssignedLetter.getInwardNumber() +
                "</td>\r\n" +
                "</td>\r\n" +
                "<td>" +
                convertInstantToString(markerAssignedLetter.getLetterDate()) +
                "</td>\r\n" +
                "<td>" +
                convertInstantToString(markerAssignedLetter.getLetterReceivedDate()) +
                "</td>\r\n" +
                "<td>" +
                convertInstantToString(markerAssignedLetter.getCreatedOn()) +
                "</td>\r\n" +
                "<td>" +
                markerAssignedLetter.getSenderName() +
                "</td>\r\n" +
                "<td>" +
                markerAssignedLetter.getSubject() +
                "</td>\r\n" +
                "<td>" +
                securityUserDTO.get().getFirstName() +
                " " +
                securityUserDTO.get().getLastName() +
                "</td>\r\n" +
                "  </tr>\r\n"
            );

            count++;
        }

        String html =
            "<!DOCTYPE html>\r\n" +
            "<html>\r\n" +
            "<head>\r\n" +
            "<meta charset=\"UTF-8\">\r\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\">\r\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n" +
            "</head>\r\n" +
            "<body>\r\n" +
            "<h3 style=\"text-align: center\">" +
            orgDescription +
            "</h3>\r\n" +
            "<h5 style=\"text-align: center\">पत्रव्यवहार - सुपुर्द अहवाल</h5>\r\n" +
            /* "<h4 style=\"text-align: left\">कर्मचाऱ्याचे नाव :+ +</h4>\r\n" + */
            "<style>\r\n" +
            "@page {\r\n" +
            "   size: landscape; \r\n" +
            " }\r\n" +
            "table {\r\n" +
            "	 font-family: arial, sans-serif;\r\n" +
            "	 border-collapse: collapse;\r\n" +
            "	 width: 100%;\r\n" +
            "      }\r\n" +
            "	th {\r\n" +
            "		background-color:#f2f2f2; \r\n" +
            "		border: 1px solid #dddddd;		\r\n" +
            "		text-align: center;\r\n" +
            "		padding: 8px;\r\n" +
            "		}\r\n" +
            "		td {\r\n" +
            "		border: 1px solid #dddddd; \r\n" +
            "		text-align: left; \r\n" +
            "		padding: 8px; \r\n" +
            "		}\r\n" +
            "				\r\n" +
            "		</style>\r\n" +
            "				\r\n" +
            "<table>\r\n" +
            "\r\n" +
            "  <tr>\r\n" +
            "    <th>अ.क्र.</th>\r\n" +
            "    <th>आवक क्र.</th>\r\n" +
            "	<th>पत्राचा दिनांक</th>\r\n" +
            "	<th>प्राप्त दिनांक</th>\r\n" +
            "	<th>नोंदणी दिनांक</th>\r\n" +
            "    <th>पाठविणार</th>\r\n" +
            "    <th>विषय</th>\r\n" +
            "	 <th>जबाबदारी</th>\r\n" +
            "  </tr>\r\n" +
            "<tr>" +
            builder.toString() +
            "</tr>" +
            "  \r\n" +
            // " <td style=\"width:180px\"></td>\r\n" +
            /* " \r\n" + */
            "</table>\r\n" +
            "\r\n" +
            "</body>\r\n" +
            "</html>\r\n" +
            "";

        return html;
    }

    /**
     * Create PDF report from Html using chrome.
     *
     * @return ResponseEntity<byte[]>.
     */

    public ResponseEntity<byte[]> createPdfFromHtml(String htmlReport) {
        File resource = null;

        try {
            resource = new ClassPathResource("reports/demo.text").getFile();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        File pdfOutPutFile = new File(pdf_output_chrome);
        Pdf pdf = new Pdf();

        try {
            pdf.createPdfUsingChrome(htmlReport, pdfOutPutFile, pdf_output_chrome, source_file_path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // put delay here

        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        byte[] contents = null;
        try {
            contents = Files.readAllBytes(pdfOutPutFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    /**
     * Method to Convert Instant date To String.
     *
     * @return date in string format.
     */

    public String convertInstantToString(Instant instant) {
        if (instant != null) {
            Date myDate = Date.from(instant);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = formatter.format(myDate);
            return formattedDate;
        }
        return "";
    }

    /**
     * create separate Dak Inward for self generated dak.
     *
     * @return inward Number string.
     */

    private String getSelfGenratedId(SecurityUserDTO user, Long organization) {
        String firstLetter = user.getFirstName() != null ? user.getFirstName().substring(0, 1) : "";
        String lastLetter = user.getLastName() != null ? user.getLastName().substring(0, 1) : "";

        String userInitials = firstLetter.toUpperCase() + lastLetter.toUpperCase();

        String nextDakIdString = null;

        int nextDakId = dakMasterRepository.findNextDakOutward(organization);
        dakMasterRepository.updateNextDakOutward(nextDakId, nextDakId + 1, organization);

        String formtedString = String.format("%05d", nextDakId);

        int year = Calendar.getInstance().get(Calendar.YEAR);

        nextDakIdString = userInitials + year + "-" + formtedString;

        return nextDakIdString;
    }

    /**
     * create separate Dak Inward for other type of dak.
     *
     * @return inward Number string.
     */
    private String getOrgInwardNumber(Long organization) {
        String nextDakIdString = null;

        int nextDakId = dakMasterRepository.findNextDakInward(organization);
        dakMasterRepository.updateNextDakInward(nextDakId, nextDakId + 1, organization);

        String formtedString = String.format("%05d", nextDakId);

        int year = Calendar.getInstance().get(Calendar.YEAR);

        nextDakIdString = "IN" + year + "-" + formtedString;

        return nextDakIdString;
    }

    /**
     * create separate Dak Outward for dak When letter status is cleared.
     *
     * @return outward Number string.
     */
    public OutwardNumber getOutwardNumber(Long organization) {
        String nextDakIdString = null;

        int nextOutwardId = dakMasterRepository.findNextDakOutward(organization);
        String formtedString = String.format("%05d", nextOutwardId);

        int year = Calendar.getInstance().get(Calendar.YEAR);

        nextDakIdString = "OW" + year + "-" + formtedString;

        OutwardNumber owNumberObj = new OutwardNumber();
        owNumberObj.setOutWardNumber(nextDakIdString);
        return owNumberObj;
    }

    /**
     * Calculate Employee inward dak count for DDR dashboard.
     *
     * @param DakMasterCriteria criteria.
     * @return List of EmployeeCount.
     */

    public Page<EmployeeCount> getEmployeeDakCount(DakMasterCriteria criteria, Pageable page) {
        List<EmployeeCount> empCountList = new ArrayList<EmployeeCount>();

        InstantFilter receivedDate = new InstantFilter();

        SecurityRoleCriteria roleCri = new SecurityRoleCriteria();
        StringFilter empFilter = new StringFilter();
        empFilter.setContains("ROLE_EMP");
        roleCri.setRoleName(empFilter);
        List<SecurityRoleDTO> roleList = securityRoleQueryService.findByCriteria(roleCri);

        SecurityUserCriteria userCri = new SecurityUserCriteria();
        LongFilter orgId = criteria.getOrganizationId();
        userCri.setOrganizationId(orgId);

        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(roleList.get(0).getId());
        userCri.setSecurityRoleId(longFilter);

        List<SecurityUserDTO> empList = securityUserQueryService.findByCriteria(userCri);

        for (SecurityUserDTO employee : empList) {
            String employeeName = employee.getFirstName() + " " + employee.getLastName();
            long empId = employee.getId();

            EmployeeCount empCountObj = new EmployeeCount();
            empCountObj.setEmployeeName(employeeName);
            empCountObj.setUserId(empId);

            // --------------Set Dak criteria dak assigned to employee
            longFilter.setEquals(empId);
            criteria.setDakAssignee(longFilter);

            // ------------------------Calculate Today's and last seventh Day
            // date---------------------------------------------------------------
            Calendar todayDate = Calendar.getInstance();
            todayDate.add(Calendar.HOUR_OF_DAY, 00);
            todayDate.add(Calendar.MINUTE, 59);
            todayDate.add(Calendar.SECOND, 59);
            todayDate.add(Calendar.MILLISECOND, 999);
            Date today = todayDate.getTime();

            Calendar last7thDay = Calendar.getInstance();
            last7thDay.add(Calendar.DAY_OF_MONTH, -7);
            last7thDay.set(Calendar.MILLISECOND, 0);
            last7thDay.set(Calendar.SECOND, 0);
            last7thDay.set(Calendar.MINUTE, 0);
            last7thDay.set(Calendar.HOUR_OF_DAY, 0);
            Date lastsevenDays = last7thDay.getTime();

            receivedDate.setLessThanOrEqual(today.toInstant());
            receivedDate.setGreaterThanOrEqual(lastsevenDays.toInstant());
            criteria.setLetterReceivedDate(receivedDate);

            Long sevenDaysCount = dakMasterQueryService.countByCriteria(criteria);
            empCountObj.setSevenDays(sevenDaysCount);

            // ------------------------Calculate last fifteenth day date-------------

            Calendar last15thDay = Calendar.getInstance();
            last15thDay.add(Calendar.DAY_OF_MONTH, -15);
            last15thDay.set(Calendar.MILLISECOND, 0);
            last15thDay.set(Calendar.SECOND, 0);
            last15thDay.set(Calendar.MINUTE, 0);
            last15thDay.set(Calendar.HOUR_OF_DAY, 0);

            Date lastFifteenDays = last15thDay.getTime();

            receivedDate.setLessThan(lastsevenDays.toInstant());
            receivedDate.setGreaterThanOrEqual(lastFifteenDays.toInstant());
            criteria.setLetterReceivedDate(receivedDate);

            Long eightToFifteenCount = dakMasterQueryService.countByCriteria(criteria);
            empCountObj.setEightToFifteen(eightToFifteenCount);

            // ------------------------Calculate last month day date-----------------

            Calendar last30thDay = Calendar.getInstance();
            last30thDay.add(Calendar.DAY_OF_MONTH, -30);
            last30thDay.set(Calendar.MILLISECOND, 0);
            last30thDay.set(Calendar.SECOND, 0);
            last30thDay.set(Calendar.MINUTE, 0);
            last30thDay.set(Calendar.HOUR_OF_DAY, 0);
            Date lastThrityDays = last30thDay.getTime();

            receivedDate.setLessThan(lastFifteenDays.toInstant());
            receivedDate.setGreaterThanOrEqual(lastThrityDays.toInstant());
            criteria.setLetterReceivedDate(receivedDate);

            Long fifteenToThirty = dakMasterQueryService.countByCriteria(criteria);

            empCountObj.setFifteenToThirty(fifteenToThirty);

            // ----------------------------------------------------------------------------------------
            // This for last 1- 3 months
            Calendar last2MonthDay = Calendar.getInstance();
            last2MonthDay = Calendar.getInstance();
            last2MonthDay.add(Calendar.MONTH, -3);
            last2MonthDay.add(Calendar.DAY_OF_MONTH, -1);
            last2MonthDay.set(Calendar.MILLISECOND, 0);
            last2MonthDay.set(Calendar.SECOND, 0);
            last2MonthDay.set(Calendar.MINUTE, 0);
            last2MonthDay.set(Calendar.HOUR_OF_DAY, 0);
            Date lastThreeMonth = last2MonthDay.getTime();

            receivedDate.setLessThan(lastThrityDays.toInstant());
            receivedDate.setGreaterThanOrEqual(lastThreeMonth.toInstant());
            criteria.setLetterReceivedDate(receivedDate);

            Long lastThreeMonths = dakMasterQueryService.countByCriteria(criteria);
            empCountObj.setOneToThreeMonth(lastThreeMonths);

            // -----------------------------------------------------------------------------
            // This for last more than 3 months
            Calendar last3MonthDay = Calendar.getInstance();
            last3MonthDay.add(Calendar.MONTH, -3);
            last3MonthDay.add(Calendar.DAY_OF_MONTH, -2);
            last3MonthDay.set(Calendar.MILLISECOND, 0);
            last3MonthDay.set(Calendar.SECOND, 0);
            last3MonthDay.set(Calendar.MINUTE, 0);
            last3MonthDay.set(Calendar.HOUR_OF_DAY, 0);
            Date lastThreemonth = last3MonthDay.getTime();

            Calendar oneYearBack = Calendar.getInstance();
            oneYearBack.add(Calendar.YEAR, -10);
            Date lastOneYear = oneYearBack.getTime();

            receivedDate.setLessThan(lastThreemonth.toInstant());
            receivedDate.setGreaterThanOrEqual(lastOneYear.toInstant());
            criteria.setLetterReceivedDate(receivedDate);

            Long moreThanThreeMonth = dakMasterQueryService.countByCriteria(criteria);
            empCountObj.setMoreThanThreeMonth(moreThanThreeMonth);

            // ----------------------------------------------------------------------------------------------
            // --------------for total count

            receivedDate.setLessThanOrEqual(today.toInstant());
            receivedDate.setGreaterThanOrEqual(lastOneYear.toInstant());
            criteria.setLetterReceivedDate(receivedDate);

            //Long total = dakMasterQueryService.countByCriteria(criteria);
            Long total = sevenDaysCount + eightToFifteenCount + fifteenToThirty + lastThreeMonths + moreThanThreeMonth;
            empCountObj.setTotal(total);
            // --------------------------------------------------------------------------------------------------------

            empCountList.add(empCountObj);
        }

        final int start = (int) page.getOffset();
        final int end = Math.min((start + page.getPageSize()), empCountList.size());
        final Page<EmployeeCount> pageList = new PageImpl<>(empCountList.subList(start, end), page, empCountList.size());

        return pageList;
    }

    /**
     * Calculate for handedOver letters list by marker.
     *
     * @param DakMasterCriteria criteria.
     * @return Set of MarkerAssignedLetter.
     */

    public Set<MarkerAssignedLetter> getAssinedListCountByDate(DakMasterCriteria criteria) {
        HashSet<MarkerAssignedLetter> empListByDate = new HashSet<MarkerAssignedLetter>();

        HashMap<String, ArrayList<String>> finalMap = new HashMap<String, ArrayList<String>>();

        SecurityUserCriteria userCri = new SecurityUserCriteria();
        LongFilter orgId = criteria.getOrganizationId();
        userCri.setOrganizationId(orgId);

        LongFilter userId = criteria.getDakAssignedFrom();
        userCri.setId(userId);

        List<SecurityUserDTO> user = securityUserQueryService.findByCriteria(userCri);
        Set<SecurityRoleDTO> roles = user.get(0).getSecurityRoles();
        String role = roles.iterator().next().getRoleName();

        ArrayList<String> list = new ArrayList<String>();

        if (role.equalsIgnoreCase("ROLE_MARKER") && criteria.getDakAssignedFrom() != null && criteria.getAssignedDate() != null) {
            List<DakMasterDTO> dakList = dakMasterQueryService.findByCriteria(criteria);
            for (int i = 0; i < dakList.size(); i++) {
                Optional<SecurityUserDTO> securityUserDTO = securityUserService.findOne(dakList.get(i).getDakAssignee().getId());
                String employeeName = securityUserDTO.get().getFirstName() + " " + securityUserDTO.get().getLastName();

                if (finalMap.containsKey(employeeName)) {
                    list = finalMap.get(employeeName);
                    list.add(dakList.get(i).getInwardNumber());
                    finalMap.put(employeeName, list);
                } else {
                    list = new ArrayList<String>();
                    list.add(dakList.get(i).getInwardNumber());
                    finalMap.put(employeeName, list);
                }
            }
        }

        for (Entry<String, ArrayList<String>> entry : finalMap.entrySet()) {
            MarkerAssignedLetter emp1 = new MarkerAssignedLetter();
            String empName = entry.getKey();
            emp1.setEmployeeName(empName);

            ArrayList<String> arrayList = entry.getValue();

            String citiesCommaSeparated = String.join(", ", arrayList);
            emp1.setInwardNumberList(citiesCommaSeparated);

            empListByDate.add(emp1);
        }
        /*
         * System.out.println("Key = " + + ", Value = " + entry.getValue());
         */

        // emp1.setInwardNumberList(commaSeparatedString);

        return empListByDate;
    }

    /**
     * Get handed over Letters report of Employee for Marker.
     *
     * @return Html string.
     */

    //Service for get dashboard count
    public Page<DashboardCount> getDashboardCount(DakMasterCriteria criteria, Pageable page) {
        // TODO Auto-generated method stub
        List<DashboardCount> dashboardCountList = new ArrayList<DashboardCount>();
        InstantFilter receivedDate = new InstantFilter();

        DashboardCount dashboardCountObj = new DashboardCount();
        LongFilter orgId = criteria.getOrganizationId();

        List<DakStatus> status = new ArrayList<DakStatus>();
        //For Recently added
        Calendar todayDate = Calendar.getInstance();
        todayDate.add(Calendar.HOUR_OF_DAY, 00);
        todayDate.add(Calendar.MINUTE, 59);
        todayDate.add(Calendar.SECOND, 59);
        todayDate.add(Calendar.MILLISECOND, 999);
        Date today = todayDate.getTime();

        Calendar last7thDay = Calendar.getInstance();
        last7thDay.add(Calendar.DAY_OF_MONTH, -7);
        last7thDay.set(Calendar.MILLISECOND, 0);
        last7thDay.set(Calendar.SECOND, 0);
        last7thDay.set(Calendar.MINUTE, 0);
        last7thDay.set(Calendar.HOUR_OF_DAY, 0);
        Date lastsevenDays = last7thDay.getTime();

        receivedDate.setLessThanOrEqual(today.toInstant());
        receivedDate.setGreaterThanOrEqual(lastsevenDays.toInstant());
        criteria.setLetterReceivedDate(receivedDate);
        DakStatusFilter dakStatusFilter = new DakStatusFilter();
        status.add(DakStatus.CREATED);
        status.add(DakStatus.UPDATED);
        dakStatusFilter.setIn(status);
        dakStatusFilter.setNotIn(null);

        criteria.setCurrentStatus(dakStatusFilter);

        Long recentlyAdded = dakMasterQueryService.countByCriteria(criteria);

        //For Pending
        DakMasterCriteria criteria1 = new DakMasterCriteria();
        criteria1.setOrganizationId(orgId);
        DakStatusFilter dakStatusFilter1 = new DakStatusFilter();
        dakStatusFilter1.setEquals(DakStatus.ASSIGNED);
        criteria1.setCurrentStatus(dakStatusFilter1);

        Long pending = dakMasterQueryService.countByCriteria(criteria1);
        dashboardCountObj.setPending(pending);

        //For awaited
        DakMasterCriteria criteria2 = new DakMasterCriteria();
        criteria1.setOrganizationId(orgId);
        DakStatusFilter dakStatusFilter2 = new DakStatusFilter();
        dakStatusFilter2.setEquals(DakStatus.AWAITED);
        criteria2.setCurrentStatus(dakStatusFilter2);

        Long awaited = dakMasterQueryService.countByCriteria(criteria2);
        dashboardCountObj.setAwaiting(awaited);

        //For cleared
        DakMasterCriteria criteria3 = new DakMasterCriteria();
        criteria3.setOrganizationId(orgId);
        DakStatusFilter dakStatusFilter3 = new DakStatusFilter();
        dakStatusFilter3.setEquals(DakStatus.CLEARED);
        criteria3.setCurrentStatus(dakStatusFilter3);

        Long cleared = dakMasterQueryService.countByCriteria(criteria3);
        dashboardCountObj.setCleared(cleared);

        //For hearing
        DakMasterCriteria criteria4 = new DakMasterCriteria();
        criteria4.setOrganizationId(orgId);
        List<DakStatus> status1 = new ArrayList<DakStatus>();

        DakStatusFilter dakStatusFilter4 = new DakStatusFilter();
        status1.add(DakStatus.HEARING);
        status1.add(DakStatus.AWAITED_FOR_ORDER);
        dakStatusFilter4.setIn(status1);
        criteria4.setCurrentStatus(dakStatusFilter4);

        Long hearing = dakMasterQueryService.countByCriteria(criteria4);
        dashboardCountObj.setHearing(hearing);

        //For Todays hearing
        DakMasterCriteria dakMasterCriteria6 = new DakMasterCriteria();
        DakStatusFilter dakStatusFilter5 = new DakStatusFilter();
        dakStatusFilter5.setEquals(DakStatus.HEARING);
        dakMasterCriteria6.setCurrentStatus(dakStatusFilter5);
        dakMasterCriteria6.setOrganizationId(orgId);
        Page<DakMasterDTO> dakPage1 = dakMasterQueryService.findByCriteria(dakMasterCriteria6, page);

        List<Long> dakIdlist = new ArrayList<Long>();
        for (DakMasterDTO dakMasterDTO : dakPage1.getContent()) {
            HearingDetailsCriteria hearingDetailsCriteria = new HearingDetailsCriteria();

            com.techvg.eoffice.service.criteria.HearingDetailsCriteria.DakStatusFilter dakStatusFilter10 = new HearingDetailsCriteria.DakStatusFilter();
            dakStatusFilter10.setEquals(DakStatus.HEARING);
            hearingDetailsCriteria.setStatus(dakStatusFilter10);
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
            hearingDetailsCriteria.setDate(dateFilter);

            Page<HearingDetailsDTO> hearingDetailsDTOPage = hearingDetailsQueryService.findByCriteria(hearingDetailsCriteria, page);
            dashboardCountObj.setTodaysHearing(hearingDetailsDTOPage.getContent().size());
        }

        // For Total Pending
        Long totalPending = recentlyAdded + pending + awaited + hearing;
        dashboardCountObj.setTotalPending(totalPending);
        dashboardCountList.add(dashboardCountObj);

        final int start = (int) page.getOffset();
        final int end = Math.min((start + page.getPageSize()), dashboardCountList.size());
        final Page<DashboardCount> pageList = new PageImpl<>(dashboardCountList.subList(start, end), page, dashboardCountList.size());

        return pageList;
    }

    public String getHandedOverReport(Instant date, Set<MarkerAssignedLetter> assignedLetters, String orgName) {
        StringBuilder builder = new StringBuilder();

        Iterator<MarkerAssignedLetter> iterator = assignedLetters.iterator();

        int count = 1;
        while (iterator.hasNext()) {
            MarkerAssignedLetter markerAssignedLetter = iterator.next();

            builder.append(
                "<tr>\r\n" +
                "    <td>" +
                count +
                "</td>\r\n" +
                "    <td>" +
                markerAssignedLetter.getEmployeeName() +
                "</td>\r\n" +
                "    <td>" +
                markerAssignedLetter.getInwardNumberList() +
                "</td>\r\n" +
                "    <td style=\"width:180px\"></td>\r\n" +
                "  </tr>"
            );
            count++;
        }

        String html =
            "<!DOCTYPE html>\r\n" +
            "<html>\r\n" +
            "<head>\r\n" +
            "<meta charset=\"UTF-8\">\r\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\">\r\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n" +
            "</head>\r\n" +
            "<body>\r\n" +
            "<h3 style=\"text-align: center\">" +
            orgName +
            "</h3>\r\n" +
            "<h5 style=\"text-align: center\">पत्रव्यवहार - सुपुर्द अहवाल</h5>\r\n" +
            "<style>\r\n" +
            "@page {\r\n" +
            "   size: landscape; \r\n" +
            " }\r\n" +
            "table {\r\n" +
            "	 font-family: arial, sans-serif;\r\n" +
            "	 border-collapse: collapse;\r\n" +
            "	 width: 100%;\r\n" +
            "      }\r\n" +
            "	th {\r\n" +
            "		background-color:#f2f2f2; \r\n" +
            "		border: 1px solid #dddddd;\r\n" +
            "		text-align: center;\r\n" +
            "		padding: 8px;\r\n" +
            "		}\r\n" +
            "		td {\r\n" +
            "		border: 1px solid #dddddd; \r\n" +
            "		text-align: left; \r\n" +
            "		padding: 8px; \r\n" +
            "		}\r\n" +
            "				\r\n" +
            "		</style>\r\n" +
            "				\r\n" +
            "<table>\r\n" +
            "\r\n" +
            "  <tr>\r\n" +
            "    <th colspan=4>दिनांक: " +
            convertInstantToString(date) +
            "</th>\r\n" +
            "    \r\n" +
            "  </tr>\r\n" +
            "  <tr>\r\n" +
            "    <th>अ.क्र.</th>\r\n" +
            "    <th>कर्मचाऱ्याचे नाव</th>\r\n" +
            "    <th>आवक क्र. नुसार सुपुर्द केलेला पत्रव्यवहार</th>\r\n" +
            "    <th>स्वाक्षरी</th>\r\n" +
            "  </tr>\r\n" +
            "<tr>" +
            builder.toString() +
            "</tr>" +
            "</table>\r\n" +
            "\r\n" +
            "</body>\r\n" +
            "</html>\r\n" +
            "";

        return html;
    }

    /**
     * Transfer a dakMaster to other employee.
     *
     * @param dakMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public DakMasterDTO transferLetter(DakMasterDTO dakMasterDTO) {
        log.debug("Request to save DakMaster : {}", dakMasterDTO);

        Long dakAssignedfromId = dakMasterDTO.getDakAssignedFrom().getId();
        Long dakAssignee = dakMasterDTO.getDakAssignee().getId();
        Long dakId = dakMasterDTO.getId();
        String updatedStatus = dakMasterDTO.getCurrentStatus().toString();
        dakMasterRepository.transferLetter(dakAssignedfromId, dakAssignee, updatedStatus, dakId);

        dakMasterDTO.setUpdatedBy(dakMasterDTO.getDakAssignedFrom().getFirstName());
        dakMasterDTO.setUpdatedOn(Instant.now());

        // ------------Save comment for transfer letter -----
        if (dakMasterDTO.getComment() != null) {
            CommentMasterDTO commentMasterDTO = new CommentMasterDTO();
            commentMasterDTO.setDescription(dakMasterDTO.getComment());
            commentMasterDTO.setDakMaster(dakMasterDTO);
            commentMasterDTO.setSecurityUser(dakMasterDTO.getDakAssignedFrom());
            commentMasterDTO.setStatus(true);
            commentMasterDTO.setCreatedOn(Instant.now());
            commentMasterDTO.setCreatedBy(dakMasterDTO.getDakAssignedFrom().getFirstName());
            commentMasterDTO.setLastModified(Instant.now());

            commentMasterService.save(commentMasterDTO);
        }
        DakMaster dakMaster = dakMasterMapper.toEntity(dakMasterDTO);

        return dakMasterMapper.toDto(dakMaster);
    }
}
