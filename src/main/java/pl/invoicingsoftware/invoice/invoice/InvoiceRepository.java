package pl.invoicingsoftware.invoice.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
//
//    //@Query("SELECT i FROM Invoice i ORDER BY i.id DESC LIMIT 1")
    @Query(nativeQuery = true, value = "SELECT * FROM invoices WHERE rodzaj = ?1 ORDER BY id DESC LIMIT 1")
    //@Query("SELECT i FROM Invoice i WHERE i.kindInvoicing = ?1")
    Invoice findFirstByOrderByKindInvoicingDesc(String rodzaj);
//
    @Query("SELECT i FROM Invoice i JOIN i.organization WHERE i.numberInvoicing = ?1")
    Invoice findByByNumberInvoicing(String numberInvoicing);

    //@Query("SELECT i FROM Invoice i WHERE i.dateOfIssue LIKE ?1%")
    @Query(value = "SELECT * FROM invoices WHERE wystawiono LIKE ?1%", nativeQuery = true)
    List<Invoice> findAllInvoicesByDateOfIssue(int year);

    @Query(value = "SELECT MIN(wystawiono) FROM invoices", nativeQuery = true)
    LocalDate findMinDate();

    List<Invoice> findByKindInvoicing(String kindInvoicing);

    @Query(value = "SELECT * FROM invoices WHERE rodzaj = ?1 AND wystawiono LIKE ?1%", nativeQuery = true)
    List<Invoice> findByKindInvoicingAndYear(String kindInvoicing, int year);

}
