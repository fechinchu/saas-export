package com.fechin.web.controller;

import com.fechin.domain.module.Module;
import com.fechin.domain.system.user.User;
import com.fechin.service.module.ModuleService;
import com.fechin.service.system.role.RoleService;
import com.fechin.service.system.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/login")
    public String login(User user, Model model) {
        //在数据库查询之前先进行判定
        if (user.getEmail() == null) {
            model.addAttribute("error", "email不能为空");
            return "forward:/login.jsp";
        }
        if (user.getPassword() == null) {
            model.addAttribute("error", "密码不能为空");
            return "forward:/login.jsp";
        }
        //前面的判断非空判断保留
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            model.addAttribute("error", "用户名或密码错误");
            return "forward:/login.jsp";
        }

        User user1 = (User) subject.getPrincipal();
        session.setAttribute("userInfo", user1);
        List<Module> modules = moduleService.findModuleByUid(user1);
        session.setAttribute("modules", modules);
        return "home/main";

    }

    @RequestMapping("/logout")
    public String logout() {
        session.removeAttribute("user");
        return "redirect:/login.jsp";
    }

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }
}
