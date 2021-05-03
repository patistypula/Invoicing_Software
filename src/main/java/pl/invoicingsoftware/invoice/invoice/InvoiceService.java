package pl.invoicingsoftware.invoice.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service @Transactional
@RequiredArgsConstructor
//@EnableJpaRepositories(basePackageClasses = InvoiceRepository.class)
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public Invoice getInvoiceById(long id){
        return invoiceRepository.getOne(id);
    }

    public Invoice getLastInvoice(String kindInvoicing){
        return invoiceRepository.findFirstByOrderByKindInvoicingDesc(kindInvoicing);
    }

    public Invoice saveNewInvoice(Invoice invoice){
        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceByNumber(String numberInvoicing){
        return invoiceRepository.findByByNumberInvoicing(numberInvoicing);
    }

    public List<Invoice> getAllInvoice(int year){
        return invoiceRepository.findAllInvoicesByDateOfIssue(year);
    }

    public List<Invoice> getAllInvoice(){
        return invoiceRepository.findAll();
    }

    public List<Invoice> getAllByKindInvoicing(String kindInvoicing){
        return invoiceRepository.findByKindInvoicing(kindInvoicing);
    }

    public LocalDate getMinData(){
        return invoiceRepository.findMinDate();
    }

    public List<Invoice> getByKindInvoivingAndYear(String kindInvoicing, int year){
        return invoiceRepository.findByKindInvoicingAndYear(kindInvoicing, year);
    }


}
