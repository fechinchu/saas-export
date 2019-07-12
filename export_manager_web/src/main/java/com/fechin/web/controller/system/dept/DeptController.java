package com.fechin.web.controller.system.dept;

import com.fechin.web.controller.BaseController;
import com.fechin.domain.system.dept.Dept;
import com.fechin.service.system.dept.DeptService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {
    @Autowired
    private DeptService deptService;

    @RequestMapping(path="/list",name = "查询所有的部门")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size, Model model){
        PageInfo<Dept> pageInfo = deptService.findByPage(page,size,companyId);
        model.addAttribute("page",pageInfo);
        return "system/dept/dept-list";
    }

    @RequestMapping(path="/toUpdate",name="跳转到更新部门页面")
    public String toUpdate(String id,Model model){
        //根据部门ID和公司id去查
        Dept dept = deptService.findByOne(id,companyId);
        model.addAttribute("dept",dept);
        //查询所有的部门
        List<Dept> deptList = deptService.findAll(companyId);
        model.addAttribute("deptList",deptList);
        return "system/dept/dept-update";
    }

    ///system/dept/edit.do
    @RequestMapping(path="/edit",name="修改部门或更新部门完成")
    public String edit(Dept dept){
        //在添加之前,需要加上companyId和companyName
        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);
        if(StringUtils.isEmpty(dept.getDeptId())){
            deptService.add(dept);
            return "redirect:/system/dept/list.do";
        }else{
            //修改操作
            deptService.update(dept);
            return "redirect:/system/dept/list.do";
        }
    }

    @RequestMapping(path="/toAdd",name="跳转到添加部门页面")
    public String toAdd(Model model){
        //查询所有部门
        List<Dept> deptList = deptService.findAll(companyId);
        model.addAttribute("deptList",deptList);
        //继续跳转update页面,update页面和add页面的效果是一样的
        return "system/dept/dept-update";
    }

    @RequestMapping(path="/delete",name="删除部门")
    public String delete(String id){
        deptService.delete(id,companyId);
        return "redirect:/system/dept/list.do";
    }


}
