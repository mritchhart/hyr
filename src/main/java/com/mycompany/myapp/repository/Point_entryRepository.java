package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Point_entry;

import org.springframework.data.jpa.repository.*;

import org.springframework.data.repository.query.Param;


import java.util.List;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Spring Data JPA repository for the Point_entry entity.
 */
public interface Point_entryRepository extends JpaRepository<Point_entry,Long> {


    @Query("SELECT p FROM Point_entry p " +
        "WHERE p.student.id = :studentId")
    List<Point_entry> ooga(@Param("studentId")Long studentId);

    @Query("SELECT p FROM Point_entry p, Social_skill s " +
        "WHERE p.student.id = :studentId AND s.id = :skillId")
    List<Point_entry> findAllByStudentIdAndSkillId(@Param("studentId")Long studentId, @Param("skillId")Long skillId);

    @Query("SELECT SUM(p.ent_value) FROM Point_entry p, Social_skill s " +
        "WHERE p.student.id = :studentId AND s.id = :skillId")
    Integer countSumByStudentIdAndSkillId(@Param("studentId")Long studentId, @Param("skillId")Long skillId);

    @Query("SELECT SUM(p.ent_value), s.name, s.id, t.first_name FROM Point_entry p, Social_skill s, Student t " +
        "WHERE p.student.id = :studentId AND t.id = :studentId AND p.social_skill.id = :skillId AND s.id = :skillId GROUP BY s.name, t.first_name")
    List findSkillAndPointsByStudentIdAndSkillId(@Param("studentId")Long studentId, @Param("skillId")Long skillId);

    @Query("SELECT SUM(p.ent_value), s.name FROM Point_entry p, Social_skill s " +
        "WHERE p.student.id = :studentId AND s.id = p.social_skill.id GROUP BY s.name, p.ent_value ")
    List findAllSkillsAndPointsByStudentIdAndSkillId(@Param("studentId")Long studentId);

    @Query("SELECT SUM(p.ent_value), s.name, s.id, t.first_name FROM Point_entry p, Social_skill s, Student t " +
        "WHERE p.student.id = :studentId AND t.id = :studentId AND p.social_skill.id = :skillId AND s.id = :skillId " +
        "AND p.ent_submission_time > :currentDateMinusTwelveHours AND p.ent_submission_time <= :currentDate " +
        "GROUP BY s.name, t.first_name")
    List findSkillAndPointsDailyByStudentIdAndSkillId(@Param("studentId")Long studentId, @Param("skillId")Long skillId, @Param("currentDateMinusTwelveHours")ZonedDateTime currentDateMinusTwelveHours, @Param("currentDate")ZonedDateTime currentDate);

    @Query("SELECT SUM(p.ent_value), s.name, s.id, t.first_name FROM Point_entry p, Social_skill s, Student t " +
        "WHERE p.student.id = :studentId AND t.id = :studentId AND p.social_skill.id = :skillId AND s.id = :skillId " +
        "AND p.ent_submission_time >= :startDate AND p.ent_submission_time <= :endDate " +
        "GROUP BY s.name, t.first_name")
    List findSkillAndPointsCustomByStudentIdAndSkillId(@Param("studentId")Long studentId, @Param("skillId")Long skillId, @Param("startDate")ZonedDateTime startDate, @Param("endDate")ZonedDateTime endDate);


}
