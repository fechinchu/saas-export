package com.fechin.web.controller.cargo;

import com.fechin.domain.cargo.Finance;
import com.fechin.domain.cargo.Invoice;
import com.fechin.domain.cargo.InvoiceExample;
import com.fechin.service.packing.FinanceService;
import com.fechin.service.packing.InvoiceService;
import com.fechin.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@SuppressWarnings("all")
@RequestMapping("/cargo/finance")
public class FinanceController extends BaseController {
    @Autowired
    private FinanceService financeService;

    @Autowired
    private InvoiceService invoiceService;

    @RequestMapping(path = "/list", name = "财务报运展示")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size,
                       Model model) {
        PageInfo<Finance> pageInfo = financeService.findOnPage(page, size);
        model.addAttribute("page", pageInfo);
        return "cargo/finance/finance-list";
    }

    @RequestMapping(path = "/toAdd", name = "前往添加财务报运单页面")
    public String toAdd(Model model,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "5") Integer size) {
        InvoiceExample invoiceExample = new InvoiceExample();
        InvoiceExample.Criteria criteria = invoiceExample.createCriteria();
        criteria.andStateEqualTo(1);
        PageInfo<Invoice> pageInfo = invoiceService.findOnPage(page, size, invoiceExample);
        model.addAttribute("page", pageInfo);
        return "cargo/finance/finance-add";
    }

    @RequestMapping(path = "/toUpdate", name = "前往财务报运单更新界面")
    public String toUpdate(Model model, String id) {
        Finance finance = financeService.findById(id);
        model.addAttribute("finance", finance);
        return "cargo/finance/finance-update";
    }

    @RequestMapping(path = "/edit", name = "更新财务报运单")
    public String edit(String id, Finance finance) {
        if(StringUtils.isEmpty(finance.getFinanceId())){
            //表明是添加
            if(StringUtils.isEmpty(id)){
                throw new RuntimeException("要选择的呀装箱单的呀,兄丢");
            }
            Invoice invoice = new Invoice();
            invoice.setInvoiceId(id);
            invoice.setState(2);
            invoiceService.update(invoice);
            finance.setFinanceId(id);
            finance.setCreateBy(userName);
            finance.setCreateDept(deptId);
            finance.setCreateTime(new Date());
            finance.setState(0);
            financeService.save(finance);
            return "redirect:/cargo/finance/list.do";
        }else{
            financeService.update(finance);
            return "redirect:/cargo/finance/list.do";
        }
    }

    @RequestMapping(path = "/delete",name = "删除财务报运单")
    public String delete(String id){
        financeService.delete(id);
        return "redirect:/cargo/finance/list.do";
    }

    @RequestMapping(path = "/submit",name = "提交")
    public String submit(String id){
        financeService.submit(id);
        return "redirect:/cargo/finance/list.do";
    }

    @RequestMapping(path = "/cancel",name = "取消")
    public String cancel(String id){
        financeService.cancel(id);
        return "redirect:/cargo/finance/list.do";
    }
}
