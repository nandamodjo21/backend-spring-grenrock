package com.greenrock.backendgreenrock.controller;

import com.greenrock.backendgreenrock.dto.LoginDto;
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
@RequestMapping("/login")
public class Login {

    @Autowired
    EntityManager em;

    @PostMapping
    public Object Login(@RequestBody LoginDto loginDto){
        List cekUser = em
                .createNativeQuery(
                        "SELECT u.id_user,u.email,r.role FROM tbl_user u, tbl_role r WHERE u.role_id=r.id_role and email = :email AND password = MD5(:password)")
                .setParameter("email", loginDto.getEmail())
                .setParameter("password", loginDto.getPassword())
                .getResultList();


        JSONObject res = new JSONObject();

        if (cekUser.isEmpty()){
            res.put("code",0);
            res.put("message","user belum terdaftar");
            return res;
        } else {
            Object o2[] =(Object[]) cekUser.get(0);
            List user = em.createNativeQuery("select nama_lengkap from tbl_user where id_user= :id_user")
                    .setParameter("id_user",o2[0])
                    .getResultList();

            String o1 = (String)  user.get(0);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_user",o2[0]);
            jsonObject.put("email",o2[1]);
            jsonObject.put("role",o2[2]);
            jsonObject.put("nama_lengkap",o1);
            res.put("code",1);
            res.put("message","berhasil Login");
            res.put("data",jsonObject);
            System.out.println(jsonObject);
            return res;
        }
    }
}
