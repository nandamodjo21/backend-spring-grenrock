package com.greenrock.backendgreenrock.controller;

import com.greenrock.backendgreenrock.dto.RegisterDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/register")
public class Register {
    @Autowired
    EntityManager em;
    @PostMapping
    public Object Regis(@RequestBody RegisterDto registerDto){
        List cekDatac = em.createNativeQuery("select email,no_hp,nik from tbl_user where email=:email and no_hp=:no_hp and nik=:nik")
                .setParameter("email", registerDto.getEmail())
                .setParameter("no_hp",registerDto.getNoHp())
                .setParameter("nik",registerDto.getNik())
                .getResultList();



        JSONObject res = new JSONObject();

        if (!cekDatac.isEmpty()){
            res.put("code", 0);
            res.put("message", "user sudah terdaftar");
            return res;

        } else {

                em.createNativeQuery("insert into tbl_user(id_user, role_id,nama_lengkap,email, no_hp, alamat, password, nik) values (UUID(), '2', :nama, :email, :nohp, :alamat, MD5(:password), :nik)")
                        .setParameter("nama",registerDto.getNama())
                        .setParameter("email", registerDto.getEmail())
                        .setParameter("nohp",registerDto.getNoHp())
                        .setParameter("alamat",registerDto.getAlamat())
                        .setParameter("password",registerDto.getPassword())
                        .setParameter("nik",registerDto.getNik())
                        .executeUpdate();
                res.put("code",1);
                res.put("message","sukses registrasi");
                return res;

        }
    }
}
