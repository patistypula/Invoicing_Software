package pl.invoicingsoftware.invoice.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.invoicingsoftware.invoice.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Product.TABLE_NAME)
@Setter @Getter @NoArgsConstructor
public class Product extends BaseEntity {
    public final static String TABLE_NAME = "PRODUCT";

    @Column(name = "product_name")
    private String productName;

    @Column(name = "netto")
    private Float netto;
    @Column(name = "brutto")
    private Float brutto;
    @Column(name = "VAT")
    private Float vat;
    @Column(name = "discount")
    private Float discount;
    @Column(name = "quantity")
    private Long quantity;


}
