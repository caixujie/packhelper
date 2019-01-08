package pres.swegnhan.packhelper.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pres.swegnhan.packhelper.application.commandservice.SupportSystemCommandService;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.application.commandservice.PackageCommandService;
import pres.swegnhan.packhelper.core.SupportSystem;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE )
public class PackageCommandServiceTest {

    @Autowired
    private PackageCommandService packageCommandService;

    @Autowired
    private SupportSystemCommandService supportSystemCommandService;

    private Package pack;

    private String packFilePath;

    @Before
    public void setUp() throws Exception{
        pack = new Package(
                "",
                "",
                "1",
                "",
                ".deb",
                new String[]{"ubuntu|14.04", "ubuntu|16.04"});
        packFilePath = "/home/swegnhan/IdeaProjects/packhelpercrash/apache2_2.4.29-1ubuntu4.5_amd64.deb";
    }

    @Test
    public void should_package_create_update_delete_success() throws Exception{
        for(SupportSystem sups : pack.getSupsList())
            supportSystemCommandService.create(sups);
        packageCommandService.create(pack, packFilePath);
        Package packMirror = packageCommandService.findByNameVersion(pack.getName(), pack.getVersion()).get();
        assertThat(pack, equalTo(packMirror));
        for(SupportSystem sups : pack.getSupsList())
            supportSystemCommandService.remove(sups.getUid());
        packageCommandService.remove(pack.getUid());
        assertThat(Optional.empty(), equalTo(packageCommandService.findByUid(pack.getUid())));
    }

    @Test
    public void for_debug() throws Exception{
        System.out.println(packageCommandService);
    }

}
