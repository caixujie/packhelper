package pres.swegnhan.packhelper.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pres.swegnhan.packhelper.core.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoryDictionary {

    private Map<Integer, String> id2nameMap;

    private Map<String, Integer> name2idMap;

    @Autowired
    public CategoryDictionary(CategoryRepository categoryRepository){
        List<Category> categories = categoryRepository.getCategoryList();
        id2nameMap = new HashMap<>();
        name2idMap = new HashMap<>();
        for(Category ctgr : categories){
            id2nameMap.put(ctgr.getId(), ctgr.getName());
            name2idMap.put(ctgr.getName(), ctgr.getId());
        }
    }

    public String id2name(int id){
        return id2nameMap.get(id);
    }

    public int name2id(String name){
        return name2idMap.get(name);
    }


}
