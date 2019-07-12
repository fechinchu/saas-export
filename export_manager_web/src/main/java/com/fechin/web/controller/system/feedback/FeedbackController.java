package com.fechin.web.controller.system.feedback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fechin.domain.system.feedback.Feedback;
import com.fechin.domain.system.feedback.FeedbackExample;
import com.fechin.service.FeedbackService;
import com.fechin.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.UUID;

@Controller
@SuppressWarnings("all")
@RequestMapping("/system/feedback")
public class FeedbackController extends BaseController {
    @Reference
    private FeedbackService feedbackService;


    @RequestMapping(path= "/list",name = "前往意见展示页面")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size,
                       Model model){

        //在这边进行细你粒度的权限控制
        FeedbackExample feedbackExample = new FeedbackExample();
        FeedbackExample.Criteria criteria = feedbackExample.createCriteria();
        if(degree==4){
            criteria.andCreateByEqualTo(userId);
        }else if(degree==3){
            criteria.andCreateDeptEqualTo(deptId);
        }else if(degree==2){
            criteria.andCreateDeptLike(deptId+"%");
        }
        PageInfo<Feedback> pageInfor = feedbackService.findByPage(feedbackExample,page,size);
        model.addAttribute("page",pageInfor);
        return "system/feedback/feedback-list";

    }
    @RequestMapping(path="/toAdd",name="前往添加界面")
    public String toAdd(){
        return "system/feedback/feedback-add";
    }

    @RequestMapping(path="/edit",name="新增或修改feedback")
    public String edit(Feedback feedback){
        String feedbackId = feedback.getFeedbackId();
        if(StringUtils.isEmpty(feedbackId)){
            //如果feedbackId为空,那么可以知道是新增
            feedback.setCreateBy(userName);
            feedback.setCompanyId(companyId);
            feedback.setCompanyName(companyName);
            feedback.setCreateTime(new Date());
            feedback.setState("0");
            feedback.setCreateDept(deptName);
            feedback.setFeedbackId(UUID.randomUUID().toString().replace("-",""));
            feedbackService.save(feedback);
            return "redirect:/system/feedback/list.do";
        }else{
            if(StringUtils.isEmpty(feedback.getResolution())){
                feedbackService.update(feedback);
                return "redirect:/system/feedback/list.do";
            }else{
                feedback.setState("1");
                feedbackService.update(feedback);
                return "redirect:/system/feedback/list.do";
            }
        }
    }

    @RequestMapping(path="toView",name="查看详情")
    public String toView(String id,Model model){
        Feedback feedback = feedbackService.findById(id);
        model.addAttribute("feedback",feedback);
        return "system/feedback/feedback-view";
    }

    @RequestMapping(path = "toUpdate",name = "修改")
    public String toUpdate(String id,Model model){
        Feedback feedback = feedbackService.findById(id);
        model.addAttribute("feedback",feedback);
        return "system/feedback/feedback-update";
    }

    @RequestMapping(path="toProcess",name="前往处理页面")
    public String toProcess(String id,Model model){
        Feedback feedback = feedbackService.findById(id);
        model.addAttribute("feedback",feedback);
        return "system/feedback/feedback-process";
    }

    @RequestMapping(path="delete",name="删除feedback")
    public String delete(String id){
        feedbackService.delete(id);
        return "redirect:/system/feedback/list.do";
    }
}
