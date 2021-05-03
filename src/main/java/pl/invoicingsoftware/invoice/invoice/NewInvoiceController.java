package pl.invoicingsoftware.invoice.invoice;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.invoicingsoftware.invoice.organization.Organization;
import pl.invoicingsoftware.invoice.organization.OrganizationController;
import pl.invoicingsoftware.invoice.organization.OrganizationService;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/invoice")
public class NewInvoiceController {
    private final InvoiceService invoiceService;
    private final OrganizationService organizationService;
    private final Validator validator;
    private final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    //wystawianie nowej faktury

    //wyświetlenie formularza nowej faktury
    @RequestMapping(value = "/newInvoice", method = RequestMethod.GET)
    public String newInvoice(Model model){
        Invoice invoice = new Invoice();
        model.addAttribute("invoice", invoice);
        return "invoice/new-invoice";
    }

    //sprawdzenie czy nowa fakura jest poprawnie wypełniona
    @RequestMapping(value = "/newInvoice", params = "saveInvoice", method = RequestMethod.POST)
    public String checkNewInvoce(@Valid @ModelAttribute("invoice") Invoice invoice,
                                BindingResult result,
                                Model model) {
        if(result.hasErrors()){
            return "invoice/new-invoice";
        }
        invoice.setNumberInvoicing(invoice.createNumberInvoicing(invoice.getKindInvoicing(), invoiceService));
        invoice.setBrutto(invoice.getNetto()*(1+(invoice.getVat()/100)));
        model.addAttribute("invoice", invoice);
        return "invoice/check-new-invoice";
    }

    //zapisz sprawdzoną nową fakturę
    @RequestMapping(value = "/newInvoice", params = "okCheckNewInvoice", method = RequestMethod.POST)
    public String saveNewInvoice(@ModelAttribute ("invoice") Invoice invoice){
        System.out.println("nr faktury "+invoice.getNumberInvoicing());
        System.out.println("rodzaj faktury: "+invoice.getKindInvoicing());
        invoice.setBrutto(invoice.getNetto()*((100+invoice.getVat())/100));
        invoiceService.saveNewInvoice(invoice);
        return "redirect:/invoice/invoice-page";
    }

    //powrót do edycji nowej faktury
    @RequestMapping(value = "/newInvoice", params = "editNewInvoice", method = RequestMethod.POST)
    public String backToEditNewInvoice(@ModelAttribute ("invoice") Invoice invoice,
                                       Model model){
        model.addAttribute("invoice", invoice);
        return "invoice/new-invoice";
    }

    //anulowanie wystawienia nowej faktury
    @RequestMapping(value = "/newInvoice", params = "cancelNewInvocie", method = RequestMethod.POST)
    public String cancelNewInvoice(){
        return "redirect:/invoice/invoice-page";
    }

    //wyświetlenie wszystkich fakturobiorców z bazy danych do wybrania do faktury
    @RequestMapping(value = "/newInvoice", params = "loadORGANIZATION", method = RequestMethod.POST)
    public String laoadOrganizationFromDB(@ModelAttribute ("invoice") Invoice invoice,
                                          Model model){
        List<Organization> organizations = organizationService.getAllOrganizationExceptFirst();
        model.addAttribute("invoice", invoice);
        model.addAttribute("organizations", organizations);
        return "invoice/select-organization";
    }

    //dodanie fakturobiorcy z bazy danych do faktury i powrót do formularza faktury
    @RequestMapping(value = "/newInvoice", params = "addOrganization", method = RequestMethod.POST)
    public String addOrganizationFromBase(@RequestParam long addOrganization,
                                          @ModelAttribute("invoice") Invoice invoice,
                                          Model model){
        invoice.setOrganization(organizationService.getOrganizationbyId(addOrganization));
        model.addAttribute("invoice", invoice);
        return "invoice/new-invoice";
    }

    //wyświetlenie formularza wprowadzenia nowego fakturobiorcy do bzy danych
    @RequestMapping(value = "/newInvoice", params = "newORGANIZATION", method = RequestMethod.POST)
    public String newOrganization(@ModelAttribute ("invoice") Invoice invoice,
                                  Model model){
        //List<Organization> organizations = organizationService.getAllOrganizationExceptFirst();
        InvoicePlusNewOrganization object = new InvoicePlusNewOrganization();
        object.setInvoice(invoice);
        object.setOrganization(new Organization());
        model.addAttribute("object", object);
        //model.addAttribute("organization", new Organization());
        return "organization/add-new-organization-Invoice";
    }

    //zapis nowego fakturobiorcy i powrót do formularza nowej faktury
    @RequestMapping(value = "/newInvoice", params = "save", method = RequestMethod.POST)
    public String saveNewOrganization(@Valid @ModelAttribute ("object") InvoicePlusNewOrganization object,
                                      BindingResult result,
                                      Model model){
        if(result.hasErrors()){
            return "organization/add-new-organization-Invoice";
        }
        organizationService.saveOrganization(object.getOrganization());
        Invoice invoice = object.getInvoice();
        invoice.setOrganization(object.getOrganization());
        model.addAttribute("invoice", invoice);
        return "invoice/new-invoice";
    }
}
