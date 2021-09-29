package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.model.response.ApiResponse1;
import uz.pdp.service.AttachmentService;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;
    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse1<?>> upload(MultipartHttpServletRequest request) {
        return attachmentService.uploadFile(request);
    }
}
