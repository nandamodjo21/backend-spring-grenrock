package com.greenrock.backendgreenrock.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "t_penyewa")

public class Sewa {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_penyewa")
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")

    private Penyewa penyewa;




}
