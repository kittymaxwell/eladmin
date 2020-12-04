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
package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.PluginAdvise;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.PluginAdviseRepository;
import me.zhengjie.modules.system.service.PluginAdviseService;
import me.zhengjie.modules.system.service.dto.PluginAdviseDto;
import me.zhengjie.modules.system.service.dto.PluginAdviseQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.PluginAdviseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author wujianbin
* @date 2020-12-04
**/
@Service
@RequiredArgsConstructor
public class PluginAdviseServiceImpl implements PluginAdviseService {

    private final PluginAdviseRepository pluginAdviseRepository;
    private final PluginAdviseMapper pluginAdviseMapper;

    @Override
    public Map<String,Object> queryAll(PluginAdviseQueryCriteria criteria, Pageable pageable){
        Page<PluginAdvise> page = pluginAdviseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(pluginAdviseMapper::toDto));
    }

    @Override
    public List<PluginAdviseDto> queryAll(PluginAdviseQueryCriteria criteria){
        return pluginAdviseMapper.toDto(pluginAdviseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public PluginAdviseDto findById(Long id) {
        PluginAdvise pluginAdvise = pluginAdviseRepository.findById(id).orElseGet(PluginAdvise::new);
        ValidationUtil.isNull(pluginAdvise.getId(),"PluginAdvise","id",id);
        return pluginAdviseMapper.toDto(pluginAdvise);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PluginAdviseDto create(PluginAdvise resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return pluginAdviseMapper.toDto(pluginAdviseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PluginAdvise resources) {
        PluginAdvise pluginAdvise = pluginAdviseRepository.findById(resources.getId()).orElseGet(PluginAdvise::new);
        ValidationUtil.isNull( pluginAdvise.getId(),"PluginAdvise","id",resources.getId());
        pluginAdvise.copy(resources);
        pluginAdviseRepository.save(pluginAdvise);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            pluginAdviseRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<PluginAdviseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PluginAdviseDto pluginAdvise : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("创建时间", pluginAdvise.getCreateTime());
            map.put("更新时间", pluginAdvise.getUpdateTime());
            map.put("创建者", pluginAdvise.getCreateUser());
            map.put("更新者", pluginAdvise.getUpdateUser());
            map.put("1已删除 0未删除", pluginAdvise.getDeleteFlag());
            map.put("意见建议", pluginAdvise.getContent());
            map.put("联系方式", pluginAdvise.getContact());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}