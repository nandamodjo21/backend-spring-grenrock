package com.greenrock.backendgreenrock.controller;

import com.greenrock.backendgreenrock.dto.SewaDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/sewa")
public class SewaController {
    @Autowired
    EntityManager em;

    @PostMapping("/add")
    public Object save(@RequestBody SewaDto sewaDto) {
        em.createNativeQuery(
                "INSERT INTO `t_penyewaan` (`id_penyewa`, `user_id`, `nama_barang`, `stok`, `lama_sewa`, `tgl_sewa`, `status`, `total`) VALUES (UUID(), :id_user, :nama_barang, :stok, :lama_sewa, CURRENT_TIMESTAMP, '0', NULL);")
                .setParameter("id_user", sewaDto.getUser())
                .setParameter("nama_barang", sewaDto.getBarang())
                .setParameter("stok", sewaDto.getStok())
                .setParameter("lama_sewa", sewaDto.getLamaSewa())
                .executeUpdate();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        jsonObject.put("message", "pesanan anda sedang di proses");
        return jsonObject;
    }

    @GetMapping("/lihat/{id_user}")
    public ResponseEntity<Object> view(@PathVariable("id_user") String id_user) {
        List<Object> cekData = em.createNativeQuery(
                "SELECT p.nama_barang,p.stok,p.lama_sewa,DATE_FORMAT(p.tgl_sewa, '%d-%m-%Y') AS tgl_sewa,p.status,p.user_id FROM t_total t, `t_penyewaan` p WHERE t.id_penyewa=p.id_penyewa AND p.user_id=:id_user ORDER BY t.id_total DESC LIMIT 1;")
                .setParameter("id_user", id_user)
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
            js.put("status", sewaArrayObj[4]);
            js.put("id_user", sewaArrayObj[5]);
            jsonArray.add(js);

        }
        res.put("data", jsonArray);
        res.put("code", 1);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/barang")
    public ResponseEntity<Object> barang() {
        List<Object> cekData = em.createNativeQuery(
                "SELECT id, nama_barang from t_barang where stok_barang >= 1")
                .getResultList();

        JSONObject res = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Object sewa : cekData) {
            Object[] sewaArrayObj = (Object[]) sewa;
            JSONObject js = new JSONObject();
            js.put("id", sewaArrayObj[0]);
            js.put("nama_barang", sewaArrayObj[1]);
            jsonArray.add(js);
        }
        res.put("data", jsonArray);
        res.put("code", 1);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}

// js.put("nama_pemesan",o2[0]);
// js.put("nama_barang",o2[1]);
// js.put("lama_sewa",o2[2]);
// js.put("tgl_sewa",o2[3]);
// js.put("tgl_kembali",o2[4]);
// js.put("status",o2[5]);
// res.put("code",1);
// res.put("data", js);
// System.out.println(js);
// return res;
