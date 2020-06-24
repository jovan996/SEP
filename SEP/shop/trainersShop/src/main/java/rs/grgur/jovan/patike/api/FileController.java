package rs.grgur.jovan.patike.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.http.HttpStatus;
import rs.grgur.jovan.patike.service.FileStorageService;

@RestController()
@CrossOrigin
@RequestMapping(value = "/api/files")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/user/admin/upload/slike/{id}")
    public ResponseEntity<Void> uploadFile(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file, id + ".jpg");
        if (fileName.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/download/slike/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
            }

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException ex) {
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }
    }
}
