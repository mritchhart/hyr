package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.HopeRanchLearningAcademyApp;
import com.mycompany.myapp.domain.Student_social_skill;
import com.mycompany.myapp.repository.Student_social_skillRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Status;

/**
 * Test class for the Student_social_skillResource REST controller.
 *
 * @see Student_social_skillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HopeRanchLearningAcademyApp.class)
@WebAppConfiguration
@IntegrationTest
public class Student_social_skillResourceIntTest {


    private static final Integer DEFAULT_GROSS_PTS = 1;
    private static final Integer UPDATED_GROSS_PTS = 2;

    private static final Integer DEFAULT_NET_PTS = 1;
    private static final Integer UPDATED_NET_PTS = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Status DEFAULT_STATUS = Status.active;
    private static final Status UPDATED_STATUS = Status.inactive;

    @Inject
    private Student_social_skillRepository student_social_skillRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStudent_social_skillMockMvc;

    private Student_social_skill student_social_skill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Student_social_skillResource student_social_skillResource = new Student_social_skillResource();
        ReflectionTestUtils.setField(student_social_skillResource, "student_social_skillRepository", student_social_skillRepository);
        this.restStudent_social_skillMockMvc = MockMvcBuilders.standaloneSetup(student_social_skillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        student_social_skill = new Student_social_skill();
        student_social_skill.setGross_pts(DEFAULT_GROSS_PTS);
        student_social_skill.setNet_pts(DEFAULT_NET_PTS);
        student_social_skill.setStart_date(DEFAULT_START_DATE);
        student_social_skill.setEnd_date(DEFAULT_END_DATE);
        student_social_skill.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createStudent_social_skill() throws Exception {
        int databaseSizeBeforeCreate = student_social_skillRepository.findAll().size();

        // Create the Student_social_skill

        restStudent_social_skillMockMvc.perform(post("/api/student-social-skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(student_social_skill)))
                .andExpect(status().isCreated());

        // Validate the Student_social_skill in the database
        List<Student_social_skill> student_social_skills = student_social_skillRepository.findAll();
        assertThat(student_social_skills).hasSize(databaseSizeBeforeCreate + 1);
        Student_social_skill testStudent_social_skill = student_social_skills.get(student_social_skills.size() - 1);
        assertThat(testStudent_social_skill.getGross_pts()).isEqualTo(DEFAULT_GROSS_PTS);
        assertThat(testStudent_social_skill.getNet_pts()).isEqualTo(DEFAULT_NET_PTS);
        assertThat(testStudent_social_skill.getStart_date()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testStudent_social_skill.getEnd_date()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testStudent_social_skill.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllStudent_social_skills() throws Exception {
        // Initialize the database
        student_social_skillRepository.saveAndFlush(student_social_skill);

        // Get all the student_social_skills
        restStudent_social_skillMockMvc.perform(get("/api/student-social-skills?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(student_social_skill.getId().intValue())))
                .andExpect(jsonPath("$.[*].gross_pts").value(hasItem(DEFAULT_GROSS_PTS)))
                .andExpect(jsonPath("$.[*].net_pts").value(hasItem(DEFAULT_NET_PTS)))
                .andExpect(jsonPath("$.[*].start_date").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].end_date").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getStudent_social_skill() throws Exception {
        // Initialize the database
        student_social_skillRepository.saveAndFlush(student_social_skill);

        // Get the student_social_skill
        restStudent_social_skillMockMvc.perform(get("/api/student-social-skills/{id}", student_social_skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(student_social_skill.getId().intValue()))
            .andExpect(jsonPath("$.gross_pts").value(DEFAULT_GROSS_PTS))
            .andExpect(jsonPath("$.net_pts").value(DEFAULT_NET_PTS))
            .andExpect(jsonPath("$.start_date").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.end_date").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudent_social_skill() throws Exception {
        // Get the student_social_skill
        restStudent_social_skillMockMvc.perform(get("/api/student-social-skills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent_social_skill() throws Exception {
        // Initialize the database
        student_social_skillRepository.saveAndFlush(student_social_skill);
        int databaseSizeBeforeUpdate = student_social_skillRepository.findAll().size();

        // Update the student_social_skill
        Student_social_skill updatedStudent_social_skill = new Student_social_skill();
        updatedStudent_social_skill.setId(student_social_skill.getId());
        updatedStudent_social_skill.setGross_pts(UPDATED_GROSS_PTS);
        updatedStudent_social_skill.setNet_pts(UPDATED_NET_PTS);
        updatedStudent_social_skill.setStart_date(UPDATED_START_DATE);
        updatedStudent_social_skill.setEnd_date(UPDATED_END_DATE);
        updatedStudent_social_skill.setStatus(UPDATED_STATUS);

        restStudent_social_skillMockMvc.perform(put("/api/student-social-skills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStudent_social_skill)))
                .andExpect(status().isOk());

        // Validate the Student_social_skill in the database
        List<Student_social_skill> student_social_skills = student_social_skillRepository.findAll();
        assertThat(student_social_skills).hasSize(databaseSizeBeforeUpdate);
        Student_social_skill testStudent_social_skill = student_social_skills.get(student_social_skills.size() - 1);
        assertThat(testStudent_social_skill.getGross_pts()).isEqualTo(UPDATED_GROSS_PTS);
        assertThat(testStudent_social_skill.getNet_pts()).isEqualTo(UPDATED_NET_PTS);
        assertThat(testStudent_social_skill.getStart_date()).isEqualTo(UPDATED_START_DATE);
        assertThat(testStudent_social_skill.getEnd_date()).isEqualTo(UPDATED_END_DATE);
        assertThat(testStudent_social_skill.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteStudent_social_skill() throws Exception {
        // Initialize the database
        student_social_skillRepository.saveAndFlush(student_social_skill);
        int databaseSizeBeforeDelete = student_social_skillRepository.findAll().size();

        // Get the student_social_skill
        restStudent_social_skillMockMvc.perform(delete("/api/student-social-skills/{id}", student_social_skill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Student_social_skill> student_social_skills = student_social_skillRepository.findAll();
        assertThat(student_social_skills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
