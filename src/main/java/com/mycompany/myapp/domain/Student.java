package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String first_name;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "grade_level")
    private String grade_level;

    @Column(name = "goal")
    private String goal;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")    
    private String photoContentType;

    @Column(name = "total_points")
    private Integer total_points;

    @Column(name = "reward_points")
    private Integer reward_points;

    @Column(name = "stu_group")
    private String stu_group;

    @ManyToOne
    private Classroom classroom;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Student_social_skill> student_social_skills = new HashSet<>();

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Point_entry> point_entries = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGrade_level() {
        return grade_level;
    }

    public void setGrade_level(String grade_level) {
        this.grade_level = grade_level;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Integer getTotal_points() {
        return total_points;
    }

    public void setTotal_points(Integer total_points) {
        this.total_points = total_points;
    }

    public Integer getReward_points() {
        return reward_points;
    }

    public void setReward_points(Integer reward_points) {
        this.reward_points = reward_points;
    }

    public String getStu_group() {
        return stu_group;
    }

    public void setStu_group(String stu_group) {
        this.stu_group = stu_group;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Set<Student_social_skill> getStudent_social_skills() {
        return student_social_skills;
    }

    public void setStudent_social_skills(Set<Student_social_skill> student_social_skills) {
        this.student_social_skills = student_social_skills;
    }

    public Set<Point_entry> getPoint_entries() {
        return point_entries;
    }

    public void setPoint_entries(Set<Point_entry> point_entries) {
        this.point_entries = point_entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        if(student.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", first_name='" + first_name + "'" +
            ", last_name='" + last_name + "'" +
            ", grade_level='" + grade_level + "'" +
            ", goal='" + goal + "'" +
            ", photo='" + photo + "'" +
            ", photoContentType='" + photoContentType + "'" +
            ", total_points='" + total_points + "'" +
            ", reward_points='" + reward_points + "'" +
            ", stu_group='" + stu_group + "'" +
            '}';
    }
}
