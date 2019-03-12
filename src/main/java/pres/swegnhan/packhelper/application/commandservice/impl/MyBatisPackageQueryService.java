package pres.swegnhan.packhelper.application.commandservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pres.swegnhan.packhelper.application.commandservice.PackageQueryService;
import pres.swegnhan.packhelper.core.Page;
import pres.swegnhan.packhelper.core.Result;
import pres.swegnhan.packhelper.infrastructure.queryrepository.PackageQueryRepository;
import pres.swegnhan.packhelper.uitil.PageUtil;

@Service
public class MyBatisPackageQueryService implements PackageQueryService {
    @Autowired
    private PageUtil pageUtil;
    @Autowired
    private PackageQueryRepository  packageQueryRepository;

    @Override
    public Result queryByPage(Page page) {
        page.setPageSize(pageUtil.getPageSize());
        int totalCount=this.packageQueryRepository.getCount(page);
        page.setTotalCount(totalCount);

        int totalPage= page.getTotalPage();

        //处理前一页
        if(page.getCurrentPage()==1){
            page.setPreviousPage(1);
        }else{
            page.setPreviousPage(page.getCurrentPage()-1);
        }
        //处理后一页
        if(page.getCurrentPage()==totalPage){
            page.setNextPage(totalPage);
        }else{
            page.setNextPage(page.getCurrentPage()+1);
        }
        //获取超链接的个数
        page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(), page.getPageSize(), totalCount, totalPage));

        page.setData(this.packageQueryRepository.getPackage(page));
        Result result=new Result();
        if(page.getData()!=null){
            result.setStatus(0);
            result.setData(page);
        }else{
            result.setStatus(1);
            result.setMessage("没有角色信息");
        }

        return result;
        }
}
