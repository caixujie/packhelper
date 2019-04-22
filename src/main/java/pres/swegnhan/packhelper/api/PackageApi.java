package pres.swegnhan.packhelper.api;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pres.swegnhan.packhelper.application.commandservice.PackageCommandService;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.PackageCommandItem;
import pres.swegnhan.packhelper.core.SupportSystem;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/package")
public class PackageApi {

    @Autowired
    private PackageCommandService packageCommandService;

    @PostMapping
    public ResponseEntity<?> createPackage(@RequestBody PackageCommandItem pci){

        HttpStatus status;
        try{
            System.out.println("?????????????????"+pci);
            packageCommandService.create(pci);
            status = HttpStatus.OK;
        }catch (Exception e){
            status = HttpStatus.CONFLICT;


        }
        return new ResponseEntity<>(null, status);
    }
    
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> removePackage(@PathVariable("id") String packUid){
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