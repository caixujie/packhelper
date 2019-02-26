package pres.swegnhan.packhelper.core;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PackageCommandItem {

    private String uid;

    private String name;

    private String version;

    private String category;

    private String description;

    private String url;

    private String filetype;

    private SupportSystem[] supsList;

    private String packFileName;

    public PackageCommandItem(String uid, String name, String version, String category, String description, String url, String filetype, SupportSystem[] supsList, String packFileName){
        this.uid = uid;
        this.name = name;
        this.version = version;
        this.category = category;
        this.description = description;
        this.url = url;
        this.filetype = filetype;
        this.supsList = supsList.clone();
        this.packFileName = packFileName;
    }

}
