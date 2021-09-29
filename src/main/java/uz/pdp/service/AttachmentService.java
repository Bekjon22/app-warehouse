package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.model.response.ApiResponse1;

public interface AttachmentService {
    ResponseEntity<ApiResponse1<?>> uploadFile(MultipartHttpServletRequest request);
}
