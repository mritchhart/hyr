package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Point_entry;
import com.mycompany.myapp.repository.Point_entryRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * REST controller for managing Point_entry.
 */
@RestController
@RequestMapping("/api")
public class Point_entryResource {

    private final Logger log = LoggerFactory.getLogger(Point_entryResource.class);

    @Inject
    private Point_entryRepository Point_entryRepository;

    /**
     * POST  /point-entries : Create a new point_entry.
     *
     * @param point_entry the point_entry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new point_entry, or with status 400 (Bad Request) if the point_entry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/point-entries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Point_entry> createPoint_entry(@Valid @RequestBody Point_entry point_entry) throws URISyntaxException {
        log.debug("REST request to save Point_entry : {}", point_entry);
        if (point_entry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("point_entry", "idexists", "A new point_entry cannot already have an ID")).body(null);
        }
        Point_entry result = Point_entryRepository.save(point_entry);
        return ResponseEntity.created(new URI("/api/point-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("point_entry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /point-entries : Updates an existing point_entry.
     *
     * @param point_entry the point_entry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated point_entry,
     * or with status 400 (Bad Request) if the point_entry is not valid,
     * or with status 500 (Internal Server Error) if the point_entry couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/point-entries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Point_entry> updatePoint_entry(@Valid @RequestBody Point_entry point_entry) throws URISyntaxException {
        log.debug("REST request to update Point_entry : {}", point_entry);
        if (point_entry.getId() == null) {
            return createPoint_entry(point_entry);
        }
        Point_entry result = Point_entryRepository.save(point_entry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("point_entry", point_entry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /point-entries : get all the point_entries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of point_entries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/point-entries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Point_entry>> getAllPoint_entries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Point_entries");
        Page<Point_entry> page = Point_entryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/point-entries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /point-entries/:id : get the "id" point_entry.
     *
     * @param id the id of the point_entry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the point_entry, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/point-entries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Point_entry> getPoint_entry(@PathVariable Long id) {
        log.debug("REST request to get Point_entry : {}", id);
        Point_entry point_entry = Point_entryRepository.findOne(id);
        return Optional.ofNullable(point_entry)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /point-entries/:id : delete the "id" point_entry.
     *
     * @param id the id of the point_entry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/point-entries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePoint_entry(@PathVariable Long id) {
        log.debug("REST request to delete Point_entry : {}", id);
        Point_entryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("point_entry", id.toString())).build();
    }

    /**
     * MyService - Get a list of point entries for student {id}
     */
    @RequestMapping(value = "/points/{stuId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Point_entry> getAllPoint_EntryForStudent(@PathVariable Long stuId) {
        log.debug("REST request to get Point_Entry for student id : {}", stuId);
        return Point_entryRepository.ooga(stuId);
    }

    /**
     * MyService - Get list of point entries for student {id}, and social skill {id}
     */
    @RequestMapping(value = "/points/{stuId}/{sklId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Point_entry> getAllEntry(@PathVariable Long stuId, @PathVariable Long sklId) {
        log.debug("REST request to get Point_Entry for student id : {}", stuId);
        return Point_entryRepository.findAllByStudentIdAndSkillId(stuId, sklId);
    }

    /**
     * MyService - Get the sum of point entry values for student {id}, and social skill {id}
     */
    @RequestMapping(value = "/pointSum/{stuId}/{sklId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Integer getAllPointEntrySum(@PathVariable Long stuId, @PathVariable Long sklId) {
        log.debug("REST request to get Point_Entry for student id : {}", stuId);
        return Point_entryRepository.countSumByStudentIdAndSkillId(stuId, sklId);
    }

    /**
     * MyService - Get the sum of points, skill name, and student name for student {id}, and social skill {id}
     */
    @RequestMapping(value = "/skillData/{stuId}/{sklId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List findSkillAndPointsByStudentIdAndSkillId(@PathVariable Long stuId, @PathVariable Long sklId) {
        log.debug("REST request to get Point_Entry : {}", stuId, sklId);
        return Point_entryRepository.findSkillAndPointsByStudentIdAndSkillId(stuId, sklId);
    }

    /**
     * MyService - Get the sum of points FOR THE CURRENT DAY, skill name, and student name for student {id}, and social skill {id}
     */
    @RequestMapping(value = "/skillData/daily/{stuId}/{sklId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List findSkillAndPointsDailyByStudentIdAndSkillId(@PathVariable Long stuId, @PathVariable Long sklId) {
        log.debug("REST request to get Point_Entry : {}", stuId, sklId);
        Long myNumber = 5L;
        Date currentDateraw = new Date();
        Date currentDateMinusTwelveHoursraw = new Date(currentDateraw.getTime() - 43200000);
        ZonedDateTime currentDate = ZonedDateTime.ofInstant(currentDateraw.toInstant(), ZoneId.systemDefault());
        ZonedDateTime currentDateMinusTwelveHours = ZonedDateTime.ofInstant(currentDateMinusTwelveHoursraw.toInstant(), ZoneId.systemDefault());
        return Point_entryRepository.findSkillAndPointsDailyByStudentIdAndSkillId(stuId, sklId, currentDateMinusTwelveHours, currentDate);
    }

    /**
     * MyService - Get the sum of points FOR A CUSTOM START AND END DATE, skill name, and student name for student {id}, and social skill {id}
     */
    @RequestMapping(value = "/skillData/custom/{stuId}/{sklId}/{startDateRaw}/{endDateRaw}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List findSkillAndPointsCustomByStudentIdAndSkillId(@PathVariable Long stuId, @PathVariable Long sklId, @PathVariable Long startDateRaw, @PathVariable Long endDateRaw) {
        log.debug("REST request to get Point_Entry : {}", stuId, sklId, startDateRaw, endDateRaw);
        Date startDateRawMilliseconds = new Date(startDateRaw);
        Date endDateRawMilliseconds = new Date(endDateRaw);
        ZonedDateTime startDate = ZonedDateTime.ofInstant(startDateRawMilliseconds.toInstant(), ZoneId.systemDefault());
        ZonedDateTime endDate = ZonedDateTime.ofInstant(endDateRawMilliseconds.toInstant(), ZoneId.systemDefault());
        return Point_entryRepository.findSkillAndPointsCustomByStudentIdAndSkillId(stuId, sklId, startDate, endDate);
    }

    /**
     * MyService - Get the sum of points, skill name, and student name for student {id}
     */
    @RequestMapping(value = "/skillData/{stuId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List findAllSkillsAndPointsByStudentIdAndSkillId(@PathVariable Long stuId) {
        log.debug("REST request to get Point_Entry : {}", stuId);
        return Point_entryRepository.findAllSkillsAndPointsByStudentIdAndSkillId(stuId);
    }

}
