package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Measurement;

@Repository
public interface MeasurementRepo extends JpaRepository<Measurement,Long> {

    boolean existsByName(String name);

    Measurement findByNameAndIdNot(String name, Long id);
}
