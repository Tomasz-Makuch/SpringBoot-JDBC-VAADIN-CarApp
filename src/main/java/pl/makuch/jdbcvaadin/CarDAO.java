package pl.makuch.jdbcvaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CarDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveCar(Car car){
        String sql = "INSERT INTO Cars VALUES(?,?,?,?);";
        jdbcTemplate.update(sql, new Object[]{
                car.getId(),
                car.getMark(),
                car.getModel(),
                car.getColor()
        });
    }

    public List<Map<String, Object>> selectByMark(String mark){
        String sql = "select * from cars where mark like ?;";
        return jdbcTemplate.queryForList(sql, new Object[]{mark});
    }

    public List<Car> getAllCars(){
        String sql = "select * from cars;";
        return jdbcTemplate.query(sql,new CarMapper());
    }

    public List<Car> getAllCars(String filterType, String filter) {
        if(filter==null|| filter.isEmpty()){
            return getAllCars();
        }
        else {
            String sql = "select * from cars where "+filterType+" = ?;";
            return jdbcTemplate.query(sql,new Object[]{filter}, new CarMapper());
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDB(){

        String sql = "DELETE FROM CARS";
        jdbcTemplate.update(sql);

        saveCar(new Car(1,"BMW", "M3", "black"));
        saveCar(new Car(2,"AUDI", "Q5", "red"));
        saveCar(new Car(3,"BMW", "X5", "black"));
        saveCar(new Car(4,"AUDI", "TT", "blue"));

    }


}
