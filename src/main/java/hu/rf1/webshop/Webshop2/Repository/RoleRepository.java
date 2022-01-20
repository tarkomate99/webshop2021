package hu.rf1.webshop.Webshop2.Repository;

import hu.rf1.webshop.Webshop2.Model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {

    Role findByRole(String role);

}
