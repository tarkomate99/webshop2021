package hu.rf1.webshop.Webshop2.DAO;

import hu.rf1.webshop.Webshop2.Model.Termekek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TermekDAO extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

//    public List<Termekek> getAll(){
//        String sql = "SELECT * from termekek";
//        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
//
//        List<Termekek> result = new ArrayList<Termekek>();
//
//
//    }

    public Termekek getProductById(int id){
        String sql = "SELECT * FROM termekek WHERE id="+id;

        List<Map<String,Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Termekek> result = new ArrayList<Termekek>();
        for(Map<String, Object> row : rows){
            Termekek termek = new Termekek();
            termek.setId((Long) row.get("id"));
            termek.setCategory((String) row.get("category"));
            termek.setName((String) row.get("name"));
            termek.setNofitems((Integer) row.get("nofitems"));
            termek.setPrice((Integer) row.get("price"));
            termek.setRating((Double) row.get("rating"));
            termek.setImg((String) row.get("img"));
            termek.setDescription((String) row.get("description"));
            result.add(termek);
        }
        return result.get(0);
    }
    public void updateRatingById(long id, double rating){
        String sql = "update termekek set rating="+rating+" where id="+id;
        getJdbcTemplate().update(sql);
    }

}
