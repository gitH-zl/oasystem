package com.exoa.oasystem.server.impl;

import com.exoa.oasystem.dao.ContentVoMapper;
import com.exoa.oasystem.dto.Types;
import com.exoa.oasystem.modal.VO.ContentVo;
import com.exoa.oasystem.modal.VO.ContentVoExample;
import com.exoa.oasystem.server.IContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ContentServiceImpl implements IContentService {
    @Resource
    private ContentVoMapper contentDao;

    @Override
    public void publish(ContentVo contents) {

    }

    @Override
    public PageInfo<ContentVo> getContents(Integer p, Integer limit) {
        ContentVoExample contentVoExample= new ContentVoExample();
        contentVoExample.setOrderByClause("created desc");
        contentVoExample.createCriteria().andTypeEqualTo(Types.ARTICLE.getType()).andStatusEqualTo(Types.PUBLISH.getType());
        PageHelper.startPage(p,limit);
        List<ContentVo> contentVos = contentDao.selectByExampleWithBLOBs(contentVoExample);
        PageInfo<ContentVo> pageInfo=  new PageInfo<>(contentVos);
        log.debug("Exit getContents method--1");
        return pageInfo;
    }

    @Override
    public ContentVo getContents(String id) {
        return null;
    }

    @Override
    public void updateContentByCid(ContentVo contentVo) {

    }

    @Override
    public PageInfo<ContentVo> getArticles(Integer mid, int page, int limit) {
        return null;
    }

    @Override
    public PageInfo<ContentVo> getArticles(String keyword, Integer page, Integer limit) {
        return null;
    }

    @Override
    public PageInfo<ContentVo> getArticlesWithpage(ContentVoExample commentVoExample, Integer page, Integer limit) {
        return null;
    }

    @Override
    public void deleteByCid(Integer cid) {

    }

    @Override
    public void updateArticle(ContentVo contents) {

    }

    @Override
    public void updateCategory(String ordinal, String newCatefory) {

    }
}
