package com.greenrock.backendgreenrock.controller;

import com.greenrock.backendgreenrock.dto.SewaDto;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/sewa")
public class SewaController {
    @Autowired
    EntityManager em;

    @PostMapping("/add")
    public Object save(@RequestBody SewaDto sewaDto){
        em.createNativeQuery("INSERT INTO `t_penyewaan` (`id_penyewa`, `user_id`, `nama_barang`, `lama_sewa`, `tgl_sewa`, `Tgl_kembali`, `status`) VALUES (UUID(), :id_user, :nama_barang, :lama_sewa, now(), :tgl_kembali, '0');")
                .setParameter("id_user",sewaDto.getUser())
                .setParameter("nama_barang", sewaDto.getBarang())
                .setParameter("lama_sewa", sewaDto.getLamaSewa())
                .setParameter("tgl_kembali",sewaDto.getTglKembali())
                .executeUpdate();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",1);
        jsonObject.put("message", "pesanan anda sedang di proses");
        return jsonObject;
    }

    @GetMapping("/lihat/{id_user}")
    public ResponseEntity<Object> view(@PathVariable("id_user") String id_user){
        List<Object> cekData = em.createNativeQuery("SELECT u.nama_lengkap AS penyewa, p.nama_barang, p.lama_sewa, p.tgl_sewa, p.Tgl_kembali, p.status FROM `t_penyewaan` p, tbl_user u WHERE p.user_id=u.id_user AND date(p.tgl_sewa) = date(now()) AND u.id_user=:id_user")
                .setParameter("id_user",id_user)
                .getResultList();

        JSONObject res = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Object sewa : cekData){
            Object[] sewaArrayObj = (Object[]) sewa;
            JSONObject js = new JSONObject();
             js.put("nama_pemesan",sewaArrayObj[0]);
         js.put("nama_barang",sewaArrayObj[1]);
         js.put("lama_sewa",sewaArrayObj[2]);
         js.put("tgl_sewa",sewaArrayObj[3]);
         js.put("tgl_kembali",sewaArrayObj[4]);
         js.put("status",sewaArrayObj[5]);
         jsonArray.add(js);

        }
        res.put("data",jsonArray);
        res.put("code",1);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}

// js.put("nama_pemesan",o2[0]);
//         js.put("nama_barang",o2[1]);
//         js.put("lama_sewa",o2[2]);
//         js.put("tgl_sewa",o2[3]);
//         js.put("tgl_kembali",o2[4]);
//         js.put("status",o2[5]);
//         res.put("code",1);
//         res.put("data", js);
//         System.out.println(js);
//         return res;
