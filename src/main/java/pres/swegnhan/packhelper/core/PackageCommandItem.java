package pres.swegnhan.packhelper.core;

import lombok.Data;

import java.util.List;

@Data
public class PackageCommandItem {

    private String uid;

    private String name;

    private String version;

    private String category;

    private String url;

    private String filetype;

    private SupportSystem[] supsList;

    private String packFileName;

}
