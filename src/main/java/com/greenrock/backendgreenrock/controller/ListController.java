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
                "SELECT p.nama_barang,p.lama_sewa FROM t_penyewaan p, tbl_user u WHERE p.user_id=u.id_user AND u.nik=:nik AND date(p.tgl_sewa) = date(now());")
                .setParameter("nik", nik)
                .getResultList();

        JSONObject res = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Object sewa : cekData) {
            Object[] sewaArrayObj = (Object[]) sewa;
            JSONObject js = new JSONObject();
            js.put("nama_barang", sewaArrayObj[0]);
            js.put("lama_sewa", sewaArrayObj[1]);

            jsonArray.add(js);

        }
        res.put("data", jsonArray);
        res.put("code", 1);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

}
