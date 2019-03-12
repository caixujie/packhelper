//package pres.swegnhan.packhelper.api;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import pres.swegnhan.packhelper.application.commandservice.PackageUploadService;
//
//import java.io.File;
//import java.io.IOException;
//
//@RequestMapping(path = "/packagefile")
//@RestController
//public class PackageUploadApi {
//    @Value("${pres.swegnhan.packhelper.tempdirpath}")
//    private String TEMP_DIR_PATH;
//
//    @Autowired
//    private PackageUploadService packageUploadService;
//
//    public ResponseEntity<?> fileUpload(@RequestBody MultipartFile multipartFile){
//        HttpStatus status ;
//        status = HttpStatus.NOT_FOUND;
//        if (multipartFile == null) {
//            return new ResponseEntity<>(null, status);
//        }
//        String filename = multipartFile.getOriginalFilename();
//        String filePath = TEMP_DIR_PATH + "/" + filename;
//        File file = new File(filePath);
//        try {
//
//            //.transferTo（）方法最大上限不超过2GB
//
//
//
//            multipartFile.transferTo(file);
//        } catch (IllegalStateException | IOException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.of(packageUploadService.upload(file));
//    }
//}
