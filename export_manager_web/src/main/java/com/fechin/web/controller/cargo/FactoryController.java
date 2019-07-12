package com.fechin.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fechin.service.cargo.FactoryService;
import org.springframework.stereotype.Controller;

@Controller
public class FactoryController {
    @Reference
    private FactoryService factoryService;
}
