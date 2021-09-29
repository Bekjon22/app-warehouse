package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Attachment;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment,Long> {
}
