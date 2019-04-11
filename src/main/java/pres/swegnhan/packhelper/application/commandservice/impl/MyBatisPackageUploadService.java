package pres.swegnhan.packhelper.application.commandservice.impl;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pres.swegnhan.packhelper.application.commandservice.PackageUploadService;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.SupportSystem;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MyBatisPackageUploadService implements PackageUploadService {
    @Value("${pres.swegnhan.packhelper.debunzipshellpath}")
    private String DEB_UNZIP_SHELL_PATH;
    @Value("${pres.swegnhan.packhelper.tempdirpath}")
    private String TEMP_DIR_PATH;
    @Override
    public  Optional<Package> upload(File file) throws Exception {
        String tempThreadFolder = UUID.randomUUID().toString();

        Process unzipProcess = Runtime.getRuntime().exec(DEB_UNZIP_SHELL_PATH+" "+file.getPath()+" "+tempThreadFolder);
        unzipProcess.waitFor();

        String controlFilePath = TEMP_DIR_PATH+ '/' + tempThreadFolder + "/DEBIAN/control";
        String controlFileContext = FileUtils.readFileToString(new File(controlFilePath), "UTF-8");

        Package pack= new Package();
        DebAnalysisPattern.debAnalysis(pack, controlFileContext);
        return Optional.ofNullable(pack);
    }
//    public void saveWithAnalysis(Package pack, String tempFilePath) throws Exception{
//        String tempThreadFolder = UUID.randomUUID().toString();
//        try {
//            Process unzipProcess = Runtime.getRuntime().exec(DEB_UNZIP_SHELL_PATH + " " + tempFilePath + " " + tempThreadFolder);
//            unzipProcess.waitFor();
//            String controlFilePath =  ;
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
    static class DebAnalysisPattern{
//Package is name
    private static Pattern packagePattern = Pattern.compile("Package: ((.*))");

    private static Pattern versionPattern = Pattern.compile("Version: ((.*))");

    private static Pattern descriptionPattern = Pattern.compile("Description: (.*\n( .*\n)*)");

    public  static void debAnalysis(Package pack, String context) {
        System.out.println(context);
        Matcher packageMatcher = packagePattern.matcher(context);
        if (packageMatcher.find())
            pack.setName(packageMatcher.group(1));
        Matcher versionMatcher = versionPattern.matcher(context);
        if (versionMatcher.find())
            pack.setVersion(versionMatcher.group(1));
         Matcher descriptionPatternMatcher = descriptionPattern.matcher(context);
//        if (descriptionPatternMatcher.find())
//            pack.setDescription(descriptionPatternMatcher.group(2));
        System.out.println("===============test=================");
        if(descriptionPatternMatcher.find()) {
            pack.setDescription(descriptionPatternMatcher.group(1));
            for (int i = 0; i < descriptionPatternMatcher.groupCount(); i++)
                System.out.println("Group(" + i + "):\n" + descriptionPatternMatcher.group(i));
        }
        System.out.println("===============test=================");
    }

}
}
