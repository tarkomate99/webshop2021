package hu.rf1.webshop.Webshop2.Repository;

import hu.rf1.webshop.Webshop2.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findUserByEmail(String email);
}
