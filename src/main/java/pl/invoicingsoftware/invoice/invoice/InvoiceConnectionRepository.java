package pl.invoicingsoftware.invoice.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceConnectionRepository extends JpaRepository<InvoiceConnection, Long> {

}
