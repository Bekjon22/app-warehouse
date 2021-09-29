package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByPhoneNumber(String phoneNumber);




}
