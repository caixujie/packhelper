package pres.swegnhan.packhelper.application.commandservice;

import org.springframework.http.ResponseEntity;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.Page;
import pres.swegnhan.packhelper.core.Result;

import java.util.List;
import java.util.Optional;

public interface PackageQueryService {
   public Result queryByPage(Page page);


   public Optional<List<Package>> queryAll();


}
