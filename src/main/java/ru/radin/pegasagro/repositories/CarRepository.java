package ru.radin.pegasagro.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import ru.radin.pegasagro.models.Car;

@Repository
public class CarRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public RowMapper<Car> carRowMapper = (r, i) -> {
        Car rowObject = new Car();
        rowObject.setId(r.getLong("id"));
        rowObject.setNumberPlate(r.getString("number_plate"));
        rowObject.setBuildDate(r.getDate("build_date").toLocalDate());
        return rowObject;
    };

    public Iterable<Car> findAll(){
        return jdbcTemplate.query("SELECT * FROM cars", carRowMapper);
    }

    public Optional<Car> findById(String id) {
        List<Car> results = jdbcTemplate.query("SELECT * FROM cars WHERE id = ?", carRowMapper, Integer.valueOf(id));
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Car> getCarsByOwnerId(String id){
        return jdbcTemplate.query("SELECT * FROM cars WHERE owner_id = ?", carRowMapper, Integer.valueOf(id));
    }

    public List<Car> getCarsByDealerId(String id){
        return jdbcTemplate.query("SELECT cars.* FROM cars LEFT JOIN owners ON cars.owner_id = owners.id LEFT JOIN dealers ON owners.id = dealers.id WHERE dealers.id = ?",
                carRowMapper, Integer.valueOf(id));
    }

    public void save(Car car){
        jdbcTemplate.update("INSERT INTO cars(number_plate, build_date) VALUES (?, ?)",
                car.getNumberPlate(), car.getBuildDate());
    }

    public void assignOwnerByOwnerId(String ownerId, Car car){
        jdbcTemplate.update("UPDATE cars SET owner_id=? WHERE id=?",
                Integer.valueOf(ownerId), car.getId());
    }


    public void delete(String id){
        jdbcTemplate.update("DELETE FROM cars WHERE id = ?", Integer.valueOf(id));
    }

}
