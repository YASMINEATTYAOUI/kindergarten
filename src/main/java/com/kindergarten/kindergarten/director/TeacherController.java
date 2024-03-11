package com.kindergarten.kindergarten.director;

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
public class TeacherController {
    @Autowired
    TeacherRepo repo;
    @Autowired
    CompteRepo cptrepo;
    @Autowired
    DirectorRepo directeurrepo;

    @GetMapping("/director/teacher")
    public String home(Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = directeurrepo.findById(email).get();
                List<Teacher> teachers = (List<Teacher>) repo.findByDirector(directeur);
                model.addAttribute("currentuser", currentuser);
                model.addAttribute("teachers", teachers);
                model.addAttribute("directeur", directeur);

                return "/director/teachers/index";
            }
        }
        return "/error/accessDenied";
    }

    @GetMapping("/director/teachers/new")
    public String teacherAdd(Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = directeurrepo.findById(email).get();
                Teacher teacher = new Teacher();
                model.addAttribute("currentuser", currentuser);
                model.addAttribute("teacher", teacher);
                model.addAttribute("directeur", directeur);
                return "/director/teachers/teacherForm";
            }
        }
        return "/error/accessDenied";
    }

    @PostMapping("/director/teachers/save")
    public String teacherSave(Principal principal, Teacher teacher) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = directeurrepo.findById(email).get();
                teacher.setDirector(directeur);
                repo.save(teacher);
                return "redirect:/director/teacher";
            }
        }
        return "/error/erreur";

    }

    @GetMapping("/director/teachers/edit/{id}")
    public String teacherEdit(@PathVariable("id") Integer id, Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = directeurrepo.findById(email).get();
                Teacher teacher = repo.findById(id).get();
                model.addAttribute("currentuser", currentuser);
                model.addAttribute("teacher", teacher);
                model.addAttribute("directeur", directeur);
                return "/director/teachers/teacherForm";
            }
        }
        return "/error/accessDenied";
    }

    @GetMapping("/director/teachers/delete/{id}")
    public String teacherDelete(@PathVariable("id") Integer id) {
        repo.deleteById(id);
        return "redirect:/director/teacher";
    }

}