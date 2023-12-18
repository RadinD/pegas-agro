package ru.radin.pegasagro.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import ru.radin.pegasagro.models.Owner;


@Repository
public class OwnerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OwnerRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public RowMapper<Owner> ownerRowMapper = (r, i) -> {
        Owner rowObject = new Owner();
        rowObject.setId(r.getLong("id"));
        rowObject.setFullName(r.getString("full_name"));
        rowObject.setPhoneNumber(r.getString("phone_number"));
        rowObject.setEmail(r.getString("email"));
        return rowObject;
    };

    public Iterable<Owner> findAll(){
        return jdbcTemplate.query("SELECT * FROM owners", ownerRowMapper);
    }

    public Optional<Owner> findById(String id){
        List<Owner> results = jdbcTemplate.query("SELECT * FROM owners WHERE id = ?", ownerRowMapper, Integer.valueOf(id));
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    public void save(Owner owner){
        jdbcTemplate.update("INSERT INTO owners(full_name, phone_number, email) VALUES (?, ?, ?)",
                owner.getFullName(), owner.getPhoneNumber(), owner.getEmail());
    }

    public Optional<Owner> getCarOwner(String id){
        List<Owner> results = jdbcTemplate.query("SELECT owners.* FROM cars LEFT JOIN owners ON cars.owner_id = owners.id WHERE cars.id = ?",
                ownerRowMapper, Integer.valueOf(id));
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Owner> getOwnersByDealerId(String id){
        return jdbcTemplate.query("SELECT * FROM owners WHERE dealer_id = ?", ownerRowMapper, Integer.valueOf(id));
    }

    public void assignDealerByDealerId(String dealerId, Owner owner){
        jdbcTemplate.update("UPDATE owners SET dealer_id=? WHERE id=?",
                Integer.valueOf(dealerId), owner.getId());
    }

    public void delete(String id){
        jdbcTemplate.update("DELETE FROM owners WHERE id = ?", Integer.valueOf(id));
    }

}
