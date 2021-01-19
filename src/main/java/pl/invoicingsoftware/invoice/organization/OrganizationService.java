package pl.invoicingsoftware.invoice.organization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @Transactional
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization getFirstOrganization(){
        long id = 1;
        return organizationRepository.getFirstOrganization(id);
    }

    public Organization updateOrganization(Organization organization){
        return organizationRepository.save(organization);
    }

    public Organization saveOrganization(Organization organization){
        return organizationRepository.save(organization);
    }

    public List<Organization> getAllOrganizationExceptFirst(){
        return organizationRepository.getAllOrganizationExceptFirst();
    }

    public Organization getOrganizationbyId(long id){
        return organizationRepository.getOne(id);
    }

    public List<Organization> getOrganizationByNip(String nip){
        return organizationRepository.getOrganizationByNip(nip);
    }

    public List<Organization> getOrganizationByRegon(String regon){
        return organizationRepository.getOrganizationByRegon(regon);
    }

    public List<Organization> getOrganizationByName(String name){
        return organizationRepository.getOrganizationByName(name);
    }
}
