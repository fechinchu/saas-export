package com.fechin.web.controller.system.log;

import com.fechin.web.controller.BaseController;
import com.fechin.domain.system.log.Log;
import com.fechin.service.system.log.LogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/system/log")
public class LogController extends BaseController {
    @Autowired
    private LogService logService;

    @RequestMapping(path="/list",name="查看日志")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size, Model model){
        PageInfo<Log> logs = logService.findByPage(page, size, companyId);
        model.addAttribute("page",logs);
        return "system/log/log-list";
    }
}
