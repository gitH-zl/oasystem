package com.exoa.oasystem.server.impl;

import com.exoa.oasystem.dao.*;
import com.exoa.oasystem.dto.Types;
import com.exoa.oasystem.modal.BO.ArchiveBo;
import com.exoa.oasystem.modal.BO.StatisticsBo;
import com.exoa.oasystem.modal.VO.*;
import com.exoa.oasystem.server.ISiteService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
public class SiteServiceImpl implements ISiteService {
    @Resource
    private CommentVoMapper commentDao;

    @Resource
    private ContentVoMapper contentDao;
    @Resource
    private AttachVoMapper attachDao;

    @Resource
    private MetaVoMapper metaDao;
    @Override
    public List<CommentVo> recentComments(int limit) {
        if (limit<0||limit>10){
            limit=10;
        }
        CommentVoExample commentVoExample= new CommentVoExample();
        commentVoExample.setOrderByClause("created desc");
        PageHelper.startPage(1,limit);
        List<CommentVo> commentVos= commentDao.selectByExampleWithBLOBs(commentVoExample);

        return commentVos;
    }

    @Override
    public List<ContentVo> recentContents(int limit) {
        log.debug("Enter recentContents method");
        if (limit<0||limit>10){
            limit=10;
        }
        ContentVoExample contentVoExample = new ContentVoExample();
        contentVoExample.setOrderByClause("created desc");
        contentVoExample.createCriteria().andStatusEqualTo(Types.PUBLISH.getType()).andTypeEqualTo(Types.ARTICLE.getType());
        PageHelper.startPage(1,limit);
        List<ContentVo> contentVos= contentDao.selectByExampleWithBLOBs(contentVoExample);

        return contentVos;
    }

    @Override
    public CommentVo getComment(Integer coid) {
        return null;
    }

    @Override
    public StatisticsBo getStatistics() {
        ContentVoExample contentVoExample = new ContentVoExample();
        contentVoExample.createCriteria().andTypeEqualTo(Types.ARTICLE.getType()).andStatusEqualTo(Types.PUBLISH.getType());
        Long articles =   contentDao.countByExample(contentVoExample);

        Long attachs = attachDao.countByExample(new AttachVoExample());

        long comments = commentDao.countByExample(new CommentVoExample());

        MetaVoExample metaVoExample= new MetaVoExample();
        metaVoExample.createCriteria().andTypeEqualTo(Types.LINK.getType());
        long links = metaDao.countByExample(metaVoExample);
        StatisticsBo statisticsBo = new StatisticsBo();
        statisticsBo.setArticles(articles);
        statisticsBo.setAttachs(attachs);
        statisticsBo.setComments(comments);
        statisticsBo.setLinks(links);

        return statisticsBo;
    }

    @Override
    public List<ArchiveBo> getArchives() {
        return null;
    }

}
