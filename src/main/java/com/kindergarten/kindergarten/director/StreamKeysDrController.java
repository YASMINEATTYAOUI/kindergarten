package com.kindergarten.kindergarten.director;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kindergarten.kindergarten.compte.Compte;
import com.kindergarten.kindergarten.compte.CompteRepo;
import com.kindergarten.kindergarten.parent.Enfant;
import com.kindergarten.kindergarten.parent.EnfantRepo;
import com.kindergarten.kindergarten.parent.Parent;
import com.kindergarten.kindergarten.parent.ParentRepo;
import com.kindergarten.kindergarten.stream.SKInfo;
import com.kindergarten.kindergarten.stream.StreamKeys;
import com.kindergarten.kindergarten.stream.StreamKeysRepo;

@Controller
public class StreamKeysDrController {
    @Autowired
    private StreamKeysRepo streamkrepo;
    @Autowired
    private ParentRepo parentrepo;
    @Autowired
    private EnfantRepo enfantrepo;
    @Autowired
    private CompteRepo cptrepo;
    @Autowired
    private DirectorRepo directorrepo;

    @GetMapping("/director/stream")
    public String showVideoCallDemands(Principal principal, Model model) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();

            model.addAttribute("currentuser", currentuser);
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director director = directorrepo.findById(email).get();
                List<StreamKeys> listSK = (List<StreamKeys>) streamkrepo.findAll();
                List<SKInfo> listSKInfo = new ArrayList<>();
                for (StreamKeys sks : listSK) {
                    SKInfo skinf = new SKInfo();
                    if ((sks.getEmailParent().length() > 0) && (sks.getIdEnfant() > 0)
                            && (sks.getKeyParent().length() > 0) && (!sks.getKeyParent().equals("_"))) {
                        
                        Parent parent = parentrepo.findById(sks.getEmailParent()).get();
                        Enfant enfant = enfantrepo.findById(sks.getIdEnfant()).get();
                        skinf.setEmailparent(sks.getEmailParent());
                        skinf.setIdenfant(sks.getIdEnfant());
                        skinf.setKeyparent(sks.getKeyParent());
                        skinf.setNomparent(parent.getNom());
                        skinf.setPrenomparent(parent.getPrenom());
                        skinf.setNomenfant(enfant.getNom());
                        skinf.setPrenomenfant(enfant.getPrenom());
                        listSKInfo.add(skinf);
                        model.addAttribute("director", director);
                        model.addAttribute("listSKInfo", listSKInfo);
                        model.addAttribute("currentuser", currentuser);
                        return "/director/videoCall";
                    }
                }
                String message = "No Parent Video Calls Now";
                model.addAttribute("message", message);
                model.addAttribute("currentuser", currentuser);
                return "/error/message";
            }
        }
        return "/error/accessDenied";
    }

    @GetMapping("/director/streams/stream/{emailparent}/{idenfant}/{prenomparent}/{nomparent}/{prenomenfant}/{nomenfant}/{keyparent}/{keyenfant}")
    public String streamVideos(
            @PathVariable("emailparent") String emailparent,
            @PathVariable("idenfant") Integer idenfant,
            @PathVariable("prenomparent") String prenomparent,
            @PathVariable("nomparent") String nomparent,
            @PathVariable("prenomenfant") String prenomenfant,
            @PathVariable("nomenfant") String nomenfant,
            @PathVariable("keyparent") String keyparent,
            @PathVariable("keyenfant") String keyenfant,
            Model model, Principal principal) {
        Compte currentuser = null;
        if (principal != null) {
            String email = principal.getName();
            currentuser = cptrepo.findById(email).get();

            model.addAttribute("currentuser", currentuser);
            if (currentuser.getType().equals("Kindergarten Director")) {
                Director director = directorrepo.findById(email).get();
                SKInfo skinf = new SKInfo();
                skinf.setEmailparent(emailparent);
                skinf.setIdenfant(idenfant);
                skinf.setKeyenfant(keyenfant);
                skinf.setKeyparent(keyparent);
                skinf.setNomenfant(nomenfant);
                skinf.setNomparent(nomparent);
                skinf.setPrenomenfant(prenomenfant);
                skinf.setPrenomparent(prenomparent);
                model.addAttribute("skinf", skinf);
                model.addAttribute("director", director);
                return "/director/streamCall";
            }
        }
        return "/error/accessDenied";
    }

}
