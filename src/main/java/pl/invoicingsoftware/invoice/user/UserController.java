package pl.invoicingsoftware.invoice.user;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.invoicingsoftware.invoice.organization.Organization;
import pl.invoicingsoftware.invoice.organization.OrganizationService;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrganizationService organizationService;

    @RequestMapping(value = "/show-data-User", method = RequestMethod.GET)
    public String showDataUser(Model model){
        if(userService.getUserById()==null){
            User user = new User();
            user.setName("user");
            user.setSurname("surname");
            user.setBank_account("XX XXXX XXXX XXXX XXXX XXXX XXXX");
            user.setEmail("user@email.com");
            String salt = BCrypt.gensalt();
            user.setPassword(BCrypt.hashpw("user1", salt));
            userService.saveUser(user);
        }
        if(organizationService.getFirstOrganization()==null){
            Organization organization = new Organization();
            organization.setCity("City");
            organization.setPostcode("XX-XXX");
            organization.setStreet("street");
            organization.setBuilding(null);
            organization.setFlat(null);
            organization.setNip(null);
            organization.setRegon(null);
            organizationService.saveOrganization(organization);
        }
        model.addAttribute("user", userService.getUserById());
        model.addAttribute("organization", organizationService.getFirstOrganization());
        return "user/showDataUser";
    }

    @RequestMapping(value = "/edit-adress-data", method = RequestMethod.GET)
    public String showEditAdressData(Model model) {
        User user = userService.getUserById();
        Organization organization = organizationService.getFirstOrganization();
        UserAdressData userAdressData = new UserAdressData();
        userAdressData.setUser(user);
        userAdressData.setOrganization(organization);
        model.addAttribute("user", userAdressData);
        return "user/editUser";
    }

    @RequestMapping(value = "/edit-adress-data", params = "save", method = RequestMethod.POST)
    public String saveEditAdressData(@ModelAttribute("user") UserAdressData userAdressData,
                                     Model model){
        userAdressData.getUser().
                setBank_account(userAdressData.getUser().getBank_account().replaceAll(" ", ""));
        if(userAdressData.getUser().getBank_account().length()!=26){
            model.addAttribute("bank_error", "error");
            model.addAttribute("user", userAdressData);
            return "user/editUser";
        }
        userAdressData.getUser().setBank_account(formatAcountBank(userAdressData.getUser().getBank_account()));
        userService.updateUser(userAdressData.getUser());
        organizationService.updateOrganization(userAdressData.getOrganization());
        return "redirect:/user/show-data-User";
    }

    @RequestMapping(value = {"/edit-adress-data", "/edit-email-password"}, params = "cancel", method = RequestMethod.POST)
    public String cancelEditAdressData(){
        return "redirect:/user/show-data-User";
    }

    @RequestMapping(value = "/edit-email-password", method = RequestMethod.GET)
    public String editPasswordEmail(Model model){
        User user = userService.getUserById();
        model.addAttribute("user", user);
        return "user/edit-email";
    }

    @RequestMapping(value = "/edit-email-password", params = "save", method = RequestMethod.POST)
    public String saveEditPasswordEmail(@ModelAttribute("user") User user, @RequestParam String repassword,
                                        Model model){
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(!user.getPassword().equals(repassword)){
            model.addAttribute("password_error", "error");
            model.addAttribute("user", user);
            return "user/edit-email";
        }
        String salt = BCrypt.gensalt();
        user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
        userService.updateUser(user);
        return "redirect:/user/show-data-User";
    }

    private static String formatAcountBank(String acount){
        String bank_account = "";
        //acount = acount.replaceAll(" ", "");
        char tab[] = acount.toCharArray();
        int x = 2;
        for(int i=0;   i<tab.length;   i++){
            char leter = tab[i];
            if(i==x){
                bank_account += ' ';
                x += 4;
            }
            bank_account += tab[i];
        }
        return bank_account;
    }
}
