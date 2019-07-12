package com.fechin.web.controller.cargo;

import com.fechin.domain.cargo.*;
import com.fechin.service.packing.InvoiceService;
import com.fechin.service.packing.PackingListService;
import com.fechin.service.packing.ShippingOrderService;
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
@RequestMapping("/cargo/invoice")
@SuppressWarnings("all")
public class InvoiceController extends BaseController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ShippingOrderService shippingOrderService;
    @Autowired
    private PackingListService packingListService;

    @RequestMapping(path = "/list", name = "发票展示")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size,
                       Model model) {
        InvoiceExample invoiceExample = new InvoiceExample();
        InvoiceExample.Criteria criteria = invoiceExample.createCriteria();
        PageInfo<Invoice> pageInfo = invoiceService.findOnPage(page, size, invoiceExample);
        model.addAttribute("page", pageInfo);
        return "cargo/invoice/invoice-list";
    }

    @RequestMapping(path = "/toAdd", name = "前往添加发票页面")
    public String toAdd(Model model,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "5") Integer size) {
        ShippingOrderExample shippingOrderExample = new ShippingOrderExample();
        ShippingOrderExample.Criteria criteria = shippingOrderExample.createCriteria();
        criteria.andStateEqualTo(1);
        PageInfo<ShippingOrder> pageInfo = shippingOrderService.findOnPage(page, size, shippingOrderExample);
        model.addAttribute("page", pageInfo);
        return "cargo/invoice/invoice-add";
    }

    @RequestMapping(path = "/toUpdate", name = "前往发票更新界面")
    public String toUpdate(Model model, String id) {
        Invoice invoice = invoiceService.findById(id);
        model.addAttribute("invoice", invoice);
        return "cargo/invoice/invoice-update";
    }

    @RequestMapping(path = "/edit", name = "更新发票")
    public String edit(String id, Invoice invoice) {
        if(StringUtils.isEmpty(invoice.getInvoiceId())){
            //表明是添加
            if(StringUtils.isEmpty(id)){
                throw new RuntimeException("要选择的呀装箱单的呀,兄丢");
            }
            PackingList packingList = packingListService.findById(id);
            String exportIds = packingList.getExportIds();
            invoice.setScNo(exportIds);
            invoice.setInvoiceId(id);
            invoice.setCreateBy(userName);
            invoice.setCreateDept(deptId);
            invoice.setCreateTime(new Date());
            invoice.setState(0);
            invoiceService.save(invoice);
            ShippingOrder shippingOrder = new ShippingOrder();
            shippingOrder.setShippingOrderId(id);
            shippingOrder.setState(2);
            shippingOrderService.update(shippingOrder);
            return "redirect:/cargo/invoice/list.do";
        }else{
            invoiceService.update(invoice);
            return "redirect:/cargo/invoice/list.do";
        }
    }

    @RequestMapping(path = "/delete",name = "删除发票")
    public String delete(String id){
        invoiceService.delete(id);
        return "redirect:/cargo/invoice/list.do";
    }

    @RequestMapping(path = "/submit",name = "提交")
    public String submit(String id){
        invoiceService.submit(id);
        return "redirect:/cargo/invoice/list.do";
    }

    @RequestMapping(path = "/cancel",name = "取消")
    public String cancel(String id){
        invoiceService.cancel(id);
        return "redirect:/cargo/invoice/list.do";
    }



}
