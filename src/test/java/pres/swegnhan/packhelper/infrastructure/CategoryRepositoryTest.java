package pres.swegnhan.packhelper.infrastructure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;
import pres.swegnhan.packhelper.core.Category;
import pres.swegnhan.packhelper.infrastructure.CategoryRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@MybatisTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE )
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void should_dictionary_get_success() throws Exception{
        List<Category> list = categoryRepository.getCategoryList();
        assertThat(list.get(0).getName(), equalTo("office"));
    }

}
