package hu.rf1.webshop.Webshop2.DAO;

import hu.rf1.webshop.Webshop2.Model.Ertekelesek;
import hu.rf1.webshop.Webshop2.Model.Termekek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Repository
public class ErtekelesDAO extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    public Double getRatingById(int id){
        String sql = "SELECT * FROM ertekelesek where termek_id="+id;

        List<Map<String,Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Ertekelesek> result = new ArrayList<Ertekelesek>();
        Double ertekelesek=0.0;
        Integer dbszam=0;

        for(Map<String,Object> row : rows){
            Ertekelesek ertekeles = new Ertekelesek();
            ertekeles.setId((Long) row.get("id"));
            Timestamp timestamp = (Timestamp) row.get("date");
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            ertekeles.setDate((LocalDateTime) localDateTime);
            ertekeles.setDescription((String) row.get("description"));
            ertekeles.setRating((Double) row.get("rating"));
            ertekeles.setTermek_id((Integer) row.get("termek_id"));
            ertekeles.setUser_id((Integer) row.get("user_id"));
            result.add(ertekeles);
        }
        for(Ertekelesek res : result){
            ertekelesek+=res.getRating();
            dbszam++;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        Double d = ertekelesek/dbszam;
        //logger.info("before format"+d);
        String formatted = df.format(d);
        Double rating = Double.parseDouble(formatted.replace(",","."));
        //logger.info("after format"+Double.parseDouble(formatted.replace(",",".")));
        return rating;
    }
    public void insertAfterProductInsert(LocalDateTime date, String description, Double rating, Integer termek_id, Integer user_id){
        String sql = "insert into ertekelesek (date,description,rating,termek_id,user_id) values(?,?,?,?,?)";
        getJdbcTemplate().update(sql, new Object[]{
                        date, description, rating, termek_id, user_id
        });
    }
}
