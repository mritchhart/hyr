package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.HopeRanchLearningAcademyApp;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.repository.StudentRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StudentResource REST controller.
 *
 * @see StudentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HopeRanchLearningAcademyApp.class)
@WebAppConfiguration
@IntegrationTest
public class StudentResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_GRADE_LEVEL = "AAAAA";
    private static final String UPDATED_GRADE_LEVEL = "BBBBB";
    private static final String DEFAULT_GOAL = "AAAAA";
    private static final String UPDATED_GOAL = "BBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_TOTAL_POINTS = 1;
    private static final Integer UPDATED_TOTAL_POINTS = 2;

    private static final Integer DEFAULT_REWARD_POINTS = 1;
    private static final Integer UPDATED_REWARD_POINTS = 2;
    private static final String DEFAULT_STU_GROUP = "AAAAA";
    private static final String UPDATED_STU_GROUP = "BBBBB";

    @Inject
    private StudentRepository studentRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStudentMockMvc;

    private Student student;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudentResource studentResource = new StudentResource();
        ReflectionTestUtils.setField(studentResource, "studentRepository", studentRepository);
        this.restStudentMockMvc = MockMvcBuilders.standaloneSetup(studentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        student = new Student();
        student.setFirst_name(DEFAULT_FIRST_NAME);
        student.setLast_name(DEFAULT_LAST_NAME);
        student.setGrade_level(DEFAULT_GRADE_LEVEL);
        student.setGoal(DEFAULT_GOAL);
        student.setPhoto(DEFAULT_PHOTO);
        student.setPhotoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        student.setTotal_points(DEFAULT_TOTAL_POINTS);
        student.setReward_points(DEFAULT_REWARD_POINTS);
        student.setStu_group(DEFAULT_STU_GROUP);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student

        restStudentMockMvc.perform(post("/api/students")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(student)))
                .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = students.get(students.size() - 1);
        assertThat(testStudent.getFirst_name()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStudent.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testStudent.getGrade_level()).isEqualTo(DEFAULT_GRADE_LEVEL);
        assertThat(testStudent.getGoal()).isEqualTo(DEFAULT_GOAL);
        assertThat(testStudent.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testStudent.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testStudent.getTotal_points()).isEqualTo(DEFAULT_TOTAL_POINTS);
        assertThat(testStudent.getReward_points()).isEqualTo(DEFAULT_REWARD_POINTS);
        assertThat(testStudent.getStu_group()).isEqualTo(DEFAULT_STU_GROUP);
    }

    @Test
    @Transactional
    public void checkFirst_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setFirst_name(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(student)))
                .andExpect(status().isBadRequest());

        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setLast_name(null);

        // Create the Student, which fails.

        restStudentMockMvc.perform(post("/api/students")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(student)))
                .andExpect(status().isBadRequest());

        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the students
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
                .andExpect(jsonPath("$.[*].first_name").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].grade_level").value(hasItem(DEFAULT_GRADE_LEVEL.toString())))
                .andExpect(jsonPath("$.[*].goal").value(hasItem(DEFAULT_GOAL.toString())))
                .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
                .andExpect(jsonPath("$.[*].total_points").value(hasItem(DEFAULT_TOTAL_POINTS)))
                .andExpect(jsonPath("$.[*].reward_points").value(hasItem(DEFAULT_REWARD_POINTS)))
                .andExpect(jsonPath("$.[*].stu_group").value(hasItem(DEFAULT_STU_GROUP.toString())));
    }

    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.first_name").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.grade_level").value(DEFAULT_GRADE_LEVEL.toString()))
            .andExpect(jsonPath("$.goal").value(DEFAULT_GOAL.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.total_points").value(DEFAULT_TOTAL_POINTS))
            .andExpect(jsonPath("$.reward_points").value(DEFAULT_REWARD_POINTS))
            .andExpect(jsonPath("$.stu_group").value(DEFAULT_STU_GROUP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = new Student();
        updatedStudent.setId(student.getId());
        updatedStudent.setFirst_name(UPDATED_FIRST_NAME);
        updatedStudent.setLast_name(UPDATED_LAST_NAME);
        updatedStudent.setGrade_level(UPDATED_GRADE_LEVEL);
        updatedStudent.setGoal(UPDATED_GOAL);
        updatedStudent.setPhoto(UPDATED_PHOTO);
        updatedStudent.setPhotoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        updatedStudent.setTotal_points(UPDATED_TOTAL_POINTS);
        updatedStudent.setReward_points(UPDATED_REWARD_POINTS);
        updatedStudent.setStu_group(UPDATED_STU_GROUP);

        restStudentMockMvc.perform(put("/api/students")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
                .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = students.get(students.size() - 1);
        assertThat(testStudent.getFirst_name()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStudent.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testStudent.getGrade_level()).isEqualTo(UPDATED_GRADE_LEVEL);
        assertThat(testStudent.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testStudent.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testStudent.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testStudent.getTotal_points()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testStudent.getReward_points()).isEqualTo(UPDATED_REWARD_POINTS);
        assertThat(testStudent.getStu_group()).isEqualTo(UPDATED_STU_GROUP);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);
        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Get the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(databaseSizeBeforeDelete - 1);
    }
}
