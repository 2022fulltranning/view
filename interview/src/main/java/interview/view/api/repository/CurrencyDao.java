package interview.view.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import interview.view.api.entity.Currency;

@Repository
public interface CurrencyDao extends CrudRepository<Currency, String>{

}
