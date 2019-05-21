package pres.swegnhan.packhelper.application.commandservice;

import pres.swegnhan.packhelper.core.Package;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public interface PackageUploadDownloadService {
    public Optional<Package> upload(File file) throws Exception;
    public String download(HttpServletRequest request, HttpServletResponse response,String fileName);


}
