package pres.swegnhan.packhelper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pres.swegnhan.packhelper.application.commandservice.PushFileService;
import pres.swegnhan.packhelper.core.PushUserPackages;

import java.util.List;

@RestController
@RequestMapping(path = "/push")

public class PushFileApi {
    @Autowired
    private PushFileService pushFileService;

//    @PostMapping(path = "/now/")
//    public ResponseEntity<?> pushNow(@RequestParam() List<PushUserPackages> pushUsers){
//        HttpStatus status;
//        try{
////            System.out.println("?????????????????"+pci);
//            pushFileService.addTask(pushUsers);
//            status = HttpStatus.OK;
//        }catch (Exception e){
//            status = HttpStatus.CONFLICT;
//
//
//        }
//        return new ResponseEntity<>(null, status);
//    }
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

}

