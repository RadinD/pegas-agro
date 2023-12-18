package ru.radin.pegasagro.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import ru.radin.pegasagro.models.Dealer;

@Repository
public class DealerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DealerRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public RowMapper<Dealer> dealerRowMapper = (r, i) -> {
        Dealer rowObject = new Dealer();
        rowObject.setId(r.getLong("id"));
        rowObject.setName(r.getString("name"));
        rowObject.setEmail(r.getString("email"));
        rowObject.setAgentFullName(r.getString("agent_full_name"));
        return rowObject;
    };


    public Iterable<Dealer> findAll(){
        return jdbcTemplate.query("SELECT * FROM dealers", dealerRowMapper);
    }

    public Optional<Dealer> findById(String id){
        List<Dealer> results = jdbcTemplate.query("SELECT * FROM dealers WHERE id = ?", dealerRowMapper, Integer.valueOf(id));
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    public void save(Dealer dealer){
        jdbcTemplate.update("INSERT INTO dealers(name, email, agent_full_name) VALUES(?, ?, ?)",
                dealer.getName(), dealer.getEmail(), dealer.getAgentFullName());
    }

    public Optional <Dealer> getOwnerDealer(String id){
        List<Dealer> results = jdbcTemplate.query("SELECT dealers.* FROM dealers LEFT JOIN owners ON dealers.id = owners.dealer_id WHERE owners.id = ?",
                dealerRowMapper, Integer.valueOf(id));
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));

    }

    public void delete(String id){
        jdbcTemplate.update("DELETE FROM dealers WHERE id = ?", Integer.valueOf(id));
    }

}
