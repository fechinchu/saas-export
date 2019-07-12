package com.fechin.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fechin.domain.cargo.ExtCproduct;
import com.fechin.domain.cargo.ExtCproductExample;
import com.fechin.domain.cargo.Factory;
import com.fechin.domain.cargo.FactoryExample;
import com.fechin.service.cargo.ExtCproductService;
import com.fechin.service.cargo.FactoryService;
import com.fechin.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cargo/extCproduct")
@SuppressWarnings("all")
public class ExCproductController extends BaseController {
    @Reference
    private ExtCproductService extCproductService;
    @Reference
    private FactoryService factoryService;

    @RequestMapping(path="/list",name = "查询附件列表")
    public String list(String contractId, String contractProductId, Model model,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size) {
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
        criteria.andContractIdEqualTo(contractId);
        criteria.andContractProductIdEqualTo(contractProductId);
        PageInfo<ExtCproduct> pageInfo = extCproductService.findOnPage(extCproductExample, page, size);
        List<ExtCproduct> list = pageInfo.getList();

        model.addAttribute("page", pageInfo);
        model.addAttribute("contractId", contractId);
        model.addAttribute("contractProductId", contractProductId);

        //查询商家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findByCtype(factoryExample);
        model.addAttribute("factoryList", factoryList);
        return "cargo/extc/extc-list";
    }

    @RequestMapping(path = "/edit",name = "添加或修改附件")
    public String edit(ExtCproduct extCproduct) {
        extCproduct.setCompanyId(companyId);
        extCproduct.setCompanyName(companyName);
        if (StringUtils.isEmpty(extCproduct.getId())) {
            extCproductService.save(extCproduct);
            return "redirect:/cargo/extCproduct/list.do?contractId=" + extCproduct.getContractId()
                    + "&contractProductId=" + extCproduct.getContractProductId();
        } else {
            extCproductService.update(extCproduct);
            return "redirect:/cargo/extCproduct/list.do?contractId=" + extCproduct.getContractId()
                    + "&contractProductId=" + extCproduct.getContractProductId();
        }
    }

    @RequestMapping(path="/toUpdate",name="前往更新页面")
    public String toUpdate(String id, String contractId, String contractProductId, Model model) {
        //虽然我们可以得到三个参数,但是id有唯一新,我们去数据库总查找,直接根据id进行查找即可
        ExtCproduct extCproduct = extCproductService.findById(id);
        model.addAttribute("extCproduct", extCproduct);

        //查询商家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findByCtype(factoryExample);
        model.addAttribute("factoryList", factoryList);

        //将contractId和contractProductId也存放在request域总
        model.addAttribute("contractId", contractId);
        model.addAttribute("contractProductId", contractProductId);
        return "cargo/extc/extc-update";
    }

    @RequestMapping("/delete")
    public String delete(String id, String contractId, String contractProductId, Model model){
        extCproductService.delete(id,contractId,contractProductId);
        return "redirect:/cargo/extCproduct/list.do?contractId=" + contractId
                + "&contractProductId=" +contractProductId;
    }

}
