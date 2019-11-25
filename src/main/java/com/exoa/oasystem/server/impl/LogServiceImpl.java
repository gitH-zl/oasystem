package com.exoa.oasystem.server.impl;

import com.exoa.oasystem.constant.WebConst;
import com.exoa.oasystem.dao.LogVoMapper;
import com.exoa.oasystem.modal.VO.LogVo;
import com.exoa.oasystem.modal.VO.LogVoExample;
import com.exoa.oasystem.server.ILogService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
public class LogServiceImpl implements ILogService {

    @Resource
    private LogVoMapper logDao;
    @Override
    public void insertLog(LogVo logVo) {
        logDao.insert(logVo);
    }

    @Override
    public void insertLog(String action, String data, String ip, Integer authorId) {
        LogVo logVo =new LogVo();
        logVo.setAction(action);
        logVo.setData(data);
        logVo.setIp(ip);
        logVo.setAuthorId(authorId);
        logDao.insert(logVo);
    }

    @Override
    public List<LogVo> getLogs(int page, int limit) {
        log.debug("Enter getLogs method:page={},linit={}",page,limit);
        if (page <= 0) {
            page = 1;
        }
        if (limit < 1 || limit > WebConst.MAX_POSTS) {
            limit = 10;
        }
        LogVoExample logVoExample= new LogVoExample();
        logVoExample.setOrderByClause("id desc");
        PageHelper.startPage((page - 1) * limit, limit);
        List<LogVo> logVos = null;
        try {
            logVos = logDao.selectByExample(logVoExample);
        }catch (Exception e){
            throw new RuntimeException("zhangliang");
        }

        log.debug("Exit getLogs method");
        return logVos;
    }
}
