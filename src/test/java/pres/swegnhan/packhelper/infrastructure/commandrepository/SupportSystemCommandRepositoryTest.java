package pres.swegnhan.packhelper.infrastructure.commandrepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;
import pres.swegnhan.packhelper.core.SupportSystem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@MybatisTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SupportSystemCommandRepositoryTest {

    @Autowired
    private SupportSystemCommandRepository supportSystemCommandRepository;

    private SupportSystem sups;

    @Before
    public void setUp() throws Exception{
        sups = new SupportSystem(
                "ubuntu",
                "14.04"
        );
    }

    @Test
    public void should_supportsystem_insert_select_success() throws Exception{
        supportSystemCommandRepository.insert(sups);
        assertThat(sups, equalTo(supportSystemCommandRepository.findByUid(sups.getUid())));
    }

    @Test
    public void should_supportsystem_delete_success() throws Exception{
        supportSystemCommandRepository.insert(sups);
        supportSystemCommandRepository.delete(sups.getUid());
        assertThat(supportSystemCommandRepository.findByNameVersion(sups.getName(), sups.getVersion()), is(nullValue()));
    }

}
