package pl.invoicingsoftware.invoice.invoice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.invoicingsoftware.invoice.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter @Getter @NoArgsConstructor
@Table(name = InvoiceConnection.TABLE_NAME)
public class InvoiceConnection extends BaseEntity {
    public final static String TABLE_NAME = "ADDITIONAL_INVOICES";
    private String numberInvoicing;
}
