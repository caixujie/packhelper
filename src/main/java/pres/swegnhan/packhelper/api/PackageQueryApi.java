package pres.swegnhan.packhelper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pres.swegnhan.packhelper.application.commandservice.PackageQueryService;
import pres.swegnhan.packhelper.core.Page;
import pres.swegnhan.packhelper.core.Result;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(path = "/packages")
@RestController
public class PackageQueryApi {
    @Autowired
    private PackageQueryService packagequeryservice;

    @GetMapping(path = "/page")
    public Result queryPackages(Page page) {
        page.setRoleKeyword("undefined".equals(page.getRoleKeyword()) ? "%%" : "%"+page.getRoleKeyword()+"%");
        Result result=null;

        result=this.packagequeryservice.queryByPage(page);
        return result;
    }
    @GetMapping(path = "/all")
    public ResponseEntity<?> queryPackages(HttpServletRequest httpRequest) {
       return ResponseEntity.of(packagequeryservice.queryAll());
    }
}
