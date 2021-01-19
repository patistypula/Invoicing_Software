package pl.invoicingsoftware.invoice.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("SELECT o FROM Organization o WHERE o.id = ?1")
    Organization getFirstOrganization(long id);

    @Query("SELECT o FROM Organization o WHERE o.id !=1")
    List<Organization> getAllOrganizationExceptFirst();

    @Query("SELECT o FROM Organization o WHERE o.nip = ?1")
    List<Organization> getOrganizationByNip(String nip);

    @Query("SELECT o FROM Organization o WHERE o.regon = ?1")
    List<Organization> getOrganizationByRegon(String regon);

    @Query("SELECT o FROM Organization o WHERE o.name LIKE ?1%")
    List<Organization> getOrganizationByName(String name);
}
