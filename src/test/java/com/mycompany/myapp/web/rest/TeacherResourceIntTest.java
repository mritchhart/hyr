package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.HopeRanchLearningAcademyApp;
import com.mycompany.myapp.domain.Teacher;
import com.mycompany.myapp.repository.TeacherRepository;

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
 * Test class for the TeacherResource REST controller.
 *
 * @see TeacherResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HopeRanchLearningAcademyApp.class)
@WebAppConfiguration
@IntegrationTest
public class TeacherResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";

    private static final Integer DEFAULT_PIN = 1;
    private static final Integer UPDATED_PIN = 2;
    private static final String DEFAULT_NOTES = "AAAAA";
    private static final String UPDATED_NOTES = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    @Inject
    private TeacherRepository teacherRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTeacherMockMvc;

    private Teacher teacher;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TeacherResource teacherResource = new TeacherResource();
        ReflectionTestUtils.setField(teacherResource, "teacherRepository", teacherRepository);
        this.restTeacherMockMvc = MockMvcBuilders.standaloneSetup(teacherResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        teacher = new Teacher();
        teacher.setFirst_name(DEFAULT_FIRST_NAME);
        teacher.setLast_name(DEFAULT_LAST_NAME);
        teacher.setPin(DEFAULT_PIN);
        teacher.setNotes(DEFAULT_NOTES);
        teacher.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createTeacher() throws Exception {
        int databaseSizeBeforeCreate = teacherRepository.findAll().size();

        // Create the Teacher

        restTeacherMockMvc.perform(post("/api/teachers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teacher)))
                .andExpect(status().isCreated());

        // Validate the Teacher in the database
        List<Teacher> teachers = teacherRepository.findAll();
        assertThat(teachers).hasSize(databaseSizeBeforeCreate + 1);
        Teacher testTeacher = teachers.get(teachers.size() - 1);
        assertThat(testTeacher.getFirst_name()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTeacher.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTeacher.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testTeacher.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testTeacher.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void checkFirst_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = teacherRepository.findAll().size();
        // set the field null
        teacher.setFirst_name(null);

        // Create the Teacher, which fails.

        restTeacherMockMvc.perform(post("/api/teachers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teacher)))
                .andExpect(status().isBadRequest());

        List<Teacher> teachers = teacherRepository.findAll();
        assertThat(teachers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = teacherRepository.findAll().size();
        // set the field null
        teacher.setLast_name(null);

        // Create the Teacher, which fails.

        restTeacherMockMvc.perform(post("/api/teachers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teacher)))
                .andExpect(status().isBadRequest());

        List<Teacher> teachers = teacherRepository.findAll();
        assertThat(teachers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPinIsRequired() throws Exception {
        int databaseSizeBeforeTest = teacherRepository.findAll().size();
        // set the field null
        teacher.setPin(null);

        // Create the Teacher, which fails.

        restTeacherMockMvc.perform(post("/api/teachers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teacher)))
                .andExpect(status().isBadRequest());

        List<Teacher> teachers = teacherRepository.findAll();
        assertThat(teachers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = teacherRepository.findAll().size();
        // set the field null
        teacher.setEmail(null);

        // Create the Teacher, which fails.

        restTeacherMockMvc.perform(post("/api/teachers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teacher)))
                .andExpect(status().isBadRequest());

        List<Teacher> teachers = teacherRepository.findAll();
        assertThat(teachers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTeachers() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teachers
        restTeacherMockMvc.perform(get("/api/teachers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(teacher.getId().intValue())))
                .andExpect(jsonPath("$.[*].first_name").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getTeacher() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", teacher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(teacher.getId().intValue()))
            .andExpect(jsonPath("$.first_name").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTeacher() throws Exception {
        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeacher() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);
        int databaseSizeBeforeUpdate = teacherRepository.findAll().size();

        // Update the teacher
        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setId(teacher.getId());
        updatedTeacher.setFirst_name(UPDATED_FIRST_NAME);
        updatedTeacher.setLast_name(UPDATED_LAST_NAME);
        updatedTeacher.setPin(UPDATED_PIN);
        updatedTeacher.setNotes(UPDATED_NOTES);
        updatedTeacher.setEmail(UPDATED_EMAIL);

        restTeacherMockMvc.perform(put("/api/teachers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTeacher)))
                .andExpect(status().isOk());

        // Validate the Teacher in the database
        List<Teacher> teachers = teacherRepository.findAll();
        assertThat(teachers).hasSize(databaseSizeBeforeUpdate);
        Teacher testTeacher = teachers.get(teachers.size() - 1);
        assertThat(testTeacher.getFirst_name()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTeacher.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTeacher.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testTeacher.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testTeacher.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteTeacher() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);
        int databaseSizeBeforeDelete = teacherRepository.findAll().size();

        // Get the teacher
        restTeacherMockMvc.perform(delete("/api/teachers/{id}", teacher.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Teacher> teachers = teacherRepository.findAll();
        assertThat(teachers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
