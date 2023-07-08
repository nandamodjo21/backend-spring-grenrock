package com.greenrock.backendgreenrock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_role")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_role", columnDefinition = "int")
    private Integer id;

    @Column(name = "role", length = 100)
    private String role;

    @OneToMany(mappedBy = "role" ,cascade = CascadeType.ALL)
    private List<User> users;
}
