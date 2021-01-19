package pl.invoicingsoftware.invoice.organization;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.REGON;
import pl.invoicingsoftware.invoice.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity @Table(name = Organization.TABLE_NAME)
@NoArgsConstructor @Setter @Getter
public class Organization extends BaseEntity {
    public final static String TABLE_NAME = "ORGANIZATIONS";

    @Column(name="organization")
    @Size(min = 3, max = 100)
    private String name;
    //adres firmy
    @Column(name = "city")
    @Size(min=2, max = 40)
    private String city; //miasto
    @Column(name = "postcode")
    private String postcode; //kod pocztowy
    @Column(name = "street")
    @Size(min = 2, max = 60)
    private String street; //ulica
    @Column(name = "building")
    private Long building; //nr domu
    @Column(name = "flat")
    private Long flat; //nr mieszkania

    @Column(name = "NIP",unique = true)
    //@NotBlank(message = "Poprawny format nr NIP zawiera 10 cyfro w formacie XXX-XXX-XX-XX")
    private String nip;
    @Column(name = "regon", unique = true)
    @REGON(message = "Popranwy REGON zawiera 9 odpowiednich cyfr!")
    private String regon;
}
