package pres.swegnhan.packhelper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pres.swegnhan.packhelper.application.commandservice.PackageCommandService;
import pres.swegnhan.packhelper.core.Package;

@RestController
@RequestMapping(path = "/package")
public class PackageApi {

    @Autowired
    private PackageCommandService packageCommandService;

    @PostMapping
    public ResponseEntity<?> createPackage(Package pack, String filename){
        HttpStatus status;
        try{
            packageCommandService.create(pack, filename);
            status = HttpStatus.OK;
        }catch (Exception e){
            status = HttpStatus.CONFLICT;
        }
        return new ResponseEntity<>(null, status);
    }
    
    @DeleteMapping
    public ResponseEntity<?> removePackage(String packUid){
        HttpStatus status;
        try{
            packageCommandService.remove(packUid);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.CONFLICT;
        }
        return new ResponseEntity<>(null, status);
    }

    @PutMapping
    public ResponseEntity<?> updatePackage(Package pack, String filename){
        HttpStatus status;
        try{
            packageCommandService.update(pack, filename);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.CONFLICT;
        }
        return new ResponseEntity<>(null, status);
    }

    @GetMapping
    public ResponseEntity<?> findPackageByNameVersion(String name, String version){
        return ResponseEntity.of(packageCommandService.findByNameVersion(name, version));
    }

}
