package pres.swegnhan.packhelper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pres.swegnhan.packhelper.application.commandservice.PackageUploadDownloadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequestMapping(path = "/packagefile")
@RestController
public class PackageUploadDownloadApi {
    @Value("${pres.swegnhan.packhelper.tempdirpath}")
    private String TEMP_DIR_PATH;

    @Autowired
    private PackageUploadDownloadService packageUploadService;

    @PostMapping
    public ResponseEntity<?> fileUpload(@RequestParam(value ="addPicture") MultipartFile multipartFile) throws Exception {
//      public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        System.out.println("in this");
        HttpStatus status ;
        status = HttpStatus.NOT_FOUND;
        if (multipartFile == null) {
//            System.out.println("111");
            return new ResponseEntity<>(null, status);

        }
        String filename = multipartFile.getOriginalFilename();
        String filePath = TEMP_DIR_PATH + "/" + filename;
        File file = new File(filePath);
        try {

            //.transferTo（）方法没有最大上限

            multipartFile.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.of(packageUploadService.upload(file));
    }

    @GetMapping(path = "/download/{name}")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("name") String fileName) {
        return packageUploadService.download(request,response,fileName);
    }
}
