package com.fechin.web.controller;

import com.fechin.domain.system.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;

    protected String companyId = "1";

    protected String companyName = "传智播客教育股份有限公司";

    protected User userInfo;
    protected String userName;
    protected String userId;
    protected String deptId;
    protected String deptName;
    protected Integer degree;

    @ModelAttribute
    public void init() {

        userInfo = (User) session.getAttribute("userInfo");
        if (userInfo != null) {
            companyId = userInfo.getCompanyId();
            companyName = userInfo.getCompanyName();
            degree = userInfo.getDegree();
            userName = userInfo.getUserName();
            userId = userInfo.getUserId();
            deptId = userInfo.getDeptId();
            deptName = userInfo.getDeptName();
        }
    }

}
