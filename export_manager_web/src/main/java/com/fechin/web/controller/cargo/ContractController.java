package com.fechin.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fechin.domain.cargo.Contract;
import com.fechin.domain.cargo.ContractExample;
import com.fechin.service.cargo.ContractService;
import com.fechin.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@SuppressWarnings("all")
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {
    @Reference
    private ContractService contractService;

    @RequestMapping(path = "/list", name = "查找所有合同信息")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size,
                       Model model) {
        //查询之前,设定好查找的example和排序
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        contractExample.setOrderByClause("update_time desc");

        //在合同的入口进行细粒度的权限控制
        /**
         * 1:是系统管理员,2:管理所有下属部门和人员,3:管理本部门,4:普通员工
         * 如果是普通员工,在查询时候,只能查询他所创建的合同
         * 如果是部门经理,查询的时候,只能查询到他所在部门的所有合同
         * 如果是管理层,能够查找到他部门及其子部门的所有合同
         */
        if(degree==4){
            criteria.andCreateByEqualTo(userId);
        }else if(degree==3){
            criteria.andCreateDeptEqualTo(deptId);
        }else if(degree==2){
            criteria.andCreateDeptLike(deptId+"%");
        }

        PageInfo<Contract> pageInfo = contractService.findByCompanyIdOnPages(page, size, contractExample);
        model.addAttribute("page", pageInfo);
        return "cargo/contract/contract-list";

    }

    @RequestMapping(path = "/toAdd", name = "前往添加页面")
    public String toAdd() {
        return "cargo/contract/contract-add";
    }

    @RequestMapping(path = "/edit", name = "添加或更新完成")
    public String edit(Contract contract) {
        contract.setUpdateBy(userId);
        //添加
        if (StringUtils.isEmpty(contract.getId())) {
            contract.setCompanyId(companyId);
            contract.setCompanyName(companyName);
            contract.setCreateBy(userId);
            contract.setCreateDept(deptId);
            contractService.add(contract);
            return "redirect:/cargo/contract/list.do";
        } else {
            //修改
            contractService.update(contract);
            return "redirect:/cargo/contract/list.do";
        }

    }

    @RequestMapping(path = "/toUpdate", name = "前往更新页面")
    public String toUpdate(String id, Model model) {
        //前往更新页面之前需要回显数据
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andCompanyIdEqualTo(companyId);
        Contract contract = contractService.findByCompanyIdAndId(contractExample);
        model.addAttribute("contract", contract);
        return "cargo/contract/contract-update";
    }

    @RequestMapping(path = "delete", name = "删除合同")
    public String delete(String id) {
        contractService.deleteById(id);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(path = "/toView", name = "查看当前合同")
    public String toView(String id, Model model) {
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andCompanyIdEqualTo(companyId);
        Contract contract = contractService.findByCompanyIdAndId(contractExample);
        model.addAttribute("contract", contract);
        return "cargo/contract/contract-view";
    }

    @RequestMapping(path = "/submit", name = "提交合同")
    public String submit(String id) {
        contractService.submitById(id);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(path = "/cancel", name = "取消合同")
    public String cancel(String id) {
        contractService.cancelById(id);
        return "redirect:/cargo/contract/list.do";
    }

}
