package pl.invoicingsoftware.invoice.organization;



import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrganizationController {
    public final OrganizationService organizationService;
    private final Validator validator;
    private final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @RequestMapping(value = "/show-all-organizations", method = RequestMethod.GET)
    public String showAllOrganizations(Model model){
        List<Organization> organizations = organizationService.getAllOrganizationExceptFirst();
        model.addAttribute("organizations", organizations);
        String param = "wszystko";
        model.addAttribute("showParam", "wszystko");
        return "organization/show-all-organizations";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String showDetalisOrganization(@PathVariable long id, Model model){
        Organization organization = organizationService.getOrganizationbyId(id);
        model.addAttribute("organization", organization);
        return "organization/show-details";
    }

    @RequestMapping(value = "/add-new-organization", method = RequestMethod.GET)
    public String addNewOrganization(Model model){
        model.addAttribute("organization", new Organization());
        return "organization/add-new-organization";
    }

    @RequestMapping(value = {"/add-new-organization", "/edit/{id}"}, params = "save", method = RequestMethod.POST)
    public String saveNewOrganization(@Valid  @ModelAttribute("organization") Organization organization,
                                      BindingResult result,
                                      Model model){
        if(result.hasErrors()){
            return "organization/add-new-organization";
        }
        organizationService.saveOrganization(organization);
        return "redirect:/organization/show-all-organizations";
    }

    @RequestMapping(value = {"/add-new-organization", "/edit/{id}"}, params = "cancel", method = RequestMethod.POST)
    public String cancelSaveOrganization(){
        return "redirect:/organization/show-all-organizations";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editOrganization(@PathVariable long id, Model model){
        if(id==1){
            logger.error("Nie można edytować w tym miesjcu właściciela aplikacji");
            //error
        }
        Organization organization = organizationService.getOrganizationbyId(id);
        model.addAttribute("organization", organization);
        return "organization/add-new-organization";
    }

    @RequestMapping(value = "/search", params = "search", method = RequestMethod.GET)
    public String findOrganization(@RequestParam String searchArea,
                                   @RequestParam String what,
                                   Model model){
        model.addAttribute("param", null);
        model.addAttribute("info", what);
        logger.info("Wyszukiwanie organizacji po "+what);
        logger.info("Wyszukiwana fraza: "+searchArea);
        if("nazwa".equals(what)){
            List<Organization> organizations = organizationService.getOrganizationByName(searchArea);
            if(organizations.size()==0){
                logger.warn("nie znalezino wyników!");
            }
            model.addAttribute("listOrganizations", organizations);
        }
        if("nip".equals(what)){
            List<Organization> organizations = organizationService.getOrganizationByNip(searchArea);
            if(organizations.size()==0){
                logger.warn("nie znalezino wyników!");
            }
            model.addAttribute("listOrganizations", organizations);
        }
        if("regon".equals(what)){
            List<Organization> organizations = organizationService.getOrganizationByRegon(searchArea);
            if(organizations.size()==0){
                logger.warn("nie znalezino wyników!");
            }
            model.addAttribute("listOrganizations", organizations);
        }
        return "organization/show-all-organizations";
    }

    @RequestMapping(value = "/search", params = "clear", method = RequestMethod.GET)
    public String clearSearch(){
        return "redirect:/organization/show-all-organizations";
    }





}
