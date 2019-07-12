package com.fechin.web.controller.system.user;

import com.fechin.web.controller.BaseController;
import com.fechin.domain.system.dept.Dept;
import com.fechin.domain.system.role.Role;
import com.fechin.domain.system.user.User;
import com.fechin.service.system.dept.DeptService;
import com.fechin.service.system.role.RoleService;
import com.fechin.service.system.user.UserService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(path="/list",name="查看用户")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size, Model model) {
        PageInfo<User> pageInfo = userService.findByPage(companyId, page, size);
        model.addAttribute("page", pageInfo);
        return "/system/user/user-list";
    }

    @RequestMapping(path="/toUpdate",name="跳转到更新用户界面")
    public String toUpdate(String id, Model model) {
        //根据id查找员工信息.然后回显数据
        User user = userService.findById(companyId, id);
        model.addAttribute("user", user);
        //查询所有部门
        List<Dept> deptList = deptService.findAll(companyId);
        model.addAttribute("deptList", deptList);
        return "/system/user/user-update";
    }

    @RequestMapping(path="/edit",name="修改或添加完成用户")
    public String edit(User user) {
        //如果id为空证明是添加,否则修改
        System.out.println(user);
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        if (StringUtils.isEmpty(user.getUserId())) {
            userService.add(user);
            return "redirect:/system/user/list.do";
        } else {
            userService.update(user);
            return "redirect:/system/user/list.do";
        }
    }

    @RequestMapping(path="/toAdd",name="跳转到用户添加页面")
    public String toAdd(Model model) {
        //查询所有部门
        List<Dept> deptList = deptService.findAll(companyId);
        model.addAttribute("deptList", deptList);
        return "/system/user/user-update";
    }

    @RequestMapping(path="/delete",name="删除用户")
    public String delete(String id) {
        userService.delete(id, companyId);
        return "redirect:/system/user/list.do";
    }

    @RequestMapping(path="/roleList",name="查看用户所拥有的角色")
    public String roleList(String id,Model model){
        //去数据库查询一共有多少个角色
        List<Role> roleList = roleService.findAllWithOutPage(companyId);
        model.addAttribute("roleList",roleList);

        String userRoleStr = roleService.findRoleWithUser(id, companyId);
        model.addAttribute("userRoleStr",userRoleStr);

        User user = userService.findById(companyId, id);
        model.addAttribute("user",user);
        return "system/user/user-role";
    }


    @RequestMapping(path="/changeRole",name="改变用户所拥有的角色")
    public String changeRole(String userId,String oldRoleIds,String[] roleIds){

        userService.changeRole(userId,oldRoleIds,roleIds);
        return "redirect:/system/user/list.do";
    }

}
