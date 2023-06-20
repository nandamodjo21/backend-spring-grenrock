package com.greenrock.backendgreenrock.entity;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;



@Entity
@Table(name = "tbl_user")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_user")
    private String id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "nama_lengkap")
    private String nama;

    @Column(name = "email")
    private String email;

    @Column(name = "no_hp")
    private String noHp;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "password")
    private String password;

    @Column(name = "nik")
    private String nik;

    @CreationTimestamp
    @Column(name = "date_created", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Penyewa> penyewas;











}
