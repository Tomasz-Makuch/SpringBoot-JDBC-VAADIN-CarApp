package pl.makuch.jdbcvaadin;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int i) throws SQLException {
        Car car=new Car();
        car.setId(rs.getInt(1));
        car.setMark(rs.getString(2));
        car.setModel(rs.getString(3));
        car.setColor(rs.getString(4));
        return car;
    }
}
