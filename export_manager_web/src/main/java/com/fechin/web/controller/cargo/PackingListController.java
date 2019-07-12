package com.fechin.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fechin.domain.cargo.PackingList;
import com.fechin.domain.cargo.PackingListExample;
import com.fechin.domain.export.Export;
import com.fechin.service.export.ExportService;
import com.fechin.service.packing.PackingListService;
import com.fechin.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/cargo/packing")
public class PackingListController extends BaseController {
    @Autowired
    private PackingListService packingListService;
    @Reference
    private ExportService exportService;
    @RequestMapping(path = "/packing",name="装箱")
    public String packing(String id,Model model){
        PackingList packingList = new PackingList();
        packingList.setCreateBy(userName);
        packingList.setCreateDept(deptId);
        packingList.setCreateTime(new Date());
        packingList.setCompanyId(companyId);
        packingList.setCompanyName(companyName);
        packingList.setExportIds(id);
        packingList.setState(0L);
        packingList.setPackingListId(UUID.randomUUID().toString().replace("-",""));
        packingListService.packing(packingList);
        //对export中的数据状态进行改变,方便封锁.
        String[] strings = id.split(",");
        for (String string : strings) {
            Export export = new Export();
            export.setId(string);
            export.setState(3L);
            exportService.update(export);
        }
        model.addAttribute("packing",packingList);
        return "cargo/packing/packing-toPacking";
    }

    @RequestMapping(path="/list",name = "查看装箱单")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size,
                       Model model){
        PackingListExample packingListExample = new PackingListExample();
        PackingListExample.Criteria criteria = packingListExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo<PackingList> pageInfo  = packingListService.findAllOnPage(page,size,packingListExample);
        model.addAttribute("page",pageInfo);
        return "cargo/packing/packing-list";
    }

    @RequestMapping(path = "/edit",name = "修改装箱单")
    public String edit(PackingList packingList){
        packingListService.update(packingList);
        return "redirect:/cargo/packing/list.do";

    }

    @RequestMapping(path="/toUpdate",name="跳往修改装箱单页面")
    public String toView(String id,Model model){
        PackingList packingList = packingListService.findById(id);
        model.addAttribute("packing",packingList);
        return "cargo/packing/packing-update";
    }

    @RequestMapping(path = "/delete",name = "删除")
    public String delete(String id){
        packingListService.delete(id);
        return "redirect:/cargo/packing/list.do";
    }

    @RequestMapping(path="/submit",name = "提交")
    public String submit(String id){
        packingListService.submit(id);
        return "redirect:/cargo/packing/list.do";
    }

    @RequestMapping(path="cancel",name="取消")
    public String cancel(String id){
        packingListService.cancel(id);
        return "redirect:/cargo/packing/list.do";
    }


}
