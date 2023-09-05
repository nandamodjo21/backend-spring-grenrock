package com.greenrock.backendgreenrock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
@Transactional
@RequestMapping("/lihatsemua")
public class ListController {

    @Autowired
    EntityManager em;

    @GetMapping("/lihat/{nik}")
    public ResponseEntity<Object> view(@PathVariable("nik") String nik) {
        List<Object> cekData = em.createNativeQuery(
                "SELECT p.nama_barang,p.stok,CONCAT(p.lama_sewa,' Hari'),DATE_FORMAT(p.tgl_sewa, '%d-%m-%Y') AS tgl_sewa,CONCAT('Rp. ', FORMAT(t.total_bayar, 0)) AS formatted_total_bayar, p.status,t.id_penyewa FROM t_total t, `t_penyewaan` p, tbl_user u WHERE t.id_penyewa=p.id_penyewa AND p.user_id=u.id_user AND u.nik=:nik AND date(p.tgl_sewa) = date(now())")
                .setParameter("nik", nik)
                .getResultList();

        JSONObject res = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Object sewa : cekData) {
            Object[] sewaArrayObj = (Object[]) sewa;
            JSONObject js = new JSONObject();
            js.put("nama_barang", sewaArrayObj[0]);
            js.put("stok", sewaArrayObj[1]);
            js.put("lama_sewa", sewaArrayObj[2]);
            js.put("tgl_sewa", sewaArrayObj[3]);
            js.put("total_bayar", sewaArrayObj[4]);
            js.put("status", sewaArrayObj[5]);
            js.put("id_penyewa", sewaArrayObj[6]);

            jsonArray.add(js);

        }
        res.put("data", jsonArray);
        res.put("code", 1);
        return ResponseEntity.status(HttpStatus.OK).body(res.toJSONString());

    }

    @GetMapping("/total/{id_penyewa}")
    public ResponseEntity<Object> lihat(@PathVariable("id_penyewa") String id_penyewa) {
        List<Object> cek = em.createNativeQuery(
                "SELECT t.id_total,CONCAT('Rp. ', FORMAT(t.total_bayar, 0)) AS formatted_total_bayar FROM `t_total` t, t_penyewaan p WHERE p.id_penyewa=t.id_penyewa AND p.id_penyewa=:id_penyewa")
                .setParameter("id_penyewa", id_penyewa)
                .getResultList();
        JSONObject js = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        for (Object object : cek) {
            Object[] totalObject = (Object[]) object;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_total", totalObject[0]);
            jsonObject.put("total_bayar", totalObject[1]);
            jsonArray.add(jsonObject);
        }

        js.put("data", jsonArray);
        js.put("code", 1);
        return new ResponseEntity<Object>(js, HttpStatus.OK);

    }

}
