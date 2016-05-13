package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.HopeRanchLearningAcademyApp;
import com.mycompany.myapp.domain.Classroom;
import com.mycompany.myapp.repository.ClassroomRepository;

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
 * Test class for the ClassroomResource REST controller.
 *
 * @see ClassroomResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HopeRanchLearningAcademyApp.class)
@WebAppConfiguration
@IntegrationTest
public class ClassroomResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";
    private static final String DEFAULT_CAMPUS = "AAAAA";
    private static final String UPDATED_CAMPUS = "BBBBB";

    @Inject
    private ClassroomRepository classroomRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClassroomMockMvc;

    private Classroom classroom;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassroomResource classroomResource = new ClassroomResource();
        ReflectionTestUtils.setField(classroomResource, "classroomRepository", classroomRepository);
        this.restClassroomMockMvc = MockMvcBuilders.standaloneSetup(classroomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        classroom = new Classroom();
        classroom.setName(DEFAULT_NAME);
        classroom.setType(DEFAULT_TYPE);
        classroom.setCampus(DEFAULT_CAMPUS);
    }

    @Test
    @Transactional
    public void createClassroom() throws Exception {
        int databaseSizeBeforeCreate = classroomRepository.findAll().size();

        // Create the Classroom

        restClassroomMockMvc.perform(post("/api/classrooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classroom)))
                .andExpect(status().isCreated());

        // Validate the Classroom in the database
        List<Classroom> classrooms = classroomRepository.findAll();
        assertThat(classrooms).hasSize(databaseSizeBeforeCreate + 1);
        Classroom testClassroom = classrooms.get(classrooms.size() - 1);
        assertThat(testClassroom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassroom.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testClassroom.getCampus()).isEqualTo(DEFAULT_CAMPUS);
    }

    @Test
    @Transactional
    public void getAllClassrooms() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        // Get all the classrooms
        restClassroomMockMvc.perform(get("/api/classrooms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(classroom.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].campus").value(hasItem(DEFAULT_CAMPUS.toString())));
    }

    @Test
    @Transactional
    public void getClassroom() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        // Get the classroom
        restClassroomMockMvc.perform(get("/api/classrooms/{id}", classroom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(classroom.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.campus").value(DEFAULT_CAMPUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassroom() throws Exception {
        // Get the classroom
        restClassroomMockMvc.perform(get("/api/classrooms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassroom() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);
        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();

        // Update the classroom
        Classroom updatedClassroom = new Classroom();
        updatedClassroom.setId(classroom.getId());
        updatedClassroom.setName(UPDATED_NAME);
        updatedClassroom.setType(UPDATED_TYPE);
        updatedClassroom.setCampus(UPDATED_CAMPUS);

        restClassroomMockMvc.perform(put("/api/classrooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedClassroom)))
                .andExpect(status().isOk());

        // Validate the Classroom in the database
        List<Classroom> classrooms = classroomRepository.findAll();
        assertThat(classrooms).hasSize(databaseSizeBeforeUpdate);
        Classroom testClassroom = classrooms.get(classrooms.size() - 1);
        assertThat(testClassroom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassroom.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClassroom.getCampus()).isEqualTo(UPDATED_CAMPUS);
    }

    @Test
    @Transactional
    public void deleteClassroom() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);
        int databaseSizeBeforeDelete = classroomRepository.findAll().size();

        // Get the classroom
        restClassroomMockMvc.perform(delete("/api/classrooms/{id}", classroom.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Classroom> classrooms = classroomRepository.findAll();
        assertThat(classrooms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
