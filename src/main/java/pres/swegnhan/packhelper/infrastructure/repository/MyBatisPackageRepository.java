package pres.swegnhan.packhelper.infrastructure.repository;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pres.swegnhan.packhelper.core.packpackage.Package;
import pres.swegnhan.packhelper.core.packpackage.PackageRepository;
import pres.swegnhan.packhelper.core.packpackage.SupportSystem;
import pres.swegnhan.packhelper.infrastructure.mybatis.mapper.PackageMapper;

import java.io.File;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class MyBatisPackageRepository implements PackageRepository {

    @Value("${pers.swegnhan.packhelper.debhubpath}")
    private String DEB_HUB_PATH;

    @Value("${pres.swegnhan.packhelper.tempdirpath}")
    private String TEMP_DIR_PATH;

    @Value("${pres.swegnhan.packhelper.debunzipshellpath}")
    private String DEB_UNZIP_SHELL_PATH;

    @Autowired
    public PackageMapper packageMapper;

    @Override
    @Transactional
    public void save(Package pack, String tempFilePath) throws Exception{
        String tempThreadFolder = UUID.randomUUID().toString();
        try {
            Process unzipProcess = Runtime.getRuntime().exec(DEB_UNZIP_SHELL_PATH + " " + tempFilePath + " " + tempThreadFolder);
            unzipProcess.waitFor();
            String controlFilePath = TEMP_DIR_PATH + '/' + tempThreadFolder + "/DEBIAN/control";
            String controlFileContext = FileUtils.readFileToString(new File(controlFilePath), "UTF-8");
            DebAnalysisPattern.debAnalysis(pack, controlFileContext);
            packageMapper.insert(pack);
            for(SupportSystem sups : pack.getSupsList()) {
                if(packageMapper.findSupportSystem(sups))
                    packageMapper.insertPackSupsRelation(pack.getUid(), sups.getUid());
            }
            FileUtils.copyFileToDirectory(new File(tempFilePath), new File(DEB_HUB_PATH));
        }catch (Exception e){ throw e; }
        finally {
            File tempDir = new File(TEMP_DIR_PATH + '/' + tempThreadFolder);
            if(tempDir.exists() && tempDir.isDirectory())
                FileUtils.deleteDirectory(tempDir);
        }
        
    }

    @Override
    @Transactional
    public Optional<Package> findById(String id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public void remove(Package pack) {

    }

    public static void main(String[] args){
        String context = "Package: PackHelper";
        Pattern namePattern = Pattern.compile("Package: ((.*))");
        Matcher nameMatcher = namePattern.matcher(context);
        while(nameMatcher.find()){
            for(int i = 0; i < nameMatcher.groupCount(); i++)
                System.out.println("[" + nameMatcher.group(i) + ']');
        }
    }

}

class DebAnalysisPattern{

    private static Pattern packagePattern = Pattern.compile("Package: ((.*))");

    private static Pattern versionPattern = Pattern.compile("Version: ((.*))");

    public static void debAnalysis(Package pack, String context) {
        Matcher packageMatcher = packagePattern.matcher(context);
        if (packageMatcher.find())
            pack.setName(packageMatcher.group(1));
        Matcher versionMatcher = versionPattern.matcher(context);
        if (versionMatcher.find())
            pack.setVersion(versionMatcher.group(1));
    }

}