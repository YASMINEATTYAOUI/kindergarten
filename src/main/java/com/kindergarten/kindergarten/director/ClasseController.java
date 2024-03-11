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
public class ClasseController {
    @Autowired
    private ClasseRepo repo;
    @Autowired
    private DirectorRepo drepo;
    @Autowired
    private KinderGartenRepo krepo;
    @Autowired
    private ActivitiesRepo arepo;
    @Autowired
    private TeacherRepo trepo;
    @Autowired
    private CompteRepo cptrepo;

    @GetMapping("/director/class")
    public String home(Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = drepo.findById(email).get();
                List<Classe> classes = repo.findByDirecteurOrderByDirecteur(directeur);
                model.addAttribute("classes", classes);
                model.addAttribute("directeur", directeur);
                model.addAttribute("currentuser", currentuser);

                return "/director/classe/index";
            }
        }
        return "/error/accessDenied";
    }

    @GetMapping("/director/classe/new")
    public String addClass(Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = drepo.findById(email).get();
                Classe classe = new Classe();
                List<KinderGarten> kindergartens = (List<KinderGarten>) krepo.findByDirector(directeur);
                List<Teacher> teachers = (List<Teacher>) trepo.findByDirector(directeur);
                List<Activities> activities = (List<Activities>) arepo.findByDirecteurOrderByDirecteur(directeur);
                model.addAttribute("classe", classe);
                model.addAttribute("directeur", directeur);
                model.addAttribute("kindergartens", kindergartens);
                model.addAttribute("teachers", teachers);
                model.addAttribute("activities", activities);
                model.addAttribute("currentuser", currentuser);
                return "/director/classe/formClass";
            }
        }
        return "/error/accessDenied";
    }

    @PostMapping("/director/classe/save")
    public String saveClasse(Principal principal, Classe classe) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = drepo.findById(email).get();
                classe.setDirecteur(directeur);
                System.out.println(classe.getKindergarten().getNom());
                System.out.println(classe.getDirecteur().getNom());
                for (Teacher t : classe.getTeachers()) {
                    System.out.println(t.getId());
                }
                for (Activities a : classe.getActivities()) {
                    System.out.println(a.getId());
                }
                repo.save(classe);
                return "redirect:/director/class";
            }
        }
        return "/error/accessDenied";
    }

    @GetMapping("/director/classe/edit/{id}")
    public String editClass(@PathVariable("id") Integer id, Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director directeur = drepo.findById(email).get();
                Classe classe = repo.findById(id).get();
                List<KinderGarten> kindergartens = (List<KinderGarten>) krepo.findByDirector(directeur);
                List<Teacher> teachers = (List<Teacher>) trepo.findByDirector(directeur);
                List<Activities> activities = (List<Activities>) arepo.findByDirecteurOrderByDirecteur(directeur);
                model.addAttribute("classe", classe);
                model.addAttribute("directeur", directeur);
                model.addAttribute("kindergartens", kindergartens);
                model.addAttribute("teachers", teachers);
                model.addAttribute("activities", activities);
                model.addAttribute("currentuser", currentuser);
                return "/director/classe/formClass";
            }
        }
        return "/error/accessDenied";
    }

    @GetMapping("/director/classe/delete/{id}")
    public String deleteClasse(@PathVariable("id") Integer id, Principal principal) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
            if (currentuser.getType().equals("Kindergarten Director")) {
                repo.deleteById(id);
                return "redirect:/director/class";
            }
        }
        return "/error/accessDenied";

    }

}
