package pres.swegnhan.packhelper.infrastructure.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pres.swegnhan.packhelper.core.packpackage.Package;
import pres.swegnhan.packhelper.core.packpackage.PackageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE )
public class PackageRepositoryTest {

    @Autowired
    private PackageRepository packageRepository;

    private Package pack;

    @Before
    public void setUp() throws Exception{
        pack = new Package(
                "",
                "",
                "1",
                "",
                ".deb",
                new String[]{"ubuntu|14.04", "ubuntu|16.04"});
    }

    @Test
    public void for_debug() throws Exception{
        packageRepository.save(pack, "/home/swegnhan/IdeaProjects/packhelpercrash/apache2_2.4.29-1ubuntu4.5_amd64.deb");
    }

}
