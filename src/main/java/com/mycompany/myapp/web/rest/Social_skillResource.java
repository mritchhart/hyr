package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Social_skill;
import com.mycompany.myapp.repository.Social_skillRepository;
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
import java.util.Optional;

/**
 * REST controller for managing Social_skill.
 */
@RestController
@RequestMapping("/api")
public class Social_skillResource {

    private final Logger log = LoggerFactory.getLogger(Social_skillResource.class);
        
    @Inject
    private Social_skillRepository social_skillRepository;
    
    /**
     * POST  /social-skills : Create a new social_skill.
     *
     * @param social_skill the social_skill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new social_skill, or with status 400 (Bad Request) if the social_skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/social-skills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Social_skill> createSocial_skill(@Valid @RequestBody Social_skill social_skill) throws URISyntaxException {
        log.debug("REST request to save Social_skill : {}", social_skill);
        if (social_skill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("social_skill", "idexists", "A new social_skill cannot already have an ID")).body(null);
        }
        Social_skill result = social_skillRepository.save(social_skill);
        return ResponseEntity.created(new URI("/api/social-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("social_skill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /social-skills : Updates an existing social_skill.
     *
     * @param social_skill the social_skill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated social_skill,
     * or with status 400 (Bad Request) if the social_skill is not valid,
     * or with status 500 (Internal Server Error) if the social_skill couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/social-skills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Social_skill> updateSocial_skill(@Valid @RequestBody Social_skill social_skill) throws URISyntaxException {
        log.debug("REST request to update Social_skill : {}", social_skill);
        if (social_skill.getId() == null) {
            return createSocial_skill(social_skill);
        }
        Social_skill result = social_skillRepository.save(social_skill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("social_skill", social_skill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /social-skills : get all the social_skills.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of social_skills in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/social-skills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Social_skill>> getAllSocial_skills(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Social_skills");
        Page<Social_skill> page = social_skillRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/social-skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /social-skills/:id : get the "id" social_skill.
     *
     * @param id the id of the social_skill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the social_skill, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/social-skills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Social_skill> getSocial_skill(@PathVariable Long id) {
        log.debug("REST request to get Social_skill : {}", id);
        Social_skill social_skill = social_skillRepository.findOne(id);
        return Optional.ofNullable(social_skill)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /social-skills/:id : delete the "id" social_skill.
     *
     * @param id the id of the social_skill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/social-skills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSocial_skill(@PathVariable Long id) {
        log.debug("REST request to delete Social_skill : {}", id);
        social_skillRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("social_skill", id.toString())).build();
    }

}
