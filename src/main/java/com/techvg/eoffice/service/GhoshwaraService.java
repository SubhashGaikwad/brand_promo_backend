package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.Ghoshwara;
import com.techvg.eoffice.domain.enumeration.RegisterType;
import com.techvg.eoffice.repository.GhoshwaraRepository;
import com.techvg.eoffice.service.criteria.GhoshwaraCriteria;
import com.techvg.eoffice.service.dto.GhoshwaraDTO;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import com.techvg.eoffice.service.mapper.GhoshwaraMapper;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ghoshwara}.
 */
@Service
@Transactional
public class GhoshwaraService {

    private final Logger log = LoggerFactory.getLogger(GhoshwaraService.class);

    private final GhoshwaraRepository ghoshwaraRepository;

    private final GhoshwaraMapper ghoshwaraMapper;

    @Autowired
    private DakMasterService dakMasterService;

    public GhoshwaraService(GhoshwaraRepository ghoshwaraRepository, GhoshwaraMapper ghoshwaraMapper) {
        this.ghoshwaraRepository = ghoshwaraRepository;
        this.ghoshwaraMapper = ghoshwaraMapper;
    }

    /**
     * Save a ghoshwara.
     *
     * @param ghoshwaraDTO the entity to save.
     * @return the persisted entity.
     */
    public GhoshwaraDTO save(GhoshwaraDTO ghoshwaraDTO) {
        log.debug("Request to save Ghoshwara : {}", ghoshwaraDTO);
        Ghoshwara ghoshwara = ghoshwaraMapper.toEntity(ghoshwaraDTO);
        ghoshwara = ghoshwaraRepository.save(ghoshwara);
        return ghoshwaraMapper.toDto(ghoshwara);
    }

    /**
     * Update a ghoshwara.
     *
     * @param ghoshwaraDTO the entity to save.
     * @return the persisted entity.
     */
    public GhoshwaraDTO update(GhoshwaraDTO ghoshwaraDTO) {
        log.debug("Request to save Ghoshwara : {}", ghoshwaraDTO);
        Ghoshwara ghoshwara = ghoshwaraMapper.toEntity(ghoshwaraDTO);
        ghoshwara = ghoshwaraRepository.save(ghoshwara);
        return ghoshwaraMapper.toDto(ghoshwara);
    }

