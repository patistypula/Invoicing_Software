package pl.invoicingsoftware.invoice.invoice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.invoicingsoftware.invoice.BaseEntity;
import pl.invoicingsoftware.invoice.organization.Organization;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity @Table(name = Invoice.TABLE_NAME)
@Setter @Getter @NoArgsConstructor
public class Invoice extends BaseEntity {
    public final static String TABLE_NAME = "INVOICES";

    @Column(name = "rodzaj")
    private String kindInvoicing;
    @Column(name = "numer", unique = true)
    private String numberInvoicing;
    @Column(name = "wystawiono")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfIssue;
    @Column(name = "sprzedaz")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate saleDate;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    @Column(name = "towar")
    private String descryptionSaleProduct;
    @Column(name = "netto")
    private Float netto;
    @Column(name = "brutto")
    private Float brutto;
    @Column(name = "VAT")
    private Float vat;
    @Column(name = "platnosc")
    private String paymentMethod;

    @OneToMany
    @JoinColumn(name = "additional")
    private Set<InvoiceConnection> additionals = new HashSet<>();


    public void setAdditionals(InvoiceConnection connection) {
        this.additionals.add(connection);
    }

    public static String createNumberInvoicing(String kindInvoicing, InvoiceService invoiceService){
        String result = "";
        int currentYear = LocalDate.now().getYear();
        Invoice invoice = invoiceService.getLastInvoice(kindInvoicing);
        if(invoice!=null) {
            switch (kindInvoicing) {
                case "FA":
                    result = "FA ";
                    String[] faNumber = invoice.getNumberInvoicing().substring(3).split("/");
                    int yearFA = Integer.parseInt(faNumber[1]);
                    if (currentYear == yearFA) {
                        result += String.valueOf(Integer.parseInt(faNumber[0]) + 1);
                        result += "/";
                        result += faNumber[1];
                    } else {
                        result += "1/";
                        result += String.valueOf(currentYear);
                    }
                    break;
                case "PRO":
                    result = "PRO ";
                    String[] numberPRO = invoice.getNumberInvoicing().substring(4).split("/");
                    int yearPRO = Integer.parseInt(numberPRO[1]);
                    if (currentYear == yearPRO) {
                        result += String.valueOf(Integer.parseInt(numberPRO[0]) + 1);
                        result += "/";
                        result += numberPRO[1];
                    } else {
                        result += "1/";
                        result += String.valueOf(currentYear);
                    }
                    break;
                case "FZ":
                    result = "FZ ";
                    String[] numberFZ = invoice.getNumberInvoicing().substring(3).split("/");
                    int yearFZ = Integer.parseInt(numberFZ[1]);
                    if (currentYear == yearFZ) {
                        result += String.valueOf(Integer.parseInt(numberFZ[0]) + 1);
                        result += "/";
                        result += numberFZ[1];
                    } else {
                        result += "1/";
                        result += String.valueOf(currentYear);
                    }
                    break;
                case "FK":
                    result = "FK ";
                    String[] numberFK = invoice.getNumberInvoicing().substring(3).split("/");
                    int yearFK = Integer.parseInt(numberFK[1]);
                    if (currentYear == yearFK) {
                        result += String.valueOf(Integer.parseInt(numberFK[0]) + 1);
                        result += "/";
                        result += numberFK[1];
                    } else {
                        result += "1/";
                        result += String.valueOf(currentYear);
                    }
                    break;
            }
        }else{
            result += kindInvoicing+" 1/"+currentYear;
        }
        return result;
    }
}
