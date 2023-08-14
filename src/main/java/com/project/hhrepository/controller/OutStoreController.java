package com.project.hhrepository.controller;

import com.project.hhrepository.entity.OutStore;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.Store;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IOutStoreService;
import com.project.hhrepository.service.IStoreService;
import com.project.hhrepository.utils.WarehouseConstants;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 出库单 前端控制器
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:9988"})
@RequestMapping("/outstore")
public class OutStoreController {

    @Resource
    IOutStoreService outStoreService;


    @Resource
    IStoreService storeService;

    @PostMapping("/outstore-add")
    public Result addOutStore(@RequestBody OutStore outStore,
                              @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token) {
        Boolean aBoolean = outStoreService.addOutStore(outStore, token);
        if (aBoolean)
            return Result.ok("添加出库单信息成功");
        return Result.err(Result.CODE_ERR_BUSINESS,"添加出库单信息失败");

    }

    @GetMapping("/store-list")
    public Result getStoreList(){
        //直接调用即可
        List<Store> storeList = storeService.getStoreList();
        //这里是ok
        return Result.ok(storeList);
    }


    @GetMapping("/outstore-page-list")
    public Result getOutStoreListPage( Page page,OutStore outStore){
        Page outStoreListPage = outStoreService.getOutStoreListPage(page, outStore);
        return Result.ok(outStoreListPage);
    }

    @PutMapping("/outstore-confirm")
    public Result confirmOutStore(@RequestBody OutStore outStore,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

        return outStoreService.confirmOutStore(outStore,token);
    }
}
