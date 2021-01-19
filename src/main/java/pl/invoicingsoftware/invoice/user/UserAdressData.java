package pl.invoicingsoftware.invoice.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.REGON;
import pl.invoicingsoftware.invoice.organization.Organization;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@NoArgsConstructor @Setter @Getter
public class UserAdressData {

    private User user;
    private Organization organization;
}
