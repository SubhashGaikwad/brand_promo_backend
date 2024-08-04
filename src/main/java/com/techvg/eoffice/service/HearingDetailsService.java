package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.HearingDetails;
import com.techvg.eoffice.domain.enumeration.DakStatus;
import com.techvg.eoffice.repository.HearingDetailsRepository;
import com.techvg.eoffice.service.criteria.DakMasterCriteria;
import com.techvg.eoffice.service.dto.DakMasterDTO;
import com.techvg.eoffice.service.dto.HearingDetailsDTO;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import com.techvg.eoffice.service.mapper.HearingDetailsMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HearingDetails}.
 */
@Service
@Transactional
public class HearingDetailsService {

    private final Logger log = LoggerFactory.getLogger(HearingDetailsService.class);

    private final HearingDetailsRepository hearingDetailsRepository;

    private final HearingDetailsMapper hearingDetailsMapper;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private DakMasterService dakMasterService;

    public HearingDetailsService(HearingDetailsRepository hearingDetailsRepository, HearingDetailsMapper hearingDetailsMapper) {
        this.hearingDetailsRepository = hearingDetailsRepository;
        this.hearingDetailsMapper = hearingDetailsMapper;
    }

    /**
     * Save a hearingDetails.
     *
     * @param hearingDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public HearingDetailsDTO save(HearingDetailsDTO hearingDetailsDTO) {
        log.debug("Request to save HearingDetails : {}", hearingDetailsDTO);
        HearingDetails hearingDetails = hearingDetailsMapper.toEntity(hearingDetailsDTO);
        hearingDetails = hearingDetailsRepository.save(hearingDetails);
        return hearingDetailsMapper.toDto(hearingDetails);
    }

    /**
     * Update a hearingDetails.
     *
     * @param hearingDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public HearingDetailsDTO update(HearingDetailsDTO hearingDetailsDTO) {
        log.debug("Request to save HearingDetails : {}", hearingDetailsDTO);
        HearingDetails hearingDetails = hearingDetailsMapper.toEntity(hearingDetailsDTO);
        hearingDetails = hearingDetailsRepository.save(hearingDetails);
        return hearingDetailsMapper.toDto(hearingDetails);
    }

    /**
     * Partially update a hearingDetails.
     *
     * @param hearingDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HearingDetailsDTO> partialUpdate(HearingDetailsDTO hearingDetailsDTO) {
        log.debug("Request to partially update HearingDetails : {}", hearingDetailsDTO);

        return hearingDetailsRepository
            .findById(hearingDetailsDTO.getId())
            .map(existingHearingDetails -> {
                hearingDetailsMapper.partialUpdate(existingHearingDetails, hearingDetailsDTO);

                return existingHearingDetails;
            })
            .map(hearingDetailsRepository::save)
            .map(hearingDetailsMapper::toDto);
    }

    /**
     * Get all the hearingDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HearingDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HearingDetails");
        return hearingDetailsRepository.findAll(pageable).map(hearingDetailsMapper::toDto);
    }

    /**
     * Get all the hearingDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<HearingDetailsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return hearingDetailsRepository.findAllWithEagerRelationships(pageable).map(hearingDetailsMapper::toDto);
    }

    /**
     * Get one hearingDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HearingDetailsDTO> findOne(Long id) {
        log.debug("Request to get HearingDetails : {}", id);
        return hearingDetailsRepository.findOneWithEagerRelationships(id).map(hearingDetailsMapper::toDto);
    }

    /**
     * Delete the hearingDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HearingDetails : {}", id);
        hearingDetailsRepository.deleteById(id);
    }

    /**
     * Get Today's Hearing report HTML with data.
     *
     * @param Dak List , Organization Name and hearing DTO.
     * @return html in String.
     */
    /*public String getTodaysHearingReport(String orgName, List<HearingDetailsDTO> hearingList) {
     */
    public String getTodaysHearingReport(String orgName, List<HearingDetailsDTO> hearingList, DakMasterCriteria dakCriteria) {
        StringBuilder data = new StringBuilder();

        int count = 1;

        for (HearingDetailsDTO hearingDTO : hearingList) {
            Long dakId = hearingDTO.getDakMasterId();
            Optional<DakMasterDTO> dakMasterDTO = dakMasterService.findOne(dakId);

            if (
                dakMasterDTO.get().getCurrentStatus() == DakStatus.HEARING &&
                dakCriteria.getOrganizationId().getEquals() == dakMasterDTO.get().getOrganization().getId()
            ) {
                data.append(
                    " <tr>" +
                    "	   <td>" +
                    count +
                    "</td>" +
                    "	   <td>" +
                    dakMasterDTO.get().getInwardNumber() +
                    "</td>" +
                    "	   <td>" +
                    hearingDTO.getTime() +
                    "</td>" +
                    "	   <td>" +
                    dakMasterDTO.get().getSubject() +
                    "</td>" +
                    "	   <td>" +
                    dakMasterDTO.get().getDakAssignee().getFirstName() + // --------TODO here for assignee name after
                    // mapping in dak
                    // master table
                    "</td>" +
                    "	 </tr>"
                );
                count++;
            }
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
            "<h2 style=\"text-align: center\">" +
            orgName +
            "</h2>\r\n" +
            "<h4 style=\"text-align: center\">आजच्या सुनावण्या </h4>\r\n" +
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
            "		border: 2px solid #dddddd;\r\n" +
            "		text-align: center;\r\n" +
            "		padding: 5px;\r\n" +
            "        font-size: 13px\r\n" +
            "		}\r\n" +
            "	td {\r\n" +
            "		border: 2px solid #dddddd; \r\n" +
            "		text-align: center; \r\n" +
            "		padding: 5px; \r\n" +
            "        font-size: 13px\r\n" +
            "		}	\r\n" +
            "				\r\n" +
            "		</style>\r\n" +
            "				\r\n" +
            "<table>\r\n" +
            "  <tr>\r\n" +
            "    <th>अ.क्र.</th>\r\n" +
            "    <th>आवक क्रमांक</th> \r\n" +
            "    <th>वेळ</th>\r\n" +
            "    <th>विषय</th>\r\n" +
            "    <th>जबाबदारी</th>\r\n" +
            "   </tr>\r\n" +
            "  <tr>\r\n" +
            data.toString() +
            "  </tr>\r\n" +
            "  \r\n" +
            "</table>\r\n" +
            "\r\n" +
            "</body>\r\n" +
            "</html>\r\n" +
            "\r\n" +
            "";

        return html;
    }
}
