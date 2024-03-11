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
import com.kindergarten.kindergarten.kindergarten.KinderGarten;
import com.kindergarten.kindergarten.kindergarten.KinderGartenRepo;

@Controller
public class ActivitiesController {
    @Autowired
    ActivitiesRepo repo;
    @Autowired
    CompteRepo cptrepo;
    @Autowired
    DirectorRepo directeurrepo;
    @Autowired
    KinderGartenRepo krepo;

    @GetMapping("/director/activities")
    public String home(Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = directeurrepo.findById(email).get();
                List<Activities> activities = (List<Activities>) repo.findByDirecteurOrderByDirecteur(directeur);
                model.addAttribute("activities", activities);
                model.addAttribute("directeur", directeur);
                model.addAttribute("currentuser", currentuser);

                return "/director/activities/index";
            }
        }
        return "/error/accessDenied";
    }

    @GetMapping("/director/activities/new")
    public String addActivity(Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = directeurrepo.findById(email).get();
                Activities activity = new Activities();
                List<KinderGarten> kindergartens = (List<KinderGarten>) krepo.findByDirector(directeur);
                model.addAttribute("activity", activity);
                model.addAttribute("directeur", directeur);
                model.addAttribute("kindergartens", kindergartens);
                model.addAttribute("currentuser", currentuser);
                return "/director/activities/formActivity";
            }
        }
        return "/error/accessDenied";
    }

    @PostMapping("/director/activities/save")
    public String saveActivity(Principal principal, Activities activity) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = directeurrepo.findById(email).get();
                activity.setDirecteur(directeur);
                repo.save(activity);
                return "redirect:/director/activities";
            }
        }
        return "/error/accessDenied";
    }

    @GetMapping("/director/activities/edit/{id}")
    public String editActivity(@PathVariable("id") Integer id, Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = directeurrepo.findById(email).get();
                Activities activity = repo.findById(id).get();
                List<KinderGarten> kindergartens = (List<KinderGarten>) krepo.findByDirector(directeur);
                model.addAttribute("activity", activity);
                model.addAttribute("directeur", directeur);
                model.addAttribute("kindergartens", kindergartens);
                model.addAttribute("currentuser", currentuser);
                return "/director/activities/formActivity";
            }
        }
        return "/error/accessDenied";
    }

    @GetMapping("/director/activities/delete/{id}")
    public String deleteActivity(@PathVariable("id") Integer id, Principal principal) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                repo.deleteById(id);
                return "redirect:/director/activities";
            }
        }
        return "/error/accessDenied";

    }

}
