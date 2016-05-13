package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Classroom;
import com.mycompany.myapp.repository.ClassroomRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Classroom.
 */
@RestController
@RequestMapping("/api")
public class ClassroomResource {

    private final Logger log = LoggerFactory.getLogger(ClassroomResource.class);
        
    @Inject
    private ClassroomRepository classroomRepository;
    
    /**
     * POST  /classrooms : Create a new classroom.
     *
     * @param classroom the classroom to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classroom, or with status 400 (Bad Request) if the classroom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/classrooms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Classroom> createClassroom(@RequestBody Classroom classroom) throws URISyntaxException {
        log.debug("REST request to save Classroom : {}", classroom);
        if (classroom.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("classroom", "idexists", "A new classroom cannot already have an ID")).body(null);
        }
        Classroom result = classroomRepository.save(classroom);
        return ResponseEntity.created(new URI("/api/classrooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("classroom", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classrooms : Updates an existing classroom.
     *
     * @param classroom the classroom to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classroom,
     * or with status 400 (Bad Request) if the classroom is not valid,
     * or with status 500 (Internal Server Error) if the classroom couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/classrooms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Classroom> updateClassroom(@RequestBody Classroom classroom) throws URISyntaxException {
        log.debug("REST request to update Classroom : {}", classroom);
        if (classroom.getId() == null) {
            return createClassroom(classroom);
        }
        Classroom result = classroomRepository.save(classroom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("classroom", classroom.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classrooms : get all the classrooms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of classrooms in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/classrooms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Classroom>> getAllClassrooms(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Classrooms");
        Page<Classroom> page = classroomRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/classrooms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /classrooms/:id : get the "id" classroom.
     *
     * @param id the id of the classroom to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classroom, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/classrooms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Classroom> getClassroom(@PathVariable Long id) {
        log.debug("REST request to get Classroom : {}", id);
        Classroom classroom = classroomRepository.findOne(id);
        return Optional.ofNullable(classroom)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /classrooms/:id : delete the "id" classroom.
     *
     * @param id the id of the classroom to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/classrooms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        log.debug("REST request to delete Classroom : {}", id);
        classroomRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("classroom", id.toString())).build();
    }

}
