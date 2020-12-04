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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.PluginAdvise;
import me.zhengjie.modules.system.service.PluginAdviseService;
import me.zhengjie.modules.system.service.dto.PluginAdviseQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author wujianbin
* @date 2020-12-04
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "意见建议管理")
@RequestMapping("/api/pluginAdvise")
public class PluginAdviseController {

    private final PluginAdviseService pluginAdviseService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('pluginAdvise:list')")
    public void download(HttpServletResponse response, PluginAdviseQueryCriteria criteria) throws IOException {
        pluginAdviseService.download(pluginAdviseService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询意见建议")
    @ApiOperation("查询意见建议")
    @PreAuthorize("@el.check('pluginAdvise:list')")
    public ResponseEntity<Object> query(PluginAdviseQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(pluginAdviseService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增意见建议")
    @ApiOperation("新增意见建议")
    @PreAuthorize("@el.check('pluginAdvise:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody PluginAdvise resources){
        return new ResponseEntity<>(pluginAdviseService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改意见建议")
    @ApiOperation("修改意见建议")
    @PreAuthorize("@el.check('pluginAdvise:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody PluginAdvise resources){
        pluginAdviseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除意见建议")
    @ApiOperation("删除意见建议")
    @PreAuthorize("@el.check('pluginAdvise:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        pluginAdviseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}