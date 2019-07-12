package com.fechin.web.controller.company;

import com.fechin.domain.PageBean;
import com.fechin.domain.company.Company;
import com.fechin.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    //查询所有
    @RequestMapping(path="/list",name="查询所有公司")
    public String findAll(Model model){
       /* List<Company> list = companyService.findAll();
        model.addAttribute("list",list);*/
        PageBean pageBean = companyService.findPageNumber(10,1);
        List list = pageBean.getList();
        model.addAttribute("list",list);
        model.addAttribute("page",pageBean);
        return "company/company-list";
    }

    //去添加页面
    @RequestMapping(path="/toAdd",name="跳转到添加公司页面")
    public String toAdd(){
        return "company/company-add";
    }

    //添加信息
    @RequestMapping(path="/edit",name="公司保存修改或添加完成")
    public String edit(Company company){
        if(StringUtils.isEmpty(company.getId())){
            //System.out.println(company);
            companyService.edit(company);
            return "forward:/company/list.do";//OK
        }else {
            companyService.update(company);
            return "forward:/company/list.do";//OK
        }
    }

    //回显数据
    @RequestMapping(path="/toUpdate",name="跳转到修改公司页面")
    public String toUpdate( String id, HttpServletRequest httpServletRequest){
        Company company = companyService.findByOne(id);
        httpServletRequest.setAttribute("company",company);
        return "company/company-update";
    }
    @RequestMapping(path="/delete",name="删除公司")
    public String delete(String id){
        companyService.delete(id);
        return "redirect:/company/list.do";
    }
    @RequestMapping(path="/findByPageNumber",name = "查询一个公司")
    public String findByPageNumber(Integer pageCount, Integer pageIndex, Model model){
        //System.out.println(pageCount);
        PageBean pageBean = companyService.findPageNumber(pageCount,pageIndex);
        List list = pageBean.getList();
        model.addAttribute("list",list);
        model.addAttribute("page",pageBean);
        return "company/company-list";
    }



}
