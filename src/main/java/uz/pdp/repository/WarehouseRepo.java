package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Currency;
import uz.pdp.domain.Warehouse;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse,Long> {

    boolean existsByName(String name);

    Warehouse findByNameAndIdNot(String name, Long id);




}
