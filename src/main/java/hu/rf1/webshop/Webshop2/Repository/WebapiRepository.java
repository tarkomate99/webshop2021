package hu.rf1.webshop.Webshop2.Repository;

import hu.rf1.webshop.Webshop2.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebapiRepository extends JpaRepository<Employee, Long> {
}
