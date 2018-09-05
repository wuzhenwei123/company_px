<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.dao.${domainName?cap_first}DAO">
	<resultMap id="baseResultMap" type="${packageName}.model.${domainName?cap_first}">
	<#list columns as item>
		<#if item.name == pkName>
		<id column="${item.name}" jdbcType="${item.jdbcType}" property="${item.javaName}" />
		</#if>
		<#if item.name != pkName>
		<result column="${item.name}" jdbcType="${item.jdbcType}" property="${item.javaName}" />
		</#if>
	</#list>
	</resultMap>

	<!-- 查询参数 -->
    <sql id="${domainName}_column_body">
	<#list columns as item>
		<#if (item_index+1) != (columns?size)>
		<#if (item_index) == 0><#-- 第一个 -->
         a.${item.name},<#rt>
		</#if>
		<#if (item_index) != 0><#-- 中间部分 -->
         a.${item.name},<#t>
		</#if>
    	</#if>
		<#if (item_index+1) == (columns?size)><#-- 最后一个 -->
         a.${item.name}<#lt>
    	</#if>
	</#list>
    </sql>
    <!-- 查询条件 -->
    <sql id="${domainName}_select_body">
    	<trim prefix="where" prefixOverrides="and | or">
    	<#list columns as item>
    		<if test="${item.javaName} != null"> and a.${item.name} = #${jl}${item.javaName},jdbcType=${item.jdbcType}${jld} </if>
    	</#list>
		</trim>
    </sql>
    <select id="get${domainName?cap_first}By${pkName?cap_first}" parameterType="java.lang.${pkUPType}" resultMap="baseResultMap">
        SELECT <include refid="${domainName}_column_body" />
        FROM ${tableName} a
		where a.${pkName} = #${jl}${pkName}${jld}
    </select>
    <select id="get${domainName?cap_first}List" parameterType="${packageName}.model.${domainName?cap_first}" resultMap="baseResultMap">
        SELECT <include refid="${domainName}_column_body" />
        FROM ${tableName} a
		<include refid="${domainName}_select_body" />
    </select>
    
    <insert id="insert${domainName?cap_first}" parameterType="${packageName}.model.${domainName?cap_first}">
        INSERT INTO ${tableName}
        <trim prefix="(" suffix=")" prefixOverrides=",">
        <#list columns as item>
        	<#if item.name != pkName>
	        	<if test="${item.javaName} != null"> ,${item.name} </if>
        	</#if>
		</#list>
        </trim>
        <trim prefix="values (" suffix=")" prefixOverrides=",">
        <#list columns as item>
        	<#if item.name != pkName>
	        	<if test="${item.javaName} != null"> ,#${jl}${item.javaName},jdbcType=${item.jdbcType}${jld} </if>
		    </#if>
		</#list>
		 </trim>
        <selectKey keyProperty="${pkName}" resultType="${pkType}">
			SELECT @@IDENTITY AS ${pkName} 
		</selectKey>
    </insert>
    <update id="update${domainName?cap_first}By${pkName?cap_first}" parameterType="${packageName}.model.${domainName?cap_first}">
        UPDATE ${tableName}
        <set>
        <#list columns as item>
        	<#if item.name != pkName>
        		<if test="${item.javaName} != null"> ${item.name} = #${jl}${item.javaName},jdbcType=${item.jdbcType}${jld}, </if>
        	</#if>
		</#list>
        </set>
    	where ${pkName} = #${jl}${pkName}${jld}
    </update>
    <delete id="delete${domainName?cap_first}By${pkName?cap_first}">
        delete from ${tableName} where ${pkName} = #${jl}${pkName}${jld}
    </delete>
</mapper>