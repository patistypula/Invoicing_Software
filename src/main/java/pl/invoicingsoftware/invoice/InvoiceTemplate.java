//package pl.invoicingsoftware.invoice;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.format.annotation.DateTimeFormat;
//import pl.invoicingsoftware.invoice.organization.Organization;
//
//import javax.persistence.Column;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MappedSuperclass;
//import java.time.LocalDate;
//
//@Setter @Getter @MappedSuperclass
//public class InvoiceTemplate extends BaseEntity {
//    @Column(name = "rodzaj")
//    private String kindInvoicing;
//    @Column(name = "numer", unique = true)
//    private String numberInvoicing;
//    @Column(name = "data_wystawienia")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private LocalDate dateOfIssue;
//    @Column(name = "data_sprzedazy")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private LocalDate saleDate;
//    @ManyToOne
//    @JoinColumn(name = "organization_id")
//    private Organization organization;
//    @Column(name = "usluga-towar")
//    private String descryptionSaleProduct;
//    @Column(name = "netto")
//    private Float netto;
//    @Column(name = "brutto")
//    private Float brutto;
//    @Column(name = "VAT")
//    private Float vat;
//    @Column(name = "platnosc")
//    private String paymentMethod;
//
//}
