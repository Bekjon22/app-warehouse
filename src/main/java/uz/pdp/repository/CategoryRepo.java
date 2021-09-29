package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Category;
import uz.pdp.domain.Measurement;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {


}
