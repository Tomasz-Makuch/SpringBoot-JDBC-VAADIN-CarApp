package pl.makuch.jdbcvaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDAO(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveCar(Car car) {
        String sql = "INSERT INTO cars (mark, model, color) VALUES (?,?,?);";
        jdbcTemplate.update(sql, new Object[]{
                car.getMark(),
                car.getModel(),
                car.getColor()
        });
    }

    public void updateCar(Car car) {

        //if id=0 it's new car
        if (car.getId() == 0) {
            saveCar(car);
        }
        // if id!=0, car exist id database
        else {
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
    public List<Car> getFilteredCars() {
        String sql = "select * from cars;";
        return jdbcTemplate.query(sql, new CarMapper());
    }

    public int getCarsNumByMark(String mark){
        String sql = "SELECT COUNT(mark) FROM cars where mark = ?;";

        return jdbcTemplate.queryForObject(sql, new Object[] {mark}, Integer.class);
    }

    public List<Car> getFilteredCars(String filterType, String filter) {
        if (filter == null || filter.isEmpty()) {
            return getFilteredCars();
        } else {
            String sql = "select * from cars where " + filterType + " = ?;";
            return jdbcTemplate.query(sql, new Object[]{filter}, new CarMapper());
        }
    }

    public List<String> getAllCarMarks(){
        String sql = "SELECT DISTINCT mark FROM cars;";
        return jdbcTemplate.queryForList(sql,String.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {

        String sql1 = "CREATE TABLE IF NOT EXISTS Cars (id int NOT NULL AUTO_INCREMENT, mark varchar(255), model varchar(255), color varchar(255), PRIMARY KEY (id));";
        jdbcTemplate.update(sql1);

//        String sql2 = "DELETE FROM CARS";
//        jdbcTemplate.update(sql2);

        saveCar(new Car("BMW", "M3", "black"));
        saveCar(new Car("AUDI", "Q5", "red"));
        saveCar(new Car("BMW", "X5", "black"));
        saveCar(new Car("AUDI", "TT", "blue"));

    }
}


