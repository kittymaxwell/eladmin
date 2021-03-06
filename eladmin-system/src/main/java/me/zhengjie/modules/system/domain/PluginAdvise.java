/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author wujianbin
* @date 2020-12-04
**/
@Entity
@Data
@Table(name="sys_plugin_advise")
@EntityListeners(AuditingEntityListener.class)
public class PluginAdvise implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "create_time")
    @CreatedDate
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @LastModifiedDate
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "create_user")
    @ApiModelProperty(value = "创建者")
    private String createUser;

    @Column(name = "update_user")
    @ApiModelProperty(value = "更新者")
    private String updateUser;

    @Column(name = "delete_flag")
    @ApiModelProperty(value = "1已删除 0未删除")
    private Integer deleteFlag;

    @Column(name = "content",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "意见建议")
    private String content;

    @Column(name = "contact",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "联系方式")
    private String contact;

    public void copy(PluginAdvise source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
