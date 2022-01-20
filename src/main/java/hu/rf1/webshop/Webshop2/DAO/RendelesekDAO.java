package hu.rf1.webshop.Webshop2.DAO;

import hu.rf1.webshop.Webshop2.Model.Rendelesek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RendelesekDAO  extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    public List<Rendelesek> getOrdersById(int id){
        String sql = "SELECT * FROM rendelesek where user_id="+id;
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Rendelesek> result = new ArrayList<Rendelesek>();
        for(Map<String, Object> row: rows){
            Rendelesek rendeles = new Rendelesek();
            rendeles.setId((Long) row.get("id"));
            Timestamp timestamp = (Timestamp) row.get("date");
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            rendeles.setDate(localDateTime);
            rendeles.setOsszeg((Integer) row.get("osszeg"));
            rendeles.setTermek_id((Integer) row.get("termek_id"));
            result.add(rendeles);
        }
        return result;
    }
    public void insertOrder(Rendelesek rendeles){
        String sql = "insert into rendelesek(date,osszeg,termek_id,user_id) values (?,?,?,?)";
        getJdbcTemplate().update(sql,new Object[]{
           rendeles.getDate(), rendeles.getOsszeg(), rendeles.getTermek_id(), rendeles.getUser_id()
        });
    }

}
