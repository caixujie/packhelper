package pres.swegnhan.packhelper.infrastructure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE )
public class CategoryDictionaryTest {

    @Autowired
    private CategoryDictionary categoryDictionary;

    @Test
    @Rollback
    public void should_dictionary_map_success() throws Exception{
        assertThat(categoryDictionary.id2name(1), equalTo("office"));
        assertThat(categoryDictionary.name2id("office"), equalTo(1));
    }

}
