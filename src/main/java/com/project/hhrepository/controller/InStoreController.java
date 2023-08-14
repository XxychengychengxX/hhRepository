package com.project.hhrepository.controller;

import com.project.hhrepository.entity.InStore;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.Store;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IInStoreService;
import com.project.hhrepository.service.IStoreService;
import com.project.hhrepository.utils.WarehouseConstants;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 入库单 前端控制器
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:9988"})
@RequestMapping("/instore")
public class InStoreController {

    @Resource
    IInStoreService inStoreService;

    @Resource
    IStoreService storeService;

    @GetMapping("/instore-page-list")
    public Result getInstorePageList(Page page, InStore inStore) {
        Page storeList = inStoreService.getInstorePageList(page, inStore);
        return Result.ok(storeList);
    }

    @GetMapping("/store-list")
    public Result getStoreList() {
        List<Store> storeList = storeService.getStoreList();
        return Result.ok(storeList);
    }

    @PutMapping("/instore-confirm")
    public Result confirmInstore(@RequestBody InStore inStore,
                                 @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //首先修改入库单状态
        return inStoreService.confirmInStore(inStore, token);
    }

}
