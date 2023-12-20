package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "work_email")
    private String workemail;
    @Column(name = "phone_number")
    private String phonenumber;
    @Column(name = "company_name")
    private String companyname;
    @Column(name = "company_size")
    private Integer companysize;
    @Column(name = "role")
    private String role;
    @Column(name = "job_level")
    private String joblevel;
    @Column(name = "discuss")
    private String discuss;
    @Column(name = "contacted")
    private boolean contacted;
}
