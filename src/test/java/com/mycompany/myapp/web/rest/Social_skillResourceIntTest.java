package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.HopeRanchLearningAcademyApp;
import com.mycompany.myapp.domain.Social_skill;
import com.mycompany.myapp.repository.Social_skillRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Social_skillResource REST controller.
 *
 * @see Social_skillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HopeRanchLearningAcademyApp.class)
@WebAppConfiguration
@IntegrationTest
public class Social_skillResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_LEVEL = "AAAAA";
    private static final String UPDATED_LEVEL = "BBBBB";

    @Inject
    private Social_skillRepository social_skillRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSocial_skillMockMvc;

    private Social_skill social_skill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Social_skillResource social_skillResource = new Social_skillResource();
        ReflectionTestUtils.setField(social_skillResource, "social_skillRepository", social_skillRepository);
        this.restSocial_skillMockMvc = MockMvcBuilders.standaloneSetup(social_skillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        social_skill = new Social_skill();
        social_skill.setName(DEFAULT_NAME);
        social_skill.setDescription(DEFAULT_DESCRIPTION);
        social_skill.setLevel(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createSocial_skill() throws Exception {
        int databaseSizeBeforeCreate = social_skillRepository.findAll().size();

        // Create the Social_skill

        restSocial_skillMockMvc.perform(post("/api/social-skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(social_skill)))
                .andExpect(status().isCreated());

        // Validate the Social_skill in the database
        List<Social_skill> social_skills = social_skillRepository.findAll();
        assertThat(social_skills).hasSize(databaseSizeBeforeCreate + 1);
        Social_skill testSocial_skill = social_skills.get(social_skills.size() - 1);
        assertThat(testSocial_skill.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocial_skill.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSocial_skill.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = social_skillRepository.findAll().size();
        // set the field null
        social_skill.setName(null);

        // Create the Social_skill, which fails.

        restSocial_skillMockMvc.perform(post("/api/social-skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(social_skill)))
                .andExpect(status().isBadRequest());

        List<Social_skill> social_skills = social_skillRepository.findAll();
        assertThat(social_skills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSocial_skills() throws Exception {
        // Initialize the database
        social_skillRepository.saveAndFlush(social_skill);

        // Get all the social_skills
        restSocial_skillMockMvc.perform(get("/api/social-skills?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(social_skill.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }

    @Test
    @Transactional
    public void getSocial_skill() throws Exception {
        // Initialize the database
        social_skillRepository.saveAndFlush(social_skill);

        // Get the social_skill
        restSocial_skillMockMvc.perform(get("/api/social-skills/{id}", social_skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(social_skill.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSocial_skill() throws Exception {
        // Get the social_skill
        restSocial_skillMockMvc.perform(get("/api/social-skills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocial_skill() throws Exception {
        // Initialize the database
        social_skillRepository.saveAndFlush(social_skill);
        int databaseSizeBeforeUpdate = social_skillRepository.findAll().size();

        // Update the social_skill
        Social_skill updatedSocial_skill = new Social_skill();
        updatedSocial_skill.setId(social_skill.getId());
        updatedSocial_skill.setName(UPDATED_NAME);
        updatedSocial_skill.setDescription(UPDATED_DESCRIPTION);
        updatedSocial_skill.setLevel(UPDATED_LEVEL);

        restSocial_skillMockMvc.perform(put("/api/social-skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSocial_skill)))
                .andExpect(status().isOk());

        // Validate the Social_skill in the database
        List<Social_skill> social_skills = social_skillRepository.findAll();
        assertThat(social_skills).hasSize(databaseSizeBeforeUpdate);
        Social_skill testSocial_skill = social_skills.get(social_skills.size() - 1);
        assertThat(testSocial_skill.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocial_skill.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSocial_skill.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void deleteSocial_skill() throws Exception {
        // Initialize the database
        social_skillRepository.saveAndFlush(social_skill);
        int databaseSizeBeforeDelete = social_skillRepository.findAll().size();

        // Get the social_skill
        restSocial_skillMockMvc.perform(delete("/api/social-skills/{id}", social_skill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Social_skill> social_skills = social_skillRepository.findAll();
        assertThat(social_skills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
