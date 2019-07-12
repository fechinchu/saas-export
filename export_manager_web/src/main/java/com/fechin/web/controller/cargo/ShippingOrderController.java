package com.fechin.web.controller.cargo;

import com.fechin.domain.cargo.PackingList;
import com.fechin.domain.cargo.PackingListExample;
import com.fechin.domain.cargo.ShippingOrder;
import com.fechin.domain.cargo.ShippingOrderExample;
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
@RequestMapping("/cargo/shipping")
@SuppressWarnings("all")
public class ShippingOrderController extends BaseController {
    @Autowired
    private ShippingOrderService shippingOrderService;
    @Autowired
    private PackingListService packingListService;

    @RequestMapping(path = "/list", name = "委托管理列表")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size,
                       Model model) {
        //很不好意思,这张表TM的没有公司id
        ShippingOrderExample shippingOrderExample = new ShippingOrderExample();
        ShippingOrderExample.Criteria criteria = shippingOrderExample.createCriteria();
        PageInfo<ShippingOrder> pageInfo = shippingOrderService.findOnPage(page, size,shippingOrderExample);
        model.addAttribute("page", pageInfo);
        return "cargo/shipping/shipping-list";
    }

    @RequestMapping(path = "toAdd", name = "前往添加页面")
    public String toAdd(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "5") Integer size,
                        Model model) {
        PackingListExample packingListExample = new PackingListExample();
        PackingListExample.Criteria criteria = packingListExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        criteria.andStateEqualTo(1L);
        PageInfo<PackingList> pageInfo = packingListService.findAllOnPage(page, size, packingListExample);
        model.addAttribute("page", pageInfo);
        return "cargo/shipping/shipping-add";
    }

    @RequestMapping(path = "toUpdate", name = "前往修改页面")
    public String toUpdate(String id, Model model) {
        ShippingOrder shippingOrder = shippingOrderService.findById(id);
        model.addAttribute("shipping", shippingOrder);
        return "cargo/shipping/shipping-update";
    }

    @RequestMapping(path = "edit", name = "增加或者修改")
    public String edit(String id, ShippingOrder shippingOrder) {
        //保存操作
        if (StringUtils.isEmpty(shippingOrder.getShippingOrderId())) {
            //id是选择的装箱单id,因为装箱单和委托单是属于共享id
            if (StringUtils.isEmpty(id)) {
                throw new RuntimeException("请选择装箱单");
            }
            shippingOrder.setShippingOrderId(id);
            shippingOrder.setCreateBy(userName);
            shippingOrder.setState(0);
            shippingOrder.setCreateDept(deptId);
            shippingOrder.setCreateTime(new Date());
            shippingOrderService.save(shippingOrder);
            PackingList packingList = new PackingList();
            packingList.setPackingListId(id);
            packingList.setState(2L);
            packingListService.update(packingList);
            return "redirect:/cargo/shipping/list.do";
        } else {
            shippingOrderService.update(shippingOrder);
            return "redirect:/cargo/shipping/list.do";
        }
    }

    @RequestMapping(path = "delete", name = "删除")
    public String delete(String id) {
        shippingOrderService.delete(id);
        return "redirect:/cargo/shipping/list.do";
    }

    @RequestMapping(path = "submit", name = "提交")
    public String submit(String id) {
        ShippingOrder shippingOrder = new ShippingOrder();
        shippingOrder.setShippingOrderId(id);
        shippingOrder.setState(1);
        shippingOrderService.update(shippingOrder);
        return "redirect:/cargo/shipping/list.do";
    }

    @RequestMapping(path = "cancel", name = "取消")
    public String cancel(String id) {
        ShippingOrder shippingOrder = new ShippingOrder();
        shippingOrder.setShippingOrderId(id);
        shippingOrder.setState(0);
        shippingOrderService.update(shippingOrder);
        return "redirect:/cargo/shipping/list.do";
    }
}
