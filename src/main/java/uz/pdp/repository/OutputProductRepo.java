package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.InputProduct;
import uz.pdp.domain.OutputProduct;
import uz.pdp.model.response.ProductReport;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OutputProductRepo extends JpaRepository<OutputProduct,Long> {

}
