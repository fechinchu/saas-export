package com.fechin.web.controller.module;

import com.fechin.web.controller.BaseController;
import com.fechin.domain.module.Module;
import com.fechin.service.module.ModuleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path="/system/module")
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;

    @RequestMapping(path="/list",name="查询所有模块")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size, Model model){
        PageInfo<Module> pageInfo = moduleService.findByPage(page,size);
        model.addAttribute("page",pageInfo);
        return "system/module/module-list";
    }

    @RequestMapping(path="/toUpdate",name="跳转到模块修改页面")
    public String toUpdate(String id,Model model){
        Module module = moduleService.findByOne(id);
        model.addAttribute("module",module);

        List<Module> menus = moduleService.findAll();
        model.addAttribute("menus",menus);

        return "system/module/module-update";
    }

    ///system/module/edit.do
    @RequestMapping(path="/edit",name="修改模块或添加模块")
    public String edit(Module module){
        //在添加之前,需要加上companyId和companyName
        if(StringUtils.isEmpty(module.getId())){
            moduleService.add(module);
            return "redirect:/system/module/list.do";
        }else{
            //修改操作
            moduleService.update(module);
            return "redirect:/system/module/list.do";
        }
    }

    @RequestMapping(path="/toAdd",name="跳转到模块添加页面")
    public String toAdd(Model model){
        List<Module> menus = moduleService.findAll();
        model.addAttribute("menus",menus);
        return "system/module/module-update";
    }

    @RequestMapping(path="/delete",name="删除模块")
    public String delete(String id){
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }
}
