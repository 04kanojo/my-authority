package com.kanojo.service;

import com.kanojo.module.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ResourceService extends IService<Resource> {


    List<Resource> getResources(List<Long> resourceIds);
}
