package com.exoa.oasystem.controller.admin;


import com.exoa.oasystem.controller.BaseController;
import com.exoa.oasystem.exception.TipException;
import com.exoa.oasystem.modal.BO.StatisticsBo;
import com.exoa.oasystem.modal.VO.CommentVo;
import com.exoa.oasystem.modal.VO.ContentVo;
import com.exoa.oasystem.modal.VO.LogVo;
import com.exoa.oasystem.server.ILogService;
import com.exoa.oasystem.server.ISiteService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller("adminIndexController")
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class IndexController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    @Resource
    private ISiteService siteService;
    @Resource
    private ILogService logService;
    @GetMapping(value = {"","/index"})
    public String index(HttpServletRequest request){
        LOGGER.info("Enter admin index method");
        List<CommentVo> comments = siteService.recentComments(5);
        List<ContentVo> contents = siteService.recentContents(5);
        StatisticsBo statistics = siteService.getStatistics();
        // 取最新的20条日志
        //记录一个 Invalid bound statement (not found) 确实是映射问题
        List<LogVo> logs = logService.getLogs(1, 5);

        request.setAttribute("comments", comments);
        request.setAttribute("articles", contents);
        request.setAttribute("statistics", statistics);
        request.setAttribute("logs", logs);
        log.info("Exit admin index method");
        return "admin/index";
    }
}
