//package pres.swegnhan.packhelper.application.commandservice.impl;
//
//import org.springframework.stereotype.Service;
//import pres.swegnhan.packhelper.application.commandservice.PackageUploadService;
//import pres.swegnhan.packhelper.core.Package;
//
//import java.io.File;
//import java.util.Optional;
//
//@Service
//public class MyBatisPackageUploadService implements PackageUploadService {
//
//
//    @Override
//    public  Optional<Package> upload(File file) {
//
//        return Optional.empty();
//    }
//    public void saveWithAnalysis(Package pack, String tempFilePath) throws RuntimeException{
//        String tempThreadFolder = UUID.randomUUID().toString();
//        try {
//            Process unzipProcess = Runtime.getRuntime().exec(DEB_UNZIP_SHELL_PATH + " " + tempFilePath + " " + tempThreadFolder);
//            unzipProcess.waitFor();
//            String controlFilePath = TEMP_DIR_PATH + '/' + tempThreadFolder + "/DEBIAN/control";
//            String controlFileContext = FileUtils.readFileToString(new File(controlFilePath), "UTF-8");
//            DebAnalysisPattern.debAnalysis(pack, controlFileContext);
//            packageMapper.insert(pack);
//            for(SupportSystem sups : pack.getSupsList()) {
//                if(packageMapper.findSupportSystem(sups))
//                    packageMapper.insertPackSupsRelation(pack.getUid(), sups.getUid());
//            }
//            FileUtils.copyFileToDirectory(new File(tempFilePath), new File(DEB_HUB_PATH));
//        }catch (RuntimeException e){ throw e; }
//        finally {
//            File tempDir = new File(TEMP_DIR_PATH + '/' + tempThreadFolder);
//            if(tempDir.exists() && tempDir.isDirectory())
//                FileUtils.deleteDirectory(tempDir);
//        }
//    }
//}
