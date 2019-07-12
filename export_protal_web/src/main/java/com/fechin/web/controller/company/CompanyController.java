package com.fechin.web.controller.company;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fechin.domain.company.Company;
import com.fechin.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@RequestMapping("/company")
@Controller
public class CompanyController {
    //注意不能使用@AutoWired标签,要使用@Reference
    @Reference
    private CompanyService companyService;

    //添加信息/apply.do
    @ResponseBody
    @RequestMapping(value = "/apply", name = "公司保存修改或添加完成")
    public String save(Company company) {
        System.out.println(company);
        try {
            companyService.edit(company);
        } catch (Exception e) {
            return "2";
        }
        return "1";
    }

}
