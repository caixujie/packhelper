package pres.swegnhan.packhelper.infrastructure;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pres.swegnhan.packhelper.core.Category;

import java.util.List;

@Mapper
@Repository
public interface CategoryRepository {

    public List<Category> getCategoryList();

}
