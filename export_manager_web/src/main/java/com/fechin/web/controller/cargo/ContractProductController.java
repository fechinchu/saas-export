package com.fechin.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fechin.domain.cargo.ContractProduct;
import com.fechin.domain.cargo.ContractProductExample;
import com.fechin.domain.cargo.Factory;
import com.fechin.domain.cargo.FactoryExample;
import com.fechin.service.cargo.ContractProductService;
import com.fechin.service.cargo.FactoryService;
import com.fechin.web.controller.BaseController;
import com.fechin.web.utils.FileUpLoadUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SuppressWarnings("all")
@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {
    @Reference
    private ContractProductService contractProductService;
    @Reference
    private FactoryService factoryService;
    @Autowired
    private FileUpLoadUtils fileUpLoadUtils;

    @RequestMapping(path = "/list", name = "货物列表")
    public String list(String contractId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size, Model model) {
        ContractProductExample example = new ContractProductExample();
        ContractProductExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(contractId);
        PageInfo<ContractProduct> pageInfo = contractProductService.findByContractIdOnPages(example, page, size);
        model.addAttribute("page", pageInfo);

        //查询商家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findByCtype(factoryExample);
        model.addAttribute("factoryList", factoryList);
        model.addAttribute("contractId", contractId);
        return "cargo/product/product-list";
    }

    @RequestMapping(path = "/edit", name = "添加货物或更新货物")
    public String edit(ContractProduct contractProduct, MultipartFile productPhoto) throws Exception {
        contractProduct.setCompanyName(companyName);
        contractProduct.setCompanyId(companyId);
        if (contractProduct.getId() == null) {
            if (productPhoto!=null){
                String img = "http://"+fileUpLoadUtils.upload(productPhoto);
                //这边返回的是域名/图片名称
                contractProduct.setProductImage(img);
            }
            contractProductService.save(contractProduct);
            return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
        } else {
            contractProductService.update(contractProduct);
            return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
        }
    }

    @RequestMapping(path = "toUpdate", name = "跳向修改货物页面")
    public String toUpdate(String id, Model model) {
        //根据货物id进行查找
        ContractProduct contractProduct = contractProductService.findById(id);
        model.addAttribute("contractProduct", contractProduct);

        //查询货物商家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findByCtype(factoryExample);
        model.addAttribute("factoryList", factoryList);
        return "cargo/product/product-update";
    }

    @RequestMapping(path = "delete", name = "删除货物")
    public String delete(String id, String contractId, Model model) {
        contractProductService.delete(id, contractId);
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }



}
