package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Currency;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency,Long> {
    boolean existsByName(String name);

    Currency findByNameAndIdNot(String name, Long id);
}
