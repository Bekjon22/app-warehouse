package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Attachment;
import uz.pdp.domain.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    boolean existsByNameAndCategoryId(String name, Long category_id);

    @Query(nativeQuery = true,value = " SELECT p.id FROM product p  " +
            " ORDER BY p.id DESC LIMIT 1 ")
    Long findLastId();

    boolean existsByNameAndCategoryIdAndIdNot(String name, Long category_id, Long id);
}
