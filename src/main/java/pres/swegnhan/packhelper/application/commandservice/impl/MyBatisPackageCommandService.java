package pres.swegnhan.packhelper.application.commandservice.impl;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pres.swegnhan.packhelper.application.commandservice.PackageCommandService;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.PackageCommandItem;
import pres.swegnhan.packhelper.core.SupportSystem;
import pres.swegnhan.packhelper.infrastructure.CategoryDictionary;
import pres.swegnhan.packhelper.infrastructure.commandrepository.PackageCommandRepository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class MyBatisPackageCommandService implements PackageCommandService {

    @Value("${pers.swegnhan.packhelper.debhubpath}")
    private String PACK_HUB_PATH;

    @Value("${pres.swegnhan.packhelper.tempdirpath}")
    private String TEMP_DIR_PATH;

    @Autowired
    private PackageCommandRepository packageRepository;

    @Autowired
    private CategoryDictionary categoryDictionary;

    @Override
    @Transactional
    public void create(PackageCommandItem pci) throws RuntimeException {
        Package pack = new Package();
        pack.setUid(UUID.randomUUID().toString());
        pack.setName(pci.getName());
        pack.setVersion(pci.getVersion());
        pack.setCategory(categoryDictionary.name2id(pci.getCategory()));
        pack.setDescription(pci.getDescription());
        pack.setSupsList(Arrays.asList(pci.getSupsList()));
        pack.setFiletype(pci.getFiletype());
        if(packageRepository.findByNameVersion(pack.getName(), pack.getVersion()) != null)
            throw new RuntimeException();
        pack.setUrl(PACK_HUB_PATH + '/' + pci.getPackFileName());
        packageRepository.insert(pack);
        for(SupportSystem sups : pack.getSupsList()) {
            if(packageRepository.hasSupportSystem(sups))
                packageRepository.insertPackSupsRelation(pack.getUid(), sups.getUid());
        }
        try {
            FileUtils.copyFileToDirectory(new File(TEMP_DIR_PATH + '/' + pci.getPackFileName()), new File(PACK_HUB_PATH));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        FileUtils.deleteQuietly(new File(TEMP_DIR_PATH + '/' + pci.getPackFileName()));
    }

    @Override
    @Transactional
    public void update(Package pack, String tempFileName) throws RuntimeException {
        Package updatePack = packageRepository.findByUid(pack.getUid());
        if (updatePack == null)
            throw new RuntimeException();
        updatePack.setUrl(PACK_HUB_PATH + '/' + tempFileName);
        packageRepository.update(pack);
        if (tempFileName != null) {
            try {
                FileUtils.copyFileToDirectory(new File(TEMP_DIR_PATH + '/' + tempFileName), new File(PACK_HUB_PATH));
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

//    @Override
//    @Transactional
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

    @Override
    @Transactional
    public Optional<Package> findByUid(String uid) {
        Package pack = packageRepository.findByUid(uid);
        if(pack == null)
            return Optional.empty();
        return Optional.of(pack);
    }

    @Override
    @Transactional
    public Optional<Package> findByNameVersion(String name, String version) {
        Package pack = packageRepository.findByNameVersion(name, version);
        if(pack == null)
            return Optional.empty();
        return Optional.of(pack);
    }

    @Override
    @Transactional
    public void remove(String uid) throws RuntimeException {
        Package removePack = packageRepository.findByUid(uid);
        if(removePack == null)
            throw new RuntimeException();
        packageRepository.delete(removePack.getUid());
        FileUtils.deleteQuietly(new File(removePack.getUrl()));
    }

//    public static void main(String[] args){
//        String context = "Package: PackHelper";
//        Pattern namePattern = Pattern.compile("Package: ((.*))");
//        Matcher nameMatcher = namePattern.matcher(context);
//        while(nameMatcher.find()){
//            for(int i = 0; i < nameMatcher.groupCount(); i++)
//                System.out.println("[" + nameMatcher.group(i) + ']');
//        }
//    }

}

//class DebAnalysisPattern{
//
//    private static Pattern packagePattern = Pattern.compile("Package: ((.*))");
//
//    private static Pattern versionPattern = Pattern.compile("Version: ((.*))");
//
//    public static void debAnalysis(Package pack, String context) {
//        Matcher packageMatcher = packagePattern.matcher(context);
//        if (packageMatcher.find())
//            pack.setName(packageMatcher.group(1));
//        Matcher versionMatcher = versionPattern.matcher(context);
//        if (versionMatcher.find())
//            pack.setVersion(versionMatcher.group(1));
//    }
//
//}