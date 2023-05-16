package com.team2.inventory.controller;

import com.team2.inventory.interfacemethods.CaseInterface;
import com.team2.inventory.interfacemethods.UserInterface;
import com.team2.inventory.model.Case;
import com.team2.inventory.model.User;
import com.team2.inventory.service.CaseImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("")
@SessionAttributes("userdetails")
public class CaseController {
    @Autowired
    private CaseInterface caseService;
    @Autowired
    private UserInterface uservice;

    @RequestMapping(value = "/all/case/list")
    public String listCases(Model model) {
        model.addAttribute("cases", caseService.findAllCases());
        return "cases";
    }

    @RequestMapping(value = "/admin/case/add")
    public String addForm(ModelMap model) {
        model.addAttribute("case", new Case());
        return "addcase";
    }

    @RequestMapping(value = "/admin/case/edit/{id}")
    public String editCaseForm(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("case", caseService.findCaseById(id));
        return "case-form";
    }

    //save an edited case record
    @RequestMapping(value = "/admin/case/save")
    public String saveCase(@ModelAttribute("case") @Valid Case aCase, BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            return "case-form";
        }

        Case dbCase = caseService.findCaseById(aCase.getId());

        caseService.saveCase(aCase);

        return "forward:/all/case/list";
    }

    //method to save a newly created case
    @RequestMapping(value = "/admin/case/savenew")
    public String saveNewCase(@ModelAttribute("case") @Valid Case aCase, @RequestParam("case_file") MultipartFile caseFile, BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            return "case-form";
        }

        aCase.setCaseFile(uploadFile(caseFile));

        caseService.saveCase(aCase);

        return "forward:/all/case/list";
    }

    //method to delete a case
    @RequestMapping(value = "/admin/case/delete/{id}")
    public String deleteCase(@PathVariable("id") Integer id) {
        Case aCase = caseService.findCaseById(id);

        caseService.deleteCase(aCase);

        return "forward:/all/case/list";
    }

    public String uploadFile(MultipartFile file){
        try {
            String uploadPath = "src/main/resources/static";
            String servedPath = "target/classes/static";
            Path path = Paths.get(uploadPath+"/uploads/" + file.getOriginalFilename());
            Path path2 = Paths.get(servedPath+"/uploads/" + file.getOriginalFilename());

            if(!Files.exists(path.getParent())){
                Files.createDirectories(path.getParent());
            }

            if(!Files.exists(path2.getParent())){
                Files.createDirectories(path2.getParent());
            }

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(file.getInputStream(), path2, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            e.printStackTrace();
        }

        return "/uploads/" + file.getOriginalFilename();
    }
}