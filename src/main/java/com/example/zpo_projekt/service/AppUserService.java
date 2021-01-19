package com.example.zpo_projekt.service;


import com.example.zpo_projekt.model.AppUser;
import com.example.zpo_projekt.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public List<AppUser> getAll(){
        return appUserRepository.findAll();
    }

    public AppUser getSingleAppUser(String username) {
        return appUserRepository.findByUsername(username);
    }

    public AppUser createAppUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Transactional
    public AppUser updateAppUser(AppUser appUser, String name, String surname) {
        AppUser appUserEdited = appUserRepository.findByUsername(appUser.getUsername());
        appUserEdited.setName(name);
        appUserEdited.setSurname(surname);
        return appUserEdited;
    }

    public void deleteAppUser(Long id) {

        appUserRepository.deleteById(id);
    }

}

