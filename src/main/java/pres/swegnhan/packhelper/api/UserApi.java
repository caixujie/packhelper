package pres.swegnhan.packhelper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pres.swegnhan.packhelper.application.commandservice.PackageCommandService;
import pres.swegnhan.packhelper.application.commandservice.UserService;
import pres.swegnhan.packhelper.core.PushUser;
import pres.swegnhan.packhelper.core.User;

@RestController
@RequestMapping(path = "/user")
public class UserApi {
    @Autowired
    private UserService userService;
    @PostMapping("/add")
    public ResponseEntity<?> creatUser(@RequestBody User user){

        HttpStatus status;
        try{
            System.out.println(user.toString());
            userService.create(user);
            status = HttpStatus.OK;
        }catch (Exception e){
            status = HttpStatus.CONFLICT;


        }
        return new ResponseEntity<>(null, status);
    }

}
