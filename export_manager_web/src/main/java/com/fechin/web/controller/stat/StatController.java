package com.fechin.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fechin.service.StatService;
import com.fechin.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {
    @Reference
    private StatService statService;

    @RequestMapping(path = "/toCharts", name = "前往图形报表页面")
    public String toCharts(String chartsType) {
        //通过controller层实现页面跳转,ajax进行加载
        if (chartsType.equals("factory")) {
            return "stat/stat-factory";
        } else if (chartsType.equals("sell")) {
            return "stat/stat-sell";
        } else if (chartsType.equals("online")) {
            return "stat/stat-online";
        } else {
            //不存在
            return null;
        }
    }

    @RequestMapping(path = "/factory", name = "ajax访问工厂销售货物页面")
    @ResponseBody
    public List<Map<String, Object>> findFactoryWithSumCnumber() {
        return statService.findFactoryWithSumCnumber(companyId);
    }

    @ResponseBody
    @RequestMapping(path = "/sell", name = "ajax访问货物的销售情况")
    public List<Map<String, Object>> findSell() {
        return statService.findSell(companyId);
    }

    @ResponseBody
    @RequestMapping(path = "/online",name = "ajax访问在线时间段情况")
    public List<Map<String,Object>> findOnline(){
        return statService.findOnline(companyId);
    }


}
