package pres.swegnhan.packhelper.infrastructure.commandrepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.SupportSystem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@MybatisTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE )
public class PackageRepositoryTest {

    @Autowired
    private PackageRepository packageRepository;

    private Package pack;

    private SupportSystem supportSystem;

    @Before
    public void setUp() throws Exception{
        pack = new Package(
                "openjdk-8-jre-headless",
                "8u171-b11-0ubuntu0.18.04.1",
                "1",
                "usr/bin/packhelper/openjdk-8-jre-headless_8u171-b11-0ubuntu0.18.04.1_amd64.deb",
                ".deb",
                new String[]{"ubuntu|14.04", "ubuntu|16.04"});
        supportSystem = new SupportSystem(
                "ubuntu",
                "14.04");
    }

    @Test
    public void should_supportsystem_insert_select_success() throws Exception{
        packageRepository.insertSupportSystem(supportSystem);
        assertThat(packageRepository.findSupportSystem(supportSystem), is(true));
    }

    @Test
    public void should_package_insert_selec_success() throws Exception{
        packageRepository.insert(pack);
        assertThat(pack, equalTo(packageRepository.findByUid(pack.getUid())));
    }

    @Test
    public void should_package_update_success() throws Exception{
        packageRepository.insert(pack);
        pack.setName("openjdk-11-jdk");
        pack.setVersion("10.0.2+13-1ubuntu0.18.04.2");
        pack.setCategory(2);
        pack.setUrl("usr/bin/packhelper/openjdk-11-jdk_10.0.2+13-1ubuntu0.18.04.2_amd64.rpm");
        pack.setFiletype(".rpm");
        packageRepository.update(pack);
        assertThat(pack, equalTo(packageRepository.findByUid(pack.getUid())));
    }

    @Test
    public void should_package_insert_select_delete_update_success() throws Exception{
        packageRepository.insert(pack);
        packageRepository.delete(pack.getUid());
        assertThat(packageRepository.findByUid(pack.getUid()), is(nullValue()));
    }

    @Test
    public void should_packge_supportsystem_relation_insert_success() throws Exception{
        packageRepository.insert(pack);
        for(SupportSystem sups : pack.getSupsList()){
            if(!packageRepository.findSupportSystem(sups))
                packageRepository.insertSupportSystem(sups);
            packageRepository.insertPackSupsRelation(pack.getUid(), sups.getUid());
        }
        assertThat(packageRepository.findByNameVersion(pack.getName(), pack.getVersion()), equalTo(pack));
    }

}
