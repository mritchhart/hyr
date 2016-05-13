package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Student_social_skill;

import org.springframework.data.jpa.repository.*;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.ArrayList;

/**
 * Spring Data JPA repository for the Student_social_skill entity.
 */
public interface Student_social_skillRepository extends JpaRepository<Student_social_skill,Long> {

    @Query("SELECT s.social_skill.id, s.social_skill.name, s.id, s.social_skill.description, s.status, s.social_skill.level FROM Student_social_skill s " +
        "WHERE s.student.id = :studentId")
    ArrayList findAllSkillIDsByStudentId(@Param("studentId")Long studentId);

    @Query("SELECT s FROM Student_social_skill s " +
        "WHERE s.student.id = :studentId AND s.social_skill.id = :skillId")
    List findSSSByStudentIdAndSkillId(@Param("studentId")Long studentId, @Param("skillId")Long skillId);

}
