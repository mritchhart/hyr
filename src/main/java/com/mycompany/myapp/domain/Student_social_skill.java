package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Status;

/**
 * A Student_social_skill.
 */
@Entity
@Table(name = "student_social_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Student_social_skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "gross_pts")
    private Integer gross_pts;

    @Column(name = "net_pts")
    private Integer net_pts;

    @Column(name = "start_date")
    private LocalDate start_date;

    @Column(name = "end_date")
    private LocalDate end_date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    private Social_skill social_skill;

    @ManyToOne
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGross_pts() {
        return gross_pts;
    }

    public void setGross_pts(Integer gross_pts) {
        this.gross_pts = gross_pts;
    }

    public Integer getNet_pts() {
        return net_pts;
    }

    public void setNet_pts(Integer net_pts) {
        this.net_pts = net_pts;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Social_skill getSocial_skill() {
        return social_skill;
    }

    public void setSocial_skill(Social_skill social_skill) {
        this.social_skill = social_skill;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student_social_skill student_social_skill = (Student_social_skill) o;
        if(student_social_skill.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, student_social_skill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Student_social_skill{" +
            "id=" + id +
            ", gross_pts='" + gross_pts + "'" +
            ", net_pts='" + net_pts + "'" +
            ", start_date='" + start_date + "'" +
            ", end_date='" + end_date + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
