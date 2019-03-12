package pres.swegnhan.packhelper.application.commandservice;

import pres.swegnhan.packhelper.core.Package;

import java.io.File;
import java.util.Optional;

public interface PackageUploadService {
    public Optional<Package> upload(File file);



}
