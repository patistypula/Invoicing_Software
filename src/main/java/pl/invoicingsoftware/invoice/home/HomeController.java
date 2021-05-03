package pl.invoicingsoftware.invoice.home;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.invoicingsoftware.invoice.organization.Organization;
import pl.invoicingsoftware.invoice.organization.OrganizationService;
import pl.invoicingsoftware.invoice.user.User;
import pl.invoicingsoftware.invoice.user.UserService;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final OrganizationService organizationService;
    @GetMapping("/")
    public String homePage(){
        boolean firstRun = false;
        if(userService.getUserById()==null){
            User user = new User();
            user.setName("user");
            user.setSurname("surname");
            user.setBank_account("XX XXXX XXXX XXXX XXXX XXXX XXXX");
            user.setEmail("user@email.com");
            String salt = BCrypt.gensalt();
            user.setPassword(BCrypt.hashpw("user1", salt));
            userService.saveUser(user);
            firstRun = true;
        }
        if(organizationService.getFirstOrganization()==null){
            Organization organization = new Organization();
            organization.setName("Firma");
            organization.setCity("City");
            organization.setPostcode("XX-XXX");
            organization.setStreet("street");
            organization.setBuilding(null);
            organization.setFlat(null);
            organization.setNip(null);
            organization.setRegon(null);
            organizationService.saveOrganization(organization);
        }
        if(firstRun==true){
            return "redirect:/user/edit-adress-data";
        }
        return "index";
    }
}
