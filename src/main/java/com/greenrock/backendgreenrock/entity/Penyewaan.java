package com.greenrock.backendgreenrock.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_penyewaan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Penyewaan {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id_penyewa")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "nama_barang", referencedColumnName = "nama_barang")
    private Barang barang;

    @Column(name = "stok")
    private int stok;

    @Column(name = "lama_sewa")
    private Integer lamaSewa;

    @Column(name = "tgl_sewa")
    private Timestamp tglSewa;

    @Column(name = "status")
    private int status;

    @Column(name = "total")
    private Integer total;

    @Column(name = "image_path")
    private String imagePath;

}
