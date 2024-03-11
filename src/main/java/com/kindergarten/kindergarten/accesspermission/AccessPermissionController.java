package com.kindergarten.kindergarten.accesspermission;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kindergarten.kindergarten.compte.Compte;
import com.kindergarten.kindergarten.compte.CompteRepo;

@Controller
public class AccessPermissionController {
    @Autowired
    private AccessPermissionRepo repo;

    @Autowired
    private CompteRepo cptrepo;

    @GetMapping("/accesspermission")
    public String showlist(Principal principal, Model m) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
        }
        List<AccessPermission> listpermission = (List<AccessPermission>) repo.findAll();
        m.addAttribute("currentuser", currentuser);
        m.addAttribute("listpermission", listpermission);
        return "/accesspermission/index";
    }

    @GetMapping("/accesspermission/add")
    public String addpermission(Principal principal, Model m) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
        }
        AccessPermission ap = new AccessPermission();
        m.addAttribute("currentuser", currentuser);
        m.addAttribute("ap", ap);

        return "/accesspermission/formpermission";
    }

    @PostMapping("/accesspermission/save")
    public String saveAp(AccessPermission ap) {
        repo.save(ap);
        return "redirect:/accesspermission";
    }

    @GetMapping("/accesspermission/edit/{id}")
    public String editpermission(@PathVariable("id") String id, Principal principal, Model m) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
        }
        AccessPermission ap = repo.findById(id).get();
        m.addAttribute("currentuser", currentuser);
        m.addAttribute("ap", ap);
        return "/accesspermission/formpermission";
    }

    @GetMapping("/accesspermission/delete/{id}")
    public String deletepermission(@PathVariable("id") String id, Model m) {
        repo.deleteById(id);
        return "redirect:/accesspermission";
    }

}
