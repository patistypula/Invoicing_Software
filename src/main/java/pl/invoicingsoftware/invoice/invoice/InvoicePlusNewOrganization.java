package pl.invoicingsoftware.invoice.invoice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.invoicingsoftware.invoice.organization.Organization;

import javax.validation.Valid;

@NoArgsConstructor @Setter @Getter
public class InvoicePlusNewOrganization {
    public Invoice invoice;
    @Valid
    public Organization organization;
}
