package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Student_social_skill;
import com.mycompany.myapp.repository.Student_social_skillRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Student_social_skill.
 */
@RestController
@RequestMapping("/api")
public class Student_social_skillResource {

    private final Logger log = LoggerFactory.getLogger(Student_social_skillResource.class);

    @Inject
    private Student_social_skillRepository Student_social_skillRepository;

    /**
     * POST  /student-social-skills : Create a new student_social_skill.
     *
     * @param student_social_skill the student_social_skill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new student_social_skill, or with status 400 (Bad Request) if the student_social_skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/student-social-skills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Student_social_skill> createStudent_social_skill(@RequestBody Student_social_skill student_social_skill) throws URISyntaxException {
        log.debug("REST request to save Student_social_skill : {}", student_social_skill);
        if (student_social_skill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("student_social_skill", "idexists", "A new student_social_skill cannot already have an ID")).body(null);
        }
        Student_social_skill result = Student_social_skillRepository.save(student_social_skill);
        return ResponseEntity.created(new URI("/api/student-social-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("student_social_skill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-social-skills : Updates an existing student_social_skill.
     *
     * @param student_social_skill the student_social_skill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated student_social_skill,
     * or with status 400 (Bad Request) if the student_social_skill is not valid,
     * or with status 500 (Internal Server Error) if the student_social_skill couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/student-social-skills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Student_social_skill> updateStudent_social_skill(@RequestBody Student_social_skill student_social_skill) throws URISyntaxException {
        log.debug("REST request to update Student_social_skill : {}", student_social_skill);
        if (student_social_skill.getId() == null) {
            return createStudent_social_skill(student_social_skill);
        }
        Student_social_skill result = Student_social_skillRepository.save(student_social_skill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("student_social_skill", student_social_skill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-social-skills : get all the student_social_skills.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of student_social_skills in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/student-social-skills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Student_social_skill>> getAllStudent_social_skills(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Student_social_skills");
        Page<Student_social_skill> page = Student_social_skillRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-social-skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-social-skills/:id : get the "id" student_social_skill.
     *
     * @param id the id of the student_social_skill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the student_social_skill, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/student-social-skills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Student_social_skill> getStudent_social_skill(@PathVariable Long id) {
        log.debug("REST request to get Student_social_skill : {}", id);
        Student_social_skill student_social_skill = Student_social_skillRepository.findOne(id);
        return Optional.ofNullable(student_social_skill)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /student-social-skills/:id : delete the "id" student_social_skill.
     *
     * @param id the id of the student_social_skill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/student-social-skills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStudent_social_skill(@PathVariable Long id) {
        log.debug("REST request to delete Student_social_skill : {}", id);
        Student_social_skillRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("student_social_skill", id.toString())).build();
    }

    /**
     * MYSERVICE - get all Skill IDs from student_Social_Skill, where "id" = student ID
     */
    @RequestMapping(value = "/studentSkills/{stuId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ArrayList findAllSkillIDsByStudentId(@PathVariable Long stuId) {
        log.debug("REST request to get Student_Social_Skill IDs : {}", stuId);
        return Student_social_skillRepository.findAllSkillIDsByStudentId(stuId);

    }

    /**
     * MyService - Get the sum of points, skill name, and student name for student {id}, and social skill {id}
     */
    @RequestMapping(value = "/sss/{stuId}/{sklId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List findSSSByStudentIdAndSkillId(@PathVariable Long stuId, @PathVariable Long sklId) {
        log.debug("REST request to get Point_Entry : {}", stuId, sklId);
        return Student_social_skillRepository.findSSSByStudentIdAndSkillId(stuId, sklId);
    }

}