    /**
     * Partially update a ghoshwara.
     *
     * @param ghoshwaraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GhoshwaraDTO> partialUpdate(GhoshwaraDTO ghoshwaraDTO) {
        log.debug("Request to partially update Ghoshwara : {}", ghoshwaraDTO);

        return ghoshwaraRepository
            .findById(ghoshwaraDTO.getId())
            .map(existingGhoshwara -> {
                ghoshwaraMapper.partialUpdate(existingGhoshwara, ghoshwaraDTO);

                return existingGhoshwara;
            })
            .map(ghoshwaraRepository::save)
            .map(ghoshwaraMapper::toDto);
    }

    /**
     * Get all the ghoshwaras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GhoshwaraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ghoshwaras");
        return ghoshwaraRepository.findAll(pageable).map(ghoshwaraMapper::toDto);
    }

    /**
     * Get all the ghoshwaras with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GhoshwaraDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ghoshwaraRepository.findAllWithEagerRelationships(pageable).map(ghoshwaraMapper::toDto);
    }

    /**
     * Get one ghoshwara by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GhoshwaraDTO> findOne(Long id) {
        log.debug("Request to get Ghoshwara : {}", id);
        return ghoshwaraRepository.findOneWithEagerRelationships(id).map(ghoshwaraMapper::toDto);
    }

    /**
     * Delete the ghoshwara by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ghoshwara : {}", id);
        ghoshwaraRepository.deleteById(id);
    }

    public String getGoshwaraHtmlReport(
        GhoshwaraCriteria criteria,
        List<GhoshwaraDTO> ghoshwaraList,
        String orgName,
        SecurityUserDTO user
    ) {
        StringBuilder builder = new StringBuilder();

        //--------Calculate LastSunday of this week-----
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK, -(cal.get(Calendar.DAY_OF_WEEK) - 1));
        Date lastSunday = cal.getTime();
        Instant lastSundayDate = lastSunday.toInstant();

        //--------Calculate Saturday of this week-----

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Date thisWeekSaturday = c.getTime();
        Instant saturdayDate = thisWeekSaturday.toInstant();

        Set<GhoshwaraDTO> ghoshwaraSet1 = new HashSet<GhoshwaraDTO>();
        Set<GhoshwaraDTO> ghoshwaraSet2 = new HashSet<GhoshwaraDTO>();

        for (GhoshwaraDTO ghoshwaraDTO : ghoshwaraList) {
            if ("WORK_DESCRIPTION".equalsIgnoreCase(ghoshwaraDTO.getRegisterType().toString())) {
                ghoshwaraSet1.add(ghoshwaraDTO);
            } else if ("AWAITED_REGISTER".equalsIgnoreCase(ghoshwaraDTO.getRegisterType().toString())) {
                ghoshwaraSet2.add(ghoshwaraDTO);
            }
        }

        GhoshwaraDTO workDesObj = ghoshwaraSet1.iterator().next();
        GhoshwaraDTO awaitRegObj = ghoshwaraSet2.iterator().next();

        SecurityUserDTO employee = workDesObj.getSecurityUser();
        String empName = user.getFirstName() + "" + user.getLastName();

        long totalInitialPending = workDesObj.getInitialPendings() + awaitRegObj.getInitialPendings();
        long totalDailyPendings = workDesObj.getDailyPendings() + awaitRegObj.getDailyPendings();
        long totalCurrentWeekInward = workDesObj.getCurrentWeekInwards() + awaitRegObj.getCurrentWeekInwards();
        long totalDescTotal = workDesObj.getTotal() + awaitRegObj.getTotal();
        long totalSelfGenrated = workDesObj.getSelfGenerated() + awaitRegObj.getSelfGenerated();
        long tatalCurrentWeekCleared = workDesObj.getCurrentWeekCleared() + awaitRegObj.getCurrentWeekCleared();
        long totalWeeklyPending = workDesObj.getCurrentWeekPendings() + awaitRegObj.getCurrentWeekPendings();
        long totalOneWeek = workDesObj.getFirstWeek() + awaitRegObj.getFirstWeek();
        long totalSecondWeek = workDesObj.getSecondWeek() + awaitRegObj.getSecondWeek();
        long totalThirdWeek = workDesObj.getThirdWeek() + awaitRegObj.getThirdWeek();
        long totalOneMonth = workDesObj.getFirstMonth() + awaitRegObj.getFirstMonth();
        long totalSecondMonth = workDesObj.getSecondMonth() + awaitRegObj.getSecondMonth();
        long totatlThirdMonth = workDesObj.getThirdMonth() + awaitRegObj.getThirdMonth();
        long totalSixMonth = workDesObj.getWithinSixMonths() + awaitRegObj.getWithinSixMonths();
        long totalAboveSixMonth = workDesObj.getAboveSixMonths() + awaitRegObj.getAboveSixMonths();
        long totalOneYear = workDesObj.getAboveOneYear() + awaitRegObj.getAboveOneYear();

        builder.append(
            "</head>\n" +
            "<body>\n" +
            "<h2 style=\"text-align: center\">" +
            orgName +
            "</h2>\n" +
            "<h4 style=\"text-align: center\">गोषवारा</h4>\n" +
            "<style>\n" +
            "@page {\n" +
            "   size: landscape; \n" +
            " }\n" +
            "table {\n" +
            "	 font-family: arial, sans-serif;\n" +
            "	 border-collapse: collapse;\n" +
            "	 width: 100%;\n" +
            "      }\n" +
            "	th {\n" +
            "		background-color:#f2f2f2; \n" +
            "		border: 2px solid #dddddd;\n" +
            "		text-align: center;\n" +
            "		padding: 5px;\n" +
            "        font-size: 13px\n" +
            "		}\n" +
            "	td {\n" +
            "		border: 2px solid #dddddd; \n" +
            "		text-align: center; \n" +
            "		padding: 5px; \n" +
            "        font-size: 13px\n" +
            "		}	\n" +
            "				\n" +
            "		</style>\n" +
            "				\n" +
            "<table>\n" +
            "  <tr>\n" +
            "    <th colspan=19>दिनांक :" +
            dakMasterService.convertInstantToString(lastSundayDate) +
            "  ते " +
            dakMasterService.convertInstantToString(saturdayDate) +
            "</th>\n" +
            "    \n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <th rowspan=2>अ.क्र.</th>\n" +
            "    <th rowspan=2>कर्मचाऱ्याचे नाव</th> \n" +
            "    <th rowspan=2>नोंदवहीचा प्रकार</th>\n" +
            "    <th colspan=\"2\">आरंभी शिल्लक</th>\n" +
            "    <th rowspan=2>चालू आठवड्यातील आवक</th>\n" +
            "    <th rowspan=2>एकुण</th>\n" +
            "    <th rowspan=2>स्वयं निर्मित</th>\n" +
            "    <th rowspan=2>चालू आठवड्यातील निर्गती</th>\n" +
            "     <th rowspan=2>चालू आठवडा प्रलंबित</th>\n" +
            "    <th colspan=\"9\">शिल्लक संदर्भाची वर्गवारी</th>\n" +
            "    \n" +
            "    </tr>\n" +
            "  \n" +
            "  \n" +
            "  <tr>\n" +
            "  <th>आठवडा अखेर शिल्लक</th>\n" +
            "  <th>दैनिक प्रलंबित</th>\n" +
            "    <th>१ आठवड्यातील</th>\n" +
            "    <th>२ आठवड्यातील</th>\n" +
            "    <th>३ आठवड्यातील</th>\n" +
            "    <th>१ महिन्यातील</th>\n" +
            "    <th>२ महिन्यातील</th>\n" +
            "    <th>३ महिन्यातील</th>\n" +
            "    <th>६ महिन्यांचे आतील</th>\n" +
            "    <th>६ महिन्यांचे वरील</th>\n" +
            "    <th>१ वर्षावरील</th>\n" +
            "  </tr>+" +
            "    \r\n" +
            "  </tr>\r\n" +
            " <tr>" +
            "<td rowspan=3>1</td>" +
            "<td rowspan=3>" +
            empName +
            "</td>" +
            "<td>कार्यविवरण</td>" +
            "<td>" +
            workDesObj.getInitialPendings() +
            "</td>" +
            "<td>" +
            workDesObj.getDailyPendings() +
            "</td>" +
            "<td>" +
            workDesObj.getCurrentWeekInwards() +
            "</td>" +
            "<td>" +
            workDesObj.getTotal() +
            "</td>" +
            "<td>" +
            workDesObj.getSelfGenerated() +
            "</td>" +
            "<td>" +
            workDesObj.getCurrentWeekCleared() +
            "</td>" +
            "<td>" +
            workDesObj.getCurrentWeekPendings() +
            "</td>" +
            "<td>" +
            workDesObj.getFirstWeek() +
            "</td>" +
            "<td>" +
            workDesObj.getSecondWeek() +
            "</td>" +
            "<td>" +
            workDesObj.getThirdWeek() +
            "</td>" +
            "<td>" +
            workDesObj.getFirstMonth() +
            "</td>" +
            "<td>" +
            workDesObj.getSecondMonth() +
            "</td>" +
            "<td>" +
            workDesObj.getThirdMonth() +
            "</td>" +
            "<td>" +
            workDesObj.getWithinSixMonths() +
            "</td>" +
            "<td>" +
            workDesObj.getAboveSixMonths() +
            "</td>" +
            "<td>" +
            workDesObj.getAboveOneYear() +
            "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>प्रतिक्षाधीन नोंदवही</td>" +
            "<td>" +
            awaitRegObj.getInitialPendings() +
            "<td>" +
            awaitRegObj.getDailyPendings() +
            "</td>" +
            "</td>" +
            "<td>" +
            awaitRegObj.getCurrentWeekInwards() +
            "</td>" +
            "<td>" +
            awaitRegObj.getTotal() +
            "</td>" +
            "<td>" +
            awaitRegObj.getSelfGenerated() +
            "</td>" +
            "<td>" +
            awaitRegObj.getCurrentWeekCleared() +
            "</td>" +
            "<td>" +
            awaitRegObj.getCurrentWeekPendings() +
            "</td>" +
            "<td>" +
            awaitRegObj.getFirstWeek() +
            "</td>" +
            "<td>" +
            awaitRegObj.getSecondWeek() +
            "</td>" +
            "<td>" +
            awaitRegObj.getThirdWeek() +
            "</td>" +
            "<td>" +
            awaitRegObj.getFirstMonth() +
            "</td>" +
            "<td>" +
            awaitRegObj.getSecondMonth() +
            "</td>" +
            "<td>" +
            awaitRegObj.getThirdMonth() +
            "</td>" +
            "<td>" +
            awaitRegObj.getWithinSixMonths() +
            "</td>" +
            "<td>" +
            awaitRegObj.getAboveSixMonths() +
            "</td>" +
            "<td>" +
            awaitRegObj.getAboveOneYear() +
            "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>एकुण</td>" +
            "<td>" +
            totalInitialPending +
            "</td>" +
            "<td>" +
            totalDailyPendings +
            "</td>" +
            "<td>" +
            totalCurrentWeekInward +
            "</td>" +
            "<td>" +
            totalDescTotal +
            "</td>" +
            " <td>" +
            totalSelfGenrated +
            "</td>" +
            "<td>" +
            tatalCurrentWeekCleared +
            "</td>" +
            "<td>" +
            totalWeeklyPending +
            "</td>" +
            "<td>" +
            totalOneWeek +
            "</td>" +
            "<td>" +
            totalSecondWeek +
            "</td>" +
            "<td>" +
            totalThirdWeek +
            "</td>" +
            "<td>" +
            totalOneMonth +
            "</td>" +
            "<td>" +
            totalSecondMonth +
            "</td>" +
            "<td>" +
            totatlThirdMonth +
            "</td>" +
            "<td>" +
            totalSixMonth +
            "</td>" +
            "<td>" +
            totalAboveSixMonth +
            "</td>" +
            "<td>" +
            totalOneYear +
            "</td>" +
            "</td>" +
            " </tr>"
        );

        String html = builder.toString();
        return html;
    }
}
