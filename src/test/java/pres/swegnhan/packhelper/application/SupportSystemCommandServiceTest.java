package pres.swegnhan.packhelper.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pres.swegnhan.packhelper.application.commandservice.SupportSystemCommandService;
import pres.swegnhan.packhelper.core.SupportSystem;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SupportSystemCommandServiceTest {

    @Autowired
    private SupportSystemCommandService supportSystemCommandService;

    private SupportSystem sups;

    @Before
    public void setUp() throws Exception{
        sups = new SupportSystem(
                "ubuntu",
                "16.04"
        );
    }

    @Test
    public void should_create_find_remove_success() throws Exception{
        supportSystemCommandService.create(sups);
        SupportSystem supsMirror = supportSystemCommandService.findByNameVersion(sups.getName(), sups.getVersion()).get();
        assertThat(sups, equalTo(supsMirror));
        supportSystemCommandService.remove(sups.getUid());;
        assertThat(Optional.empty(), equalTo(supportSystemCommandService.findByUid(sups.getUid())));
    }

}
