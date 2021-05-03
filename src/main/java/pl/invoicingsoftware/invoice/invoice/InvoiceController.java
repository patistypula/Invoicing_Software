package pl.invoicingsoftware.invoice.invoice;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.invoicingsoftware.invoice.organization.Organization;
import pl.invoicingsoftware.invoice.organization.OrganizationController;
import pl.invoicingsoftware.invoice.organization.OrganizationService;


import javax.swing.text.html.InlineView;
import javax.validation.Valid;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequiredArgsConstructor
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final OrganizationService organizationService;
    private final Validator validator;
    private final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @RequestMapping(value = "/invoice-page", method = RequestMethod.GET)
    public String selectKindOfInvoice(){
        return "invoice/invoice";
    }





    //@RequestMapping(value = "/showInvoice/{numberInvoicing}", method = RequestMethod.GET)
    @RequestMapping(value = "/showInvoice", method = RequestMethod.GET)
    public String showInvoice(@RequestParam String faktura,
                              Model model){
//    public String showInvoice(@PathVariable String numberInvoicing,
//                              Model model){
        faktura = faktura.replaceFirst("_", " ");
        faktura = faktura.replaceFirst("-", "/");
        logger.info("Odkodowano nr faktury na: "+faktura);
        Invoice invoice = invoiceService.getInvoiceByNumber(faktura);
        if(invoice!=null){
            logger.info("Faktura nr "+invoice.getNumberInvoicing()+" pobrnana z bazy danych");
        } else{
            logger.error("Faktura nr "+faktura+" nie istnieje!");
        }
        model.addAttribute("adress", invoice.getOrganization().adress());
        model.addAttribute("invoice", invoice);
        return "invoice/show-invoice";
    }

    @RequestMapping(value = "/showAllInvoice", method = RequestMethod.GET)
    public String showAllInvoice(Model model){
        int maxYear = LocalDate.now().getYear();
        List<Invoice> invoices = invoiceService.getAllInvoice(maxYear);
        LocalDate minData = invoiceService.getMinData();
        int minYear = minData.getYear();
        List<Integer> years = new ArrayList<>();
        for(int i=0;   i<(maxYear-minYear)+1;   i++){
            years.add(minYear+i);
        }
        model.addAttribute("years", years);
        model.addAttribute("maxYear", maxYear);
        model.addAttribute("invoices", invoices);
        return "invoice/show-all-invoice";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public String filterShowAllInvoice(@RequestParam String invoice,
                                       @RequestParam int year,
                                       Model model){
        if(year==0 && "ALL".equals(invoice)){
            logger.info("year -> all");
            logger.info("invoices -> all");
            List<Invoice> invoices = invoiceService.getAllInvoice();
            LocalDate minData = invoiceService.getMinData();
            int minYear = minData.getYear();
            int maxYear = LocalDate.now().getYear();
            List<Integer> years = new ArrayList<>();
            for(int i=0;   i<(maxYear-minYear)+1;   i++){
                years.add(minYear+i);
            }
            model.addAttribute("year", 0);
            model.addAttribute("invoice", invoice);
            model.addAttribute("years", years);
            model.addAttribute("maxYear", maxYear);
            model.addAttribute("invoices", invoices);
            return "invoice/show-all-invoice";
        }
        if(year!=0 && "ALL".equals(invoice)){
            logger.info("year -> "+year);
            logger.info("invoices -> all");
            List<Invoice> invoices = invoiceService.getAllInvoice(year);
            LocalDate minData = invoiceService.getMinData();
            int minYear = minData.getYear();
            int maxYear = LocalDate.now().getYear();
            List<Integer> years = new ArrayList<>();
            for(int i=0;   i<(maxYear-minYear)+1;   i++){
                years.add(minYear+i);
            }
            model.addAttribute("year", year);
            model.addAttribute("invoice", invoice);
            model.addAttribute("years", years);
            model.addAttribute("maxYear", maxYear);
            model.addAttribute("invoices", invoices);
            return "invoice/show-all-invoice";
        }
        if(year==0 && !"ALL".equals(invoice)){
            logger.info("year -> all");
            logger.info("invoices -> "+invoice);
            List<Invoice> invoices = invoiceService.getAllByKindInvoicing(invoice);
            LocalDate minData = invoiceService.getMinData();
            int minYear = minData.getYear();
            int maxYear = LocalDate.now().getYear();
            List<Integer> years = new ArrayList<>();
            for(int i=0;   i<(maxYear-minYear)+1;   i++){
                years.add(minYear+i);
            }
            model.addAttribute("year", year);
            model.addAttribute("invoice", invoice);
            model.addAttribute("years", years);
            model.addAttribute("maxYear", maxYear);
            model.addAttribute("invoices", invoices);
            return "invoice/show-all-invoice";
        }
        if(year!=0 && !"ALL".equals(invoice)){
            logger.info("year -> "+year);
            logger.info("invoices -> "+invoice);
            List<Invoice> invoices = invoiceService.getByKindInvoivingAndYear(invoice, year);
            LocalDate minData = invoiceService.getMinData();
            int minYear = minData.getYear();
            int maxYear = LocalDate.now().getYear();
            List<Integer> years = new ArrayList<>();
            for(int i=0;   i<(maxYear-minYear)+1;   i++){
                years.add(minYear+i);
            }
            model.addAttribute("year", year);
            model.addAttribute("invoice", invoice);
            model.addAttribute("years", years);
            model.addAttribute("maxYear", maxYear);
            model.addAttribute("invoices", invoices);
            return "invoice/show-all-invoice";
        }
        logger.error("year -> "+year);
        logger.info("invoices -> "+invoice);
        return "error/error-page";
    }


}
