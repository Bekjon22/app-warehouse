package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Output;

@Repository
public interface OutputRepo extends JpaRepository<Output,Long> {


}
