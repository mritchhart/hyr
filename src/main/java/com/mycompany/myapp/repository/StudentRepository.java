package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Student;

import org.springframework.data.jpa.repository.*;

import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Student entity.
 */
public interface StudentRepository extends JpaRepository<Student,Long> {

//    @Query("SELECT s FROM Student s" +
//            "WHERE s.classroom.id = :classroomId AND s.stu_group = :groupNum")
//    List<Student> findAllByClassroomIdAndGroupId(@Param("classroomId")Long classroomId, @Param("groupNum")Long groupNum);

}
