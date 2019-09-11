package pres.swegnhan.packhelper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pres.swegnhan.packhelper.application.commandservice.PushFileService;
import pres.swegnhan.packhelper.core.PushUserPackages;

import java.util.List;

@RestController
@RequestMapping(path = "/push")

public class PushFileApi {
    @Autowired
    private PushFileService pushFileService;

    @PostMapping(path = "/now/")
    public ResponseEntity<?> pushNow(@RequestParam(value ="pushfile") MultipartFile multipartFile,@RequestParam(value = "pushWhere",required=true) List<String> pushWhere,String desc,String taskName){
        HttpStatus status;
        String fileName = multipartFile.getOriginalFilename();
        try{
            System.out.println(fileName);
            pushFileService.addTask(fileName,pushWhere,desc,taskName);
            status = HttpStatus.OK;
        }catch (Exception e){
            status = HttpStatus.CONFLICT;
            e.printStackTrace();


        }
        return new ResponseEntity<>(null, status);
    }
    @GetMapping(path = "/nowtest/")
    public ResponseEntity<?> pushNow1(){
        HttpStatus status;
        try{
            System.out.println("?????????????????");
            pushFileService.addTaskTest();
            status = HttpStatus.OK;
        }catch (Exception e){
            status = HttpStatus.CONFLICT;


        }
        return new ResponseEntity<>(null, status);
    }
    @PostMapping(value = "/online/")
    public ResponseEntity<?> onlineUser(@RequestBody String mac){
        System.out.println(mac);
        pushFileService.onlineAddTask(mac);
        HttpStatus status;
        status = HttpStatus.OK;
        return new ResponseEntity<>(null, status);
    };
}

