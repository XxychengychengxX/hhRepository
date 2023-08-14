/**
 * @author Valar Morghulis
 * @Date 2023/8/9
 */
package com.project.hhrepository.controller.mycontroller;

import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.myentity.Statistics;
import com.project.hhrepository.service.IStoreService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticController {
    @Resource
    IStoreService storeService;

    @GetMapping("/store-invent")
    public Result getStoreInvent() {
        List<Statistics> storeInvents = storeService.getStoreInvents();
        return Result.ok(storeInvents);
    }

}
