package com.exoa.oasystem.dao;

import com.exoa.oasystem.modal.VO.RelationshipVoExample;
import com.exoa.oasystem.modal.VO.RelationshipVoKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RelationshipVoMapper {
    long countByExample(RelationshipVoExample example);

    int deleteByExample(RelationshipVoExample example);

    int deleteByPrimaryKey(RelationshipVoKey key);

    int insert(RelationshipVoKey record);

    int insertSelective(RelationshipVoKey record);

    List<RelationshipVoKey> selectByExample(RelationshipVoExample example);

    int updateByExampleSelective(@Param("record") RelationshipVoKey record, @Param("example") RelationshipVoExample example);

    int updateByExample(@Param("record") RelationshipVoKey record, @Param("example") RelationshipVoExample example);
}