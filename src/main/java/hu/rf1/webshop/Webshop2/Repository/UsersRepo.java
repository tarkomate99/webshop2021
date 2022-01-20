package hu.rf1.webshop.Webshop2.Repository;

import hu.rf1.webshop.Webshop2.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;

public interface UsersRepo extends JpaRepository<User,Long> {


}
