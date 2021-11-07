package hu.rf1.webshop.Webshop2.Repository;

import hu.rf1.webshop.Webshop2.Model.Users;
import org.apache.tomcat.jni.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users,Long> {

    Users findByEmail(String email);

}
