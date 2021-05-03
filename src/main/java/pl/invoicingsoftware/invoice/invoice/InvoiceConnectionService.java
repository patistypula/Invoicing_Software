package pl.invoicingsoftware.invoice.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @Transactional
@RequiredArgsConstructor
public class InvoiceConnectionService {
    private final InvoiceConnectionRepository connectionRepository;

    public InvoiceConnection saveNewInvoiceConnection(InvoiceConnection invoiceConnection){
        return connectionRepository.save(invoiceConnection);
    }
}
