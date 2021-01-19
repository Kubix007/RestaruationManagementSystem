package com.example.zpo_projekt.controller;


import com.example.zpo_projekt.model.AppUser;
import com.example.zpo_projekt.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppUserController {

    @Autowired
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/api/appusers")
    public List<AppUser> getAll(){
        return appUserService.getAll();
    }

    @GetMapping("/api/appusers/{username}")
    public AppUser getSingleAppUser(@PathVariable String username){
        return appUserService.getSingleAppUser(username);
    }

    @PostMapping("/api/appusers")
    public AppUser createAppUser(@RequestBody AppUser appUser){
        return appUserService.createAppUser(appUser);
    }

    @RequestMapping(value = "/api/appusers", produces = "application/json", method=RequestMethod.PUT)
    public AppUser updateAppUser(@RequestBody AppUser appUser,@RequestParam("name") String name, @RequestParam("surname") String surname){
        return appUserService.updateAppUser(appUser,name,surname);
    }

    @DeleteMapping("/api/appusers/{username}")
    public void deleteAppUser(@PathVariable Long id){
        appUserService.deleteAppUser(id);
    }
}
