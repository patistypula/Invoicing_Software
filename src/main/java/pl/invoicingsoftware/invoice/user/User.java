package pl.invoicingsoftware.invoice.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.invoicingsoftware.invoice.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity @Table(name = User.TABLE_NAME)
@NoArgsConstructor @Setter @Getter
public class User extends BaseEntity {
    public final static String TABLE_NAME = "USER";

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(name = "password")
    @Size(min = 5)
    private String password;

    @Column(name = "name", nullable = false)
    @Size(max = 50)
    private String name;

    @Column(name = "surname")
    @Size(max = 50)
    private String surname;

    @Column(name = "bank_account")
    @NotBlank(message = "Nr konta musi mieć 26 cyfr w formacie XX-XXXX-XXXX-XXXX-XXXX-XXXX-XXXX")
    private String bank_account;



//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles;




}
