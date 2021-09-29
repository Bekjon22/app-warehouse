package uz.pdp.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.domain.Attachment;
import uz.pdp.domain.AttachmentContent;
import uz.pdp.model.response.ApiResponse1;
import uz.pdp.repository.AttachmentContentRepo;
import uz.pdp.repository.AttachmentRepo;
import uz.pdp.service.AttachmentService;

import java.util.Iterator;
import java.util.Objects;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepo attachmentRepo;
    private final AttachmentContentRepo attachmentContentRepo;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepo attachmentRepo, AttachmentContentRepo attachmentContentRepo) {
        this.attachmentRepo = attachmentRepo;
        this.attachmentContentRepo = attachmentContentRepo;
    }

    @SneakyThrows
    @Override
    public ResponseEntity<ApiResponse1<?>> uploadFile(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (!Objects.requireNonNull(file).isEmpty()) {
            Attachment attachment = new Attachment();

            attachment.setName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());
            Attachment savedAttachment = attachmentRepo.save(attachment);

            //
            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);
            attachmentContentRepo.save(attachmentContent);
            return ResponseEntity.ok(new ApiResponse1<>(savedAttachment.getId(), true));
        }
        return ResponseEntity.ok(new ApiResponse1<>("File not saved",false));

    }
}
