package com.srct.service.dao.mapper;

import com.srct.service.dao.entity.Info;
import com.srct.service.dao.entity.InfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    long countByExample(InfoExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    int deleteByExample(InfoExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    int insert(Info record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    int insertSelective(Info record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    List<Info> selectByExample(InfoExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    Info selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Info record, @Param("example") InfoExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Info record, @Param("example") InfoExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Info record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Info record);
}
