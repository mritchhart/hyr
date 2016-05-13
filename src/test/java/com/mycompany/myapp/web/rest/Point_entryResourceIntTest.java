package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.HopeRanchLearningAcademyApp;
import com.mycompany.myapp.domain.Point_entry;
import com.mycompany.myapp.repository.Point_entryRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.EntryStatus;

/**
 * Test class for the Point_entryResource REST controller.
 *
 * @see Point_entryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HopeRanchLearningAcademyApp.class)
@WebAppConfiguration
@IntegrationTest
public class Point_entryResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Integer DEFAULT_ENT_VALUE = 1;
    private static final Integer UPDATED_ENT_VALUE = 2;

    private static final EntryStatus DEFAULT_ENT_STATUS = EntryStatus.approved;
    private static final EntryStatus UPDATED_ENT_STATUS = EntryStatus.denied;

    private static final ZonedDateTime DEFAULT_ENT_SUBMISSION_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ENT_SUBMISSION_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ENT_SUBMISSION_TIME_STR = dateTimeFormatter.format(DEFAULT_ENT_SUBMISSION_TIME);

    private static final ZonedDateTime DEFAULT_ENT_ACTION_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ENT_ACTION_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ENT_ACTION_TIME_STR = dateTimeFormatter.format(DEFAULT_ENT_ACTION_TIME);

    @Inject
    private Point_entryRepository point_entryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPoint_entryMockMvc;

    private Point_entry point_entry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Point_entryResource point_entryResource = new Point_entryResource();
        ReflectionTestUtils.setField(point_entryResource, "point_entryRepository", point_entryRepository);
        this.restPoint_entryMockMvc = MockMvcBuilders.standaloneSetup(point_entryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        point_entry = new Point_entry();
        point_entry.setEnt_value(DEFAULT_ENT_VALUE);
        point_entry.setEnt_status(DEFAULT_ENT_STATUS);
        point_entry.setEnt_submission_time(DEFAULT_ENT_SUBMISSION_TIME);
        point_entry.setEnt_action_time(DEFAULT_ENT_ACTION_TIME);
    }

    @Test
    @Transactional
    public void createPoint_entry() throws Exception {
        int databaseSizeBeforeCreate = point_entryRepository.findAll().size();

        // Create the Point_entry

        restPoint_entryMockMvc.perform(post("/api/point-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(point_entry)))
                .andExpect(status().isCreated());

        // Validate the Point_entry in the database
        List<Point_entry> point_entries = point_entryRepository.findAll();
        assertThat(point_entries).hasSize(databaseSizeBeforeCreate + 1);
        Point_entry testPoint_entry = point_entries.get(point_entries.size() - 1);
        assertThat(testPoint_entry.getEnt_value()).isEqualTo(DEFAULT_ENT_VALUE);
        assertThat(testPoint_entry.getEnt_status()).isEqualTo(DEFAULT_ENT_STATUS);
        assertThat(testPoint_entry.getEnt_submission_time()).isEqualTo(DEFAULT_ENT_SUBMISSION_TIME);
        assertThat(testPoint_entry.getEnt_action_time()).isEqualTo(DEFAULT_ENT_ACTION_TIME);
    }

    @Test
    @Transactional
    public void checkEnt_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = point_entryRepository.findAll().size();
        // set the field null
        point_entry.setEnt_value(null);

        // Create the Point_entry, which fails.

        restPoint_entryMockMvc.perform(post("/api/point-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(point_entry)))
                .andExpect(status().isBadRequest());

        List<Point_entry> point_entries = point_entryRepository.findAll();
        assertThat(point_entries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPoint_entries() throws Exception {
        // Initialize the database
        point_entryRepository.saveAndFlush(point_entry);

        // Get all the point_entries
        restPoint_entryMockMvc.perform(get("/api/point-entries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(point_entry.getId().intValue())))
                .andExpect(jsonPath("$.[*].ent_value").value(hasItem(DEFAULT_ENT_VALUE)))
                .andExpect(jsonPath("$.[*].ent_status").value(hasItem(DEFAULT_ENT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].ent_submission_time").value(hasItem(DEFAULT_ENT_SUBMISSION_TIME_STR)))
                .andExpect(jsonPath("$.[*].ent_action_time").value(hasItem(DEFAULT_ENT_ACTION_TIME_STR)));
    }

    @Test
    @Transactional
    public void getPoint_entry() throws Exception {
        // Initialize the database
        point_entryRepository.saveAndFlush(point_entry);

        // Get the point_entry
        restPoint_entryMockMvc.perform(get("/api/point-entries/{id}", point_entry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(point_entry.getId().intValue()))
            .andExpect(jsonPath("$.ent_value").value(DEFAULT_ENT_VALUE))
            .andExpect(jsonPath("$.ent_status").value(DEFAULT_ENT_STATUS.toString()))
            .andExpect(jsonPath("$.ent_submission_time").value(DEFAULT_ENT_SUBMISSION_TIME_STR))
            .andExpect(jsonPath("$.ent_action_time").value(DEFAULT_ENT_ACTION_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingPoint_entry() throws Exception {
        // Get the point_entry
        restPoint_entryMockMvc.perform(get("/api/point-entries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoint_entry() throws Exception {
        // Initialize the database
        point_entryRepository.saveAndFlush(point_entry);
        int databaseSizeBeforeUpdate = point_entryRepository.findAll().size();

        // Update the point_entry
        Point_entry updatedPoint_entry = new Point_entry();
        updatedPoint_entry.setId(point_entry.getId());
        updatedPoint_entry.setEnt_value(UPDATED_ENT_VALUE);
        updatedPoint_entry.setEnt_status(UPDATED_ENT_STATUS);
        updatedPoint_entry.setEnt_submission_time(UPDATED_ENT_SUBMISSION_TIME);
        updatedPoint_entry.setEnt_action_time(UPDATED_ENT_ACTION_TIME);

        restPoint_entryMockMvc.perform(put("/api/point-entries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPoint_entry)))
                .andExpect(status().isOk());

        // Validate the Point_entry in the database
        List<Point_entry> point_entries = point_entryRepository.findAll();
        assertThat(point_entries).hasSize(databaseSizeBeforeUpdate);
        Point_entry testPoint_entry = point_entries.get(point_entries.size() - 1);
        assertThat(testPoint_entry.getEnt_value()).isEqualTo(UPDATED_ENT_VALUE);
        assertThat(testPoint_entry.getEnt_status()).isEqualTo(UPDATED_ENT_STATUS);
        assertThat(testPoint_entry.getEnt_submission_time()).isEqualTo(UPDATED_ENT_SUBMISSION_TIME);
        assertThat(testPoint_entry.getEnt_action_time()).isEqualTo(UPDATED_ENT_ACTION_TIME);
    }

    @Test
    @Transactional
    public void deletePoint_entry() throws Exception {
        // Initialize the database
        point_entryRepository.saveAndFlush(point_entry);
        int databaseSizeBeforeDelete = point_entryRepository.findAll().size();

        // Get the point_entry
        restPoint_entryMockMvc.perform(delete("/api/point-entries/{id}", point_entry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Point_entry> point_entries = point_entryRepository.findAll();
        assertThat(point_entries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
