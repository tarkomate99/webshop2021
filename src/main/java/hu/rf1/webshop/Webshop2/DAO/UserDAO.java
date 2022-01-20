package hu.rf1.webshop.Webshop2.DAO;

import hu.rf1.webshop.Webshop2.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAO  extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }


    public User listUser(String email){
        String sql = "select * from users where email='"+email+"'";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<User> result = new ArrayList<User>();
        for(Map<String,Object>row:rows){
            User user = new User();
            user.setId((Long) row.get("id"));
            user.setAddress((String) row.get("address"));
            user.setCity((String) row.get("city"));
            user.setEmail((String) row.get("email"));
            user.setIranyito((Integer) row.get("iranyito"));
            result.add(user);
        }
        return result.get(0);
    }
    public void updateUser(String email, Integer iranyito, String city, String address){
        String sql = "update users set iranyito='"+iranyito+"', city='"+city+"', address='"+address+"' where email='"+email+"'";
        getJdbcTemplate().update(sql);
    }

    public void deleteUser(Integer id){
        String sql = "delete from users where id="+id;
        getJdbcTemplate().update(sql);
    }



}
