package com.greenrock.backendgreenrock.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


import java.time.LocalDateTime;


@Entity
@Table(name = "t_penyewaan")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Penyewa {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_penyewa")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "nama_barang")
    private String namaBarang;

    @Column(name = "lama_sewa")
    private String lamaSewa;

    @Column(name = "tgl_sewa")
    private LocalDateTime tglSewa;

    @Column(name = "Tgl_kembali")
    private String tglKembali;

    @Column(name = "status")
    private Integer status = 0;


}
