package com.fechin.web.controller.system.role;

import com.fechin.web.controller.BaseController;
import com.fechin.domain.system.role.Role;
import com.fechin.service.system.role.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(path="/list",name="查看角色")
    public String list(Model model ,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size) {
        PageInfo<Role> pageInfo = roleService.findAll(companyId,page,size);
        model.addAttribute("page",pageInfo);
        return "system/role/role-list";
    }


    @RequestMapping(path="/toUpdate",name = "跳转到角色更新页面")
    public String toUpdate(String id,Model model){
        Role role = roleService.findByOne(companyId,id);
        model.addAttribute("role",role);

        List<Role> menus = roleService.findAllWithOutPage(companyId);
        model.addAttribute("menus",menus);

        return "system/role/role-update";
    }

    ///system/role/edit.do
    @RequestMapping(path="/edit",name="更新或添加角色完成")
    public String edit(Role role){
        //在添加之前,需要加上companyId和companyName
        role.setCompanyId(companyId);
        role.setCompanyName(companyName);
        if(StringUtils.isEmpty(role.getId())){
            roleService.add(role);
            return "redirect:/system/role/list.do";
        }else{
            //修改操作
            roleService.update(role);
            return "redirect:/system/role/list.do";
        }
    }

    @RequestMapping(path="/toAdd",name="跳转到角色添加页面")
    public String toAdd(Model model){
        List<Role> menus = roleService.findAllWithOutPage(companyId);
        model.addAttribute("menus",menus);
        return "system/role/role-update";
    }

    @RequestMapping(path="/delete",name="删除角色")
    public String delete(String id){
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }

    @RequestMapping(path= "/roleModule",name="查看角色拥有模块")
    public String roleModule(String roleid,Model model){
        Role role = roleService.findByOne(companyId, roleid);
        model.addAttribute("role",role);
        return "system/role/role-module";
    }
    @ResponseBody
    @RequestMapping(path="/getZtreeNodes",name="查看角色拥有模块")
    public List getZtreeNodes (String roleid){
        //明确请求参数和返回值类型
        return roleService.findModuleByRoleForAjax(roleid, companyId);
    }

    @RequestMapping(path="/updateRoleModule",name="更新角色模块")
    public String updateRoleModule(String roleid,String moduleIds){
        roleService.updateRoleModule(roleid,moduleIds);
        return "redirect:/system/role/list.do";
    }


}
