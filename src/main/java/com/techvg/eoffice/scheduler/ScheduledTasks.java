package com.techvg.eoffice.scheduler;

import com.techvg.eoffice.domain.FetchDakRecordRequest;
import com.techvg.eoffice.domain.enumeration.DakStatus;
import com.techvg.eoffice.domain.enumeration.LetterType;
import com.techvg.eoffice.domain.enumeration.RegisterType;
import com.techvg.eoffice.repository.OrganizationRepository;
import com.techvg.eoffice.service.DakMasterQueryService;
import com.techvg.eoffice.service.GhoshwaraQueryService;
import com.techvg.eoffice.service.GhoshwaraService;
import com.techvg.eoffice.service.OrganizationQueryService;
import com.techvg.eoffice.service.SecurityRoleQueryService;
import com.techvg.eoffice.service.SecurityUserQueryService;
import com.techvg.eoffice.service.criteria.DakMasterCriteria;
import com.techvg.eoffice.service.criteria.DakMasterCriteria.LetterTypeFilter;
import com.techvg.eoffice.service.criteria.GhoshwaraCriteria;
import com.techvg.eoffice.service.criteria.OrganizationCriteria;
import com.techvg.eoffice.service.criteria.SecurityRoleCriteria;
import com.techvg.eoffice.service.criteria.SecurityUserCriteria;
import com.techvg.eoffice.service.dto.GhoshwaraDTO;
import com.techvg.eoffice.service.dto.OrganizationDTO;
import com.techvg.eoffice.service.dto.SecurityRoleDTO;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private OrganizationQueryService organizationQueryService;

    @Autowired
    private SecurityUserQueryService securityUserQueryService;

    @Autowired
    private SecurityRoleQueryService securityRoleQueryService;

    @Autowired
    private DakMasterQueryService dakMasterQueryService;

    @Autowired
    private GhoshwaraService ghoshwaraService;

    @Autowired
    private GhoshwaraQueryService ghoshwaraQueryService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Value("${shouldFetchDataFromDak}")
    private boolean shouldFetchDataFromDak;

    @Value("${shouldDeleteFromGhoshwara}")
    private boolean shouldDeleteFromGhoshwara;

    @Value("${shouldResetNextVal}")
    private boolean shouldResetNextVal;

    /**
     * {@Scheduled : scheduled task for today's mid night} This method calculate
     * previous records in ghoshwara
     */
    // Current time run after every 10 min
    //  @Scheduled(initialDelay = 1000, fixedRate = 99000)

    @Scheduled(cron = "${cron.expression}")
    public void reportCurrentTime() {
        log.info("Goshwara Schedular getup time is now {}", dateFormat.format(new Date()));

        try {
            log.info("shouldFetchDataFromDak =" + shouldFetchDataFromDak);

            if (shouldFetchDataFromDak) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

                String start_date = sdf.format(yesterday) + " 00:00:00";
                String end_date = sdf.format(yesterday) + " 11:59:59";

                log.info("after date");
                FetchDakRecordRequest fetchRequest = new FetchDakRecordRequest();
                fetchRequest.setStart_date(start_date);
                fetchRequest.setEnd_date(end_date);

                log.info("before  getEmployeeList");
                this.getEmployeeList(fetchRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GET : Get employee list of all organization
     */
    private void getEmployeeList(FetchDakRecordRequest fetchRequest) {
        List<SecurityUserDTO> employeeList = new ArrayList<SecurityUserDTO>();
        List<SecurityUserDTO> empList;

        OrganizationCriteria orgCriteria = new OrganizationCriteria();
        BooleanFilter activeOrg = new BooleanFilter();
        activeOrg.setEquals(true);
        orgCriteria.setIsActivate(activeOrg);

        List<OrganizationDTO> orgList = organizationQueryService.findByCriteria(orgCriteria);
        log.info("orgList size" + orgList.size());
        for (OrganizationDTO organization : orgList) {
            if (organization.getId() != null) {
                SecurityRoleCriteria roleCri = new SecurityRoleCriteria();
                StringFilter empFilter = new StringFilter();
                empFilter.setContains("ROLE_EMP");
                roleCri.setRoleName(empFilter);
                List<SecurityRoleDTO> roleList = securityRoleQueryService.findByCriteria(roleCri);

                SecurityUserCriteria userCri = new SecurityUserCriteria();
                LongFilter orgId = new LongFilter();
                orgId.setEquals(organization.getId());
                userCri.setOrganizationId(orgId);

                LongFilter roleId = new LongFilter();
                roleId.setEquals(roleList.get(0).getId());
                userCri.setSecurityRoleId(roleId);

                empList = securityUserQueryService.findByCriteria(userCri);

                log.info("before saveCountInGoshwara");
                this.saveCountInGoshwara(empList, fetchRequest);
            }
        }
    }

    /**
     * SAVE : Method to save Counts in goshwara table with date calculation
     */

    private void saveCountInGoshwara(List<SecurityUserDTO> empList, FetchDakRecordRequest fetchRequest) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US); // Specify locale to
        log.info("IN saveCountInGoshwara before  geting ghoshwara Count");

        LocalDateTime startldt = LocalDateTime.parse(fetchRequest.getStart_date(), f);
        LocalDateTime endldt = LocalDateTime.parse(fetchRequest.getEnd_date(), f);
        ZoneId z = ZoneId.of("America/Toronto");
        ZonedDateTime szdt = startldt.atZone(z);
        ZonedDateTime ezdt = endldt.atZone(z);
        System.out.println(
            "##################################Day of the month:" +
            ezdt.getDayOfMonth() +
            "Hour:" +
            ezdt.getHour() +
            "minutes:" +
            ezdt.getMinute() +
            ezdt.getHour() +
            "second:" +
            ezdt.getSecond()
        );

        /*  ZonedDateTime ldtZoned = startldt.atZone(ZoneId.systemDefault());  
        // get UTC ZonedDateTime  
        ZonedDateTime szdt = ldtZoned.withZoneSameInstant(ZoneId.of("UTC")); 
        
        ZonedDateTime ldtZoned2 = endldt.atZone(ZoneId.systemDefault());  
        // get UTC ZonedDateTime  
        ZonedDateTime ezdt = ldtZoned2.withZoneSameInstant(ZoneId.of("UTC"));
        */

        Instant startDate = szdt.toInstant();
        Instant endDate = ezdt.toInstant();

        // ----------Calculate last sunday-----------

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DAY_OF_WEEK, -(cal.get(Calendar.DAY_OF_WEEK) - 1));
        Date lastSunday = cal.getTime();
        Instant lastSundayDate = lastSunday.toInstant();

        // --------Calculate Saturday of this week-----

        Calendar c = Calendar.getInstance();
        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Date thisWeekSaturday = c.getTime();
        Instant saturdayDate = thisWeekSaturday.toInstant();

        LetterTypeFilter typeFilter = new LetterTypeFilter();
        typeFilter.setEquals(LetterType.INWARD);

        com.techvg.eoffice.service.criteria.DakMasterCriteria.DakStatusFilter statusFilter = new com.techvg.eoffice.service.criteria.DakMasterCriteria.DakStatusFilter();
        List<DakStatus> status = new ArrayList<DakStatus>();

        InstantFilter dateFilter = new InstantFilter();

        log.info("before  geting ghoshwara Count");
        for (SecurityUserDTO securityUserObj : empList) {
            DakMasterCriteria dakCriteria = new DakMasterCriteria();

            LongFilter userId = new LongFilter();
            userId.setEquals(securityUserObj.getId());
            dakCriteria.setDakAssignee(userId);
            LongFilter orgId = new LongFilter();
            orgId.setEquals(securityUserObj.getOrganization().getId());
            dakCriteria.setOrganizationId(orgId);
            dakCriteria.setLetterType(typeFilter);

            // ----------------- for Intial pending----------------

            // ----------WORK_DESCRIPTION-----
            dateFilter.setLessThanOrEqual(lastSundayDate);
            dateFilter.setGreaterThanOrEqual(null);
            dateFilter.setLessThan(null);
            //status.clear(); written by Aarohi
            status.clear();
            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(status);
            statusFilter.setIn(null);

            dakCriteria.setCurrentStatus(statusFilter);
            dakCriteria.setLetterReceivedDate(dateFilter);
            long initalPendings = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----
            status.clear();
            //status.add(DakStatus.AWAITED_FOR_ORDER);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(null);
            statusFilter.setIn(status);
            dakCriteria.setCurrentStatus(statusFilter);

            long initalPendingsAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------------- for current week inwards------------------

            // ----------WORK_DESCRIPTION-----
            dateFilter.setLessThanOrEqual(null);
            dateFilter.setGreaterThan(lastSundayDate);
            // dateFilter.setLessThanOrEqual(saturdayDate);
            status.clear();
            // status.add(DakStatus.AWAITED_FOR_ORDER);
            status.add(DakStatus.AWAITED);
            status.add(DakStatus.CLEARED);
            statusFilter.setNotIn(status);
            statusFilter.setIn(null);
            dakCriteria.setCurrentStatus(statusFilter);
            dakCriteria.setLetterReceivedDate(dateFilter);

            long currentWeekInwards = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----

            statusFilter.setNotIn(null);
            status.clear();
            // status.add(DakStatus.AWAITED_FOR_ORDER);
            status.add(DakStatus.AWAITED);
            statusFilter.setIn(status);
            dakCriteria.setCurrentStatus(statusFilter);
            long currentWeekInwardsAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // -------------------for current week cleared-------------------------------

            // ----------WORK_DESCRIPTION-----
            dateFilter.setGreaterThan(lastSundayDate);
            status.clear();
            status.add(DakStatus.CLEARED);

            statusFilter.setIn(status);

            dakCriteria.setCurrentStatus(statusFilter);
            dakCriteria.setLetterReceivedDate(dateFilter);

            long currentWeekCleared = dakMasterQueryService.countByCriteria(dakCriteria);

            // ---------------for current week pendings---------------------------

            // ----------WORK_DESCRIPTION-----
            statusFilter.setIn(null);
            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(status);
            dakCriteria.setCurrentStatus(statusFilter);
            long weeklyPending = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----

            status.clear();
            // status.add(DakStatus.AWAITED_FOR_ORDER);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(null);
            statusFilter.setIn(status);
            dakCriteria.setCurrentStatus(statusFilter);
            long weeklyPendingAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // -------------------for selfGenerated letters--------------------

            // ----------WORK_DESCRIPTION-----
            typeFilter.setEquals(LetterType.SELF);

            status.clear();
            status.add(DakStatus.CLEARED);
            //status.add(DakStatus.HEARING_COMPLETED);
            statusFilter.setIn(null);
            statusFilter.setNotIn(status);

            dakCriteria.setLetterType(typeFilter);
            dakCriteria.setCurrentStatus(statusFilter);
            dakCriteria.setLetterReceivedDate(null);
            long selfGenratedLetters = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----

            status.clear();
            // status.add(DakStatus.AWAITED_FOR_ORDER);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(null);
            statusFilter.setIn(status);
            dakCriteria.setCurrentStatus(statusFilter);
            long selfGenratedLettersAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // ------------------one week before---

            // ----------WORK_DESCRIPTION-----
            status.clear();

            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            //status.add(DakStatus.AWAITED_FOR_ORDER);
            statusFilter.setNotIn(status);
            statusFilter.setIn(null);

            typeFilter.setEquals(LetterType.INWARD);
            dateFilter.setGreaterThan(lastSundayDate);
            dateFilter.setLessThanOrEqual(saturdayDate);
            dakCriteria.setLetterReceivedDate(dateFilter);
            dakCriteria.setLetterType(typeFilter);
            dakCriteria.setCurrentStatus(statusFilter);
            long oneWeekCount = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----

            status.clear();
            status.add(DakStatus.AWAITED);
            //  status.add(DakStatus.AWAITED_FOR_ORDER);
            statusFilter.setIn(status);
            statusFilter.setNotIn(null);
            dakCriteria.setCurrentStatus(statusFilter);
            long oneWeekCountAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // -----------------Two week before-------------

            // ----------WORK_DESCRIPTION-----
            Date oneDayBefore = new Date(lastSunday.getTime() - 2);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(oneDayBefore);
            calendar1.add(Calendar.WEEK_OF_YEAR, -1);
            Date oneWeekf = calendar1.getTime();
            Date oneWeekfirstDay = new Date(oneWeekf.getTime() + 2);

            // status.isEmpty();
            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(status);
            statusFilter.setIn(null);

            dateFilter.setGreaterThan(null);
            dateFilter.setLessThanOrEqual(null);

            dateFilter.setGreaterThanOrEqual(oneWeekfirstDay.toInstant());
            dateFilter.setLessThan(lastSundayDate);
            dakCriteria.setLetterReceivedDate(dateFilter);
            dakCriteria.setCurrentStatus(statusFilter);
            long secondWeekCount = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----
            status.clear();
            status.add(DakStatus.AWAITED);
            //  status.add(DakStatus.AWAITED_FOR_ORDER);
            statusFilter.setIn(status);
            statusFilter.setNotIn(null);
            dakCriteria.setCurrentStatus(statusFilter);
            long secondWeekCountAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // ---------------three week before------------

            // ----------WORK_DESCRIPTION-----
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(oneDayBefore);
            calendar2.add(Calendar.WEEK_OF_YEAR, -2);
            Date twoWeekf = calendar2.getTime();
            Date twoWeekfirstDay = new Date(twoWeekf.getTime() + 2);

            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            statusFilter.setIn(null);
            statusFilter.setNotIn(status);

            dateFilter.setGreaterThanOrEqual(twoWeekfirstDay.toInstant());
            dateFilter.setLessThan(oneWeekfirstDay.toInstant());
            dakCriteria.setLetterReceivedDate(dateFilter);
            dakCriteria.setCurrentStatus(statusFilter);
            long thirdWeekCount = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----
            status.clear();
            status.add(DakStatus.AWAITED);
            //status.add(DakStatus.AWAITED_FOR_ORDER);
            statusFilter.setIn(status);
            statusFilter.setNotIn(null);
            dakCriteria.setCurrentStatus(statusFilter);
            long thirdWeekCountAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // --------------One Month before------------

            // ----------WORK_DESCRIPTION-----
            Calendar calendar4 = Calendar.getInstance();
            calendar4.setTime(thisWeekSaturday);
            calendar4.add(Calendar.MONTH, -1);
            Date oneMonthbefore = calendar4.getTime();

            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(status);
            statusFilter.setIn(null);
            dateFilter.setGreaterThanOrEqual(oneMonthbefore.toInstant());
            dateFilter.setLessThan(saturdayDate);
            dakCriteria.setLetterReceivedDate(dateFilter);
            dakCriteria.setCurrentStatus(statusFilter);
            long oneMonthCount = dakMasterQueryService.countByCriteria(dakCriteria);

            long withinOneMonth = oneMonthCount - (oneWeekCount + secondWeekCount + thirdWeekCount);

            // ----------AWAIT_REGISTER-----
            status.clear();
            status.add(DakStatus.AWAITED);
            //status.add(DakStatus.AWAITED_FOR_ORDER);
            statusFilter.setIn(status);
            statusFilter.setNotIn(null);
            dakCriteria.setCurrentStatus(statusFilter);
            long oneMonthCountAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            long withinOneMonthAwait = oneMonthCountAwait - (oneWeekCountAwait + secondWeekCountAwait + thirdWeekCountAwait);

            // --------------with in two months-----------

            // ----------WORK_DESCRIPTION-----
            Calendar calendar5 = Calendar.getInstance();
            calendar5.setTime(thisWeekSaturday);
            calendar5.add(Calendar.MONTH, -2);
            Date twoMonthbefore = calendar5.getTime();

            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(status);
            statusFilter.setIn(null);
            dateFilter.setGreaterThanOrEqual(twoMonthbefore.toInstant());
            dateFilter.setLessThan(oneMonthbefore.toInstant());
            dakCriteria.setLetterReceivedDate(dateFilter);
            dakCriteria.setCurrentStatus(statusFilter);
            long twoMonthCount = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----
            status.clear();
            status.add(DakStatus.AWAITED);
            //status.add(DakStatus.AWAITED_FOR_ORDER);
            statusFilter.setIn(status);
            statusFilter.setNotIn(null);
            dakCriteria.setCurrentStatus(statusFilter);
            long twoMonthCountAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // --------------with in three month--------

            // ----------WORK_DESCRIPTION-----
            Calendar calendar6 = Calendar.getInstance();
            calendar6.setTime(thisWeekSaturday);
            calendar6.add(Calendar.MONTH, -3);
            Date threeMonthbefore = calendar6.getTime();

            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(status);
            statusFilter.setIn(null);
            dateFilter.setGreaterThanOrEqual(threeMonthbefore.toInstant());
            dateFilter.setLessThan(twoMonthbefore.toInstant());
            dakCriteria.setLetterReceivedDate(dateFilter);
            dakCriteria.setCurrentStatus(statusFilter);
            long threeMonthCount = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----
            status.clear();
            status.add(DakStatus.AWAITED);
            // status.add(DakStatus.AWAITED_FOR_ORDER);
            statusFilter.setIn(status);
            statusFilter.setNotIn(null);
            dakCriteria.setCurrentStatus(statusFilter);
            long threeMonthCountAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // --------------with in six month--------

            // ----------WORK_DESCRIPTION-----
            Calendar calendar7 = Calendar.getInstance();
            calendar7.setTime(thisWeekSaturday);
            calendar7.add(Calendar.MONTH, -6);
            Date sixMonthbefore = calendar7.getTime();

            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(status);
            statusFilter.setIn(null);
            dateFilter.setGreaterThanOrEqual(sixMonthbefore.toInstant());
            dateFilter.setLessThan(threeMonthbefore.toInstant());
            dakCriteria.setLetterReceivedDate(dateFilter);
            dakCriteria.setCurrentStatus(statusFilter);
            long sixMonthCount = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----
            status.clear();
            status.add(DakStatus.AWAITED);
            //status.add(DakStatus.AWAITED_FOR_ORDER);
            statusFilter.setIn(status);
            statusFilter.setNotIn(null);
            dakCriteria.setCurrentStatus(statusFilter);
            long sixMonthCountAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // --------------After 6 Month--------

            // ----------WORK_DESCRIPTION-----
            Calendar calendar8 = Calendar.getInstance();
            calendar8.setTime(thisWeekSaturday);
            calendar8.add(Calendar.YEAR, -1);
            Date sixMonthAfter = calendar8.getTime();

            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(status);
            statusFilter.setIn(null);
            dateFilter.setGreaterThanOrEqual(sixMonthAfter.toInstant());
            dateFilter.setLessThan(sixMonthbefore.toInstant());
            dakCriteria.setLetterReceivedDate(dateFilter);
            dakCriteria.setCurrentStatus(statusFilter);
            long sixToTwelveMonthCount = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----

            status.clear();
            status.add(DakStatus.AWAITED);
            //status.add(DakStatus.AWAITED_FOR_ORDER);
            statusFilter.setIn(status);
            statusFilter.setNotIn(null);
            dakCriteria.setCurrentStatus(statusFilter);
            long sixToTwelveMonthCountAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // --------------After 1 year -------------

            // ----------WORK_DESCRIPTION-----
            Calendar calendar12 = Calendar.getInstance();
            calendar12.setTime(thisWeekSaturday);
            calendar12.add(Calendar.YEAR, -3);
            Date oneYearAfter = calendar12.getTime();

            status.add(DakStatus.CLEARED);
            status.add(DakStatus.AWAITED);
            statusFilter.setNotIn(status);
            statusFilter.setIn(null);
            dateFilter.setGreaterThanOrEqual(oneYearAfter.toInstant());
            dateFilter.setLessThan(sixMonthAfter.toInstant());
            dakCriteria.setLetterReceivedDate(dateFilter);
            dakCriteria.setCurrentStatus(statusFilter);
            long afterOneYearCount = dakMasterQueryService.countByCriteria(dakCriteria);

            // ----------AWAIT_REGISTER-----
            status.clear();
            status.add(DakStatus.AWAITED);
            // status.add(DakStatus.AWAITED_FOR_ORDER);
            statusFilter.setIn(status);
            statusFilter.setNotIn(null);
            dakCriteria.setCurrentStatus(statusFilter);
            long afterOneYearCountAwait = dakMasterQueryService.countByCriteria(dakCriteria);

            // --------------------

            log.info("Going to push count in Ghoswara table");
            GhoshwaraDTO goshwaraObj = new GhoshwaraDTO();

            goshwaraObj.setRegisterType(RegisterType.WORK_DESCRIPTION);
            goshwaraObj.setInitialPendings((int) initalPendings);
            goshwaraObj.setCurrentWeekInwards((int) currentWeekInwards);
            long totalCount = initalPendings + currentWeekInwards;
            goshwaraObj.setTotal((int) totalCount);
            goshwaraObj.setSelfGenerated((int) selfGenratedLetters);
            goshwaraObj.setCurrentWeekCleared((int) currentWeekCleared);
            goshwaraObj.setCurrentWeekPendings((int) weeklyPending);
            goshwaraObj.setFirstWeek((int) oneWeekCount);
            goshwaraObj.setSecondWeek((int) secondWeekCount);
            goshwaraObj.setThirdWeek((int) thirdWeekCount);
            goshwaraObj.setFirstMonth((int) withinOneMonth);
            goshwaraObj.setSecondMonth((int) twoMonthCount);
            goshwaraObj.setThirdMonth((int) threeMonthCount);
            goshwaraObj.setWithinSixMonths((int) sixMonthCount);

            goshwaraObj.setDailyPendings((int) initalPendings - (int) currentWeekCleared);
            goshwaraObj.setAboveSixMonths((int) sixToTwelveMonthCount);
            goshwaraObj.setAboveOneYear((int) afterOneYearCount);

            goshwaraObj.setDate(endDate);
            goshwaraObj.setLastModified(Instant.now());
            goshwaraObj.setSecurityUser(securityUserObj);

            GhoshwaraDTO result = ghoshwaraService.save(goshwaraObj);
            log.info("push WORK DESCRIPTION count in Ghoswara table");
            GhoshwaraDTO goshwaraObj1 = new GhoshwaraDTO();

            goshwaraObj1.setRegisterType(RegisterType.AWAITED_REGISTER);
            goshwaraObj1.setInitialPendings((int) initalPendingsAwait);
            goshwaraObj1.setCurrentWeekInwards((int) currentWeekInwardsAwait);
            long totalCount1 = initalPendingsAwait + currentWeekInwardsAwait;
            goshwaraObj1.setTotal((int) totalCount1);
            goshwaraObj1.setSelfGenerated((int) selfGenratedLettersAwait);
            goshwaraObj1.setCurrentWeekCleared(0);
            goshwaraObj1.setCurrentWeekPendings((int) weeklyPendingAwait);
            goshwaraObj1.setFirstWeek((int) oneWeekCountAwait);
            goshwaraObj1.setSecondWeek((int) secondWeekCountAwait);
            goshwaraObj1.setThirdWeek((int) thirdWeekCountAwait);
            goshwaraObj1.setFirstMonth((int) withinOneMonthAwait);
            goshwaraObj1.setSecondMonth((int) twoMonthCountAwait);
            goshwaraObj1.setThirdMonth((int) threeMonthCountAwait);
            goshwaraObj1.setWithinSixMonths((int) sixMonthCountAwait);

            goshwaraObj1.setDailyPendings((int) initalPendingsAwait - 0);
            goshwaraObj1.setAboveSixMonths((int) sixToTwelveMonthCountAwait);
            goshwaraObj1.setAboveOneYear((int) afterOneYearCountAwait);

            goshwaraObj1.setDate(endDate);
            goshwaraObj1.setLastModified(Instant.now());
            goshwaraObj1.setSecurityUser(securityUserObj);
            System.out.println("\n\n\n\n\n\n\nOBJECT goshwaraObj1" + goshwaraObj1);
            GhoshwaraDTO result1 = ghoshwaraService.save(goshwaraObj1);
            log.info("push AWAITED REGISTER count in Ghoswara table");
        }
    }

    /**
     * DELETE : delete the two months ago Goshwara counts . {@Scheduled : scheduled
     * task for every monday}
     */
    @Scheduled(cron = "${cron.everyMonday}")
    private void deletePreviousRecord() {
        log.info("Goshwara Weekely Schedular getup time is now {}", dateFormat.format(new Date()));

        try {
            if (shouldFetchDataFromDak) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                Date yesterdayDate = calendar.getTime();

                // -----------Calculate two month before date--------------
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(yesterdayDate);
                calendar1.add(Calendar.MONTH, -2);
                Date twoMonthbefore = calendar1.getTime();
                // -----------Calculate three month before date--------------
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(yesterdayDate);
                calendar2.add(Calendar.MONTH, -3);
                Date threeMonthbefore = calendar2.getTime();
                // -----------------------------------------------------------

                GhoshwaraCriteria ghoshwaraCriteria = new GhoshwaraCriteria();

                InstantFilter dateFilter = new InstantFilter();
                dateFilter.setGreaterThanOrEqual(threeMonthbefore.toInstant());
                dateFilter.setLessThanOrEqual(twoMonthbefore.toInstant());

                ghoshwaraCriteria.setLastModified(null);
                ghoshwaraCriteria.setLastModified(dateFilter);
                List<GhoshwaraDTO> dakList = ghoshwaraQueryService.findByCriteria(ghoshwaraCriteria);

                for (GhoshwaraDTO ghoshwaraObj : dakList) {
                    log.debug("REST request to delete Ghoshwara : {}", ghoshwaraObj.getId());
                    ghoshwaraService.delete(ghoshwaraObj.getId());

                    System.out.println("All two months ago counts are deleted in Ghoshwara on date:" + yesterdayDate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * DELETE : delete the two months ago Goshwara counts . {@Scheduled : scheduled
     * task for every monday}
     */
    @Scheduled(cron = "${cron.everyLastDayOfYear}")
    private void resetNextValInRecord() {
        log.info("New Year Schedular getup time is now {}", dateFormat.format(new Date()));

        try {
            if (shouldResetNextVal) {
                OrganizationCriteria orgCriteria = new OrganizationCriteria();

                List<OrganizationDTO> orgList = organizationQueryService.findByCriteria(orgCriteria);
                for (OrganizationDTO organization : orgList) {
                    int next_val_inward = 1;
                    int next_val_outward = 1;
                    organizationRepository.resetNextVal(next_val_inward, next_val_outward, organization.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
