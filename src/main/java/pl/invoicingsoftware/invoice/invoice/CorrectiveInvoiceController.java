package pl.invoicingsoftware.invoice.invoice;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.invoicingsoftware.invoice.organization.OrganizationController;
import pl.invoicingsoftware.invoice.organization.OrganizationService;

import javax.validation.Valid;
import javax.validation.Validator;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/invoice")
public class CorrectiveInvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceConnectionService connectionService;
    private final OrganizationService organizationService;
    private final Validator validator;
    private final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    //korygowanie faktury

    //wyświetlenie formularza z korygowana faktura
    @RequestMapping(value = "/correctionInvoice/{id}", method = RequestMethod.GET)
    public String correctionInvoiceId(@PathVariable long id){
        Invoice invoice = invoiceService.getInvoiceById(id);
        if(invoice==null){
            logger.error("Faktura o id "+id+" nie istnieje lub nie udało się jej pobrać");
        }
        //InvoiceConnection connection = new InvoiceConnection();
        String faktura = invoice.getNumberInvoicing();

        logger.info("Faktura id "+id+" nr faktury "+faktura);
        faktura = faktura.replaceFirst(" ", "_");
        faktura = faktura.replaceFirst("/", "-");
        logger.info("Zamiana nr do wyświetlenia na: "+faktura);
        String adress = "redirect:/invoice/correctionInvoice?faktura="+faktura;
        return adress;
    }

    @RequestMapping(value = "/correctionInvoice", method = RequestMethod.GET)
    public String correctionInvoiceByNumber(@RequestParam String faktura,
                                            Model model) {
        faktura = faktura.replaceFirst("_", " ");
        faktura = faktura.replaceFirst("-", "/");
        logger.info("Odkodowano nr faktury na: " + faktura);
        Invoice invoice = invoiceService.getInvoiceByNumber(faktura);
        //Invoice correct = new Invoice();
        if (invoice != null) {
            logger.info("Faktura nr " + invoice.getNumberInvoicing() + " pobrnana z bazy danych");
            model.addAttribute("adress", invoice.getOrganization().adress());
        } else {
            logger.error("Faktura nr " + faktura + " nie istnieje!");
        }
        invoice.setId(null);
        invoice.setKindInvoicing("FKOR");
        InvoiceConnection connection = new InvoiceConnection();
        connection.setNumberInvoicing(faktura);
        //connectionService.saveNewInvoiceConnection(connection);

        LocalDate day = invoice.getDateOfIssue();
        invoice.setDateOfIssue(LocalDate.now());
        invoice.setAdditionals(connection);
        logger.info("Nr korygowanej faktury: "+ invoice.getAdditionals().stream().findFirst().get().getNumberInvoicing());
        model.addAttribute("invoice", invoice);
        model.addAttribute("day", day);
        return "invoice/correction-invoice";
    }

    @RequestMapping(value ="/correctionInvoice", params = "saveCorrection", method = RequestMethod.POST)
    public String saveCorrectionInvoice(@RequestParam String faktura,
                                        @Valid @ModelAttribute ("invoice") Invoice invoice, BindingResult result,
                                        Model model){
        if(result.hasErrors()){
            logger.error("Błąd walidacji!");
            return "invoice/correction-invoice";
        }
        faktura = faktura.replaceFirst("_", " ");
        faktura = faktura.replaceFirst("-", "/");
        InvoiceConnection connection = new InvoiceConnection();
        connection.setNumberInvoicing(faktura);

        invoice.setNumberInvoicing(invoice.createNumberInvoicing(invoice.getKindInvoicing(), invoiceService));
        invoice.setBrutto(invoice.getNetto()*((100+invoice.getVat())/100));
        invoice = invoiceService.saveNewInvoice(invoice);
        invoice.setAdditionals(connection);
        connectionService.saveNewInvoiceConnection(connection);
        return "redirect:/invoice/invoice-page";
    }
}
