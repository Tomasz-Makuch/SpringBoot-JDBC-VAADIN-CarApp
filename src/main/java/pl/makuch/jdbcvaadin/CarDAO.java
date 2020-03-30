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

    public void saveCar(Car car) {
        //String sql = "INSERT INTO Cars VALUES(?,?,?,?);";
        String sql = "INSERT INTO cars (mark, model, color) VALUES (?,?,?);";
        jdbcTemplate.update(sql, new Object[]{
                car.getMark(),
                car.getModel(),
                car.getColor()
        });
    }


    public void updateCar(Car car) {

        System.out.println("wypisuje: "+car.getId());
        if(car.getId()==0){
            saveCar(car);
        }
        else{
            String sql1 = "UPDATE cars SET mark = ?, model=?, color=? WHERE id = ?";

            jdbcTemplate.update(sql1, new Object[]{
                    car.getMark(),
                    car.getModel(),
                    car.getColor(),
                    car.getId()
            });
        }

    }


    public void deleteCar(Car car) {
        String sql = "Delete  from cars  where id=?;";
        jdbcTemplate.update(sql, new Object[]{
                car.getId()
        });

    }

    public List<Map<String, Object>> selectByMark(String mark) {
        String sql = "select * from cars where mark like ?;";
        return jdbcTemplate.queryForList(sql, new Object[]{mark});
    }

    public List<Car> getAllCars() {
        String sql = "select * from cars;";
        return jdbcTemplate.query(sql, new CarMapper());
    }

    public List<Car> getAllCars(String filterType, String filter) {
        if (filter == null || filter.isEmpty()) {
            return getAllCars();
        } else {
            String sql = "select * from cars where " + filterType + " = ?;";
            return jdbcTemplate.query(sql, new Object[]{filter}, new CarMapper());
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {


        //  String sql1 = "CREATE TABLE Cars (id int NOT NULL AUTO_INCREMENT, mark varchar(255), model varchar(255), color varchar(255), PRIMARY KEY (id));";
        // jdbcTemplate.update(sql1);
        String sql = "DELETE FROM CARS";
        jdbcTemplate.update(sql);

        saveCar(new Car("BMW", "M3", "black"));
        saveCar(new Car("AUDI", "Q5", "red"));
        saveCar(new Car("BMW", "X5", "black"));
        saveCar(new Car("AUDI", "TT", "blue"));

    }
}


