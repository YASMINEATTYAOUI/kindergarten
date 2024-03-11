package com.kindergarten.kindergarten.compte;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kindergarten.kindergarten.accesspermission.AccessPermission;
import com.kindergarten.kindergarten.accesspermission.AccessPermissionRepo;
import com.kindergarten.kindergarten.director.Director;
import com.kindergarten.kindergarten.director.DirectorRepo;
import com.kindergarten.kindergarten.parent.Parent;
import com.kindergarten.kindergarten.parent.ParentRepo;
import com.kindergarten.kindergarten.stream.StreamKeys;
import com.kindergarten.kindergarten.stream.StreamKeysRepo;

@Controller
public class CompteController {
    @Autowired
    private CompteRepo repo;
    @Autowired
    private AccessPermissionRepo accessrepo;
    @Autowired
    private ParentRepo parentrepo;

    @Autowired
    private DirectorRepo directorrepo;

    @Autowired
    private CompteRepo cptrepo;

    @Autowired
    private StreamKeysRepo stkrepo;

    @GetMapping("/compte")
    public String listComptes(Principal principal, Model m) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
        }
        List<Compte> listcomptes = (List<Compte>) repo.findAll();
        m.addAttribute("currentuser", currentuser);
        m.addAttribute("listcomptes", listcomptes);
        return "/compte/index";
    }

    @GetMapping("/compte/new")
    public String showFormCompte(Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();
        }
        List<AccessPermission> listAP = (List<AccessPermission>) accessrepo.findAll();
        Compte compte = new Compte();
        compte.setType("Admin");
        model.addAttribute("currentuser", currentuser);
        model.addAttribute("compte", compte);
        model.addAttribute("listAP", listAP);
        return "/compte/formCompte";
    }

    @GetMapping("/compte/setperms/{email}")
    public String setPermCompte(@PathVariable("email") String email, Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String uemail = principal.getName();
            currentuser = cptrepo.findById(uemail).get();
        }
        List<AccessPermission> listAP = (List<AccessPermission>) accessrepo.findAll();
        Compte compte = repo.findById(email).get();
        CompteOwner cpt_owner = new CompteOwner();
        if (compte.getType().equals("Admin")) {
            cpt_owner.setType("Admin");
            cpt_owner.setNom("");
            cpt_owner.setPrenom("");
        } else if (compte.getType().equals("Parent")) {
            Parent parent = parentrepo.findById(email).get();
            cpt_owner.setType("Parent");
            cpt_owner.setNom(parent.getNom());
            cpt_owner.setPrenom(parent.getPrenom());
        } else if (compte.getType().equals("Kindergarten Director")) {
            Director director = directorrepo.findById(email).get();
            cpt_owner.setType("Kindergarten Director");
            cpt_owner.setNom(director.getNom());
            cpt_owner.setPrenom(director.getPrenom());
        }
        model.addAttribute("compte", compte);
        model.addAttribute("cpt_owner", cpt_owner);
        model.addAttribute("listAP", listAP);
        model.addAttribute("currentuser", currentuser);
        return "/compte/setperms";
    }

    @PostMapping("/compte/save")
    public String saveCompte(Compte cpt) {
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        if (cpt.getPassword() == null) {
            Compte old = repo.findById(cpt.getEmail()).get();
            cpt.setPassword(old.getPassword());
        } else {
            cpt.setPassword(bpe.encode(cpt.getPassword()));
        }
        repo.save(cpt);
        StreamKeys stk = new StreamKeys();
        stk.setEmailParent(cpt.getEmail());
        stk.setIdEnfant(0);
        stk.setKeyEnfant("");
        stk.setKeyParent("");
        stkrepo.save(stk);
        return "redirect:/compte";
    }

    @GetMapping("/compte/delete/{email}")
    public String deleteCompte(@PathVariable("email") String email) {
        Compte cpt = repo.findById(email).get();
        if (cpt.getType().equals("Kindergarten Director")) {
            directorrepo.deleteById(email);
        } else if (cpt.getType().equals("Parent")) {
            parentrepo.deleteById(email);
        } else {
            repo.deleteById(email);
        }

        return "redirect:/compte";
    }
}
