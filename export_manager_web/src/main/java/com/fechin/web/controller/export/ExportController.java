package com.fechin.web.controller.export;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fechin.domain.cargo.Contract;
import com.fechin.domain.cargo.ContractExample;
import com.fechin.domain.export.Export;
import com.fechin.domain.export.ExportExample;
import com.fechin.domain.export.ExportProduct;
import com.fechin.service.cargo.ContractService;
import com.fechin.service.export.ExportProductService;
import com.fechin.service.export.ExportService;
import com.fechin.service.system.user.UserService;
import com.fechin.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import common.utils.BeanMapUtils;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {

    @Reference
    private ContractService contractService;
    @Reference
    private ExportService exportService;
    @Reference
    private ExportProductService exportProductService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/contractList", name = "查询已提交的合同")
    public String contractList(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "5") Integer size,
                               Model model) {
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo<Contract> pageInfo = contractService.findByCompanyIdOnPages(1, 5, contractExample);
        model.addAttribute("page", pageInfo);
        return "cargo/export/export-contractList";
    }

    @RequestMapping(path = "/toExport", name = "前往保运页面")
    public String toExport(String id) {
        Export export = new Export();
        export.setContractIds(id);
        export.setCompanyId(companyId);
        export.setCompanyName(companyName);
        export.setCreateBy(userName);
        export.setCreateDept(deptName);
        exportService.saveExport(export);
        return "redirect:/cargo/export/contractList.do";
    }

    @RequestMapping(path = "/list", name = "展示出口报运订单列表")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size,
                       Model model) {
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo<Export> pageInfo = exportService.findAllOnPage(exportExample, page, size);
        model.addAttribute("page", pageInfo);
        return "cargo/export/export-list";
    }

    @RequestMapping(path = "/toView", name = "展示报运订单")
    public String toView(String id, Model model) {
        Export export = exportService.findById(id);
        model.addAttribute("export", export);
        return "cargo/export/export-view";
    }

    @RequestMapping(path = "/toUpdate", name = "前往更新保运订单页面")
    public String toUpdate(String id, Model model) {
        Export export = exportService.findById(id);
        model.addAttribute("export", export);
        //根据exportId查询货物
        List<ExportProduct> exportProductList = exportProductService.findByExportId(id);
        model.addAttribute("eps", exportProductList);
        return "cargo/export/export-update";
    }

    @RequestMapping(path = "/edit", name = "更新报运订单")
    public String edit(Export export) {
        System.out.println(export);
        List<ExportProduct> exportProducts = export.getExportProducts();
        for (ExportProduct exportProduct : exportProducts) {
            exportProductService.updateExportProduct(exportProduct);
        }
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping(path = "/submit", name = "提交报运单")
    public String submit(String id) {
        Export export = new Export();
        export.setId(id);
        export.setState(1L);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping(path = "/cancel", name = "取消报运单")
    public String cancel(String id) {
        Export export = new Export();
        export.setId(id);
        export.setState(0L);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping(path = "/exportE", name = "进行电子报运")
    public String exportE(String id) {
        exportService.exportE(id);
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping(path = "/exportPdf", name = "pdf打印")
    public void exportPdf(String id) throws Exception {
        Export export= exportService.findById(id);
        List<ExportProduct> exportProducts = exportProductService.findByExportId(id);
        Map<String, Object> stringObjectMap = BeanMapUtils.beanToMap(export);
        JRBeanCollectionDataSource js = new JRBeanCollectionDataSource(exportProducts);
        //1.获得jasper文件的绝对路径
        String realPath = session.getServletContext().getRealPath("/jasper/export.jasper");
        //2.创建文件输入流
        FileInputStream fileInputStream = new FileInputStream(realPath);
        //3.创建jrprint对象
        JasperPrint jasperPrint = JasperFillManager.fillReport(fileInputStream, stringObjectMap, js);
        //4.打印(下载)
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }
}
