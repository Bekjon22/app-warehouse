package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.AttachmentContent;

@Repository
public interface AttachmentContentRepo extends JpaRepository<AttachmentContent,Long> {
}
