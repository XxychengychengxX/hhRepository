package com.project.hhrepository.controller;

import com.project.hhrepository.entity.Result;
import com.project.hhrepository.entity.Store;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IStoreService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author ychengycheng
 * @since 2023-07-23
 */
@RestController
@RequestMapping("/store")
public class StoreController {
    @Resource
    IStoreService storeService;


    /*
     * /store/store-page-list?storeName=&storeAddress=&concat=&phone=&pageSize=5&pageNum=1&totalNum=0
     * */
    @GetMapping("/store-page-list")

    public Result getStorePageList(Page page, Store store) {
        page = storeService.getStorePageList(page, store);
        return Result.ok(page);
    }

    /*
     * store-num-check?storeNum=HK_Repository
     * */

    @GetMapping("/store-num-check")
    public Result checkStoreNum(@RequestParam String storeNum) {
        Boolean aBoolean = storeService.checkStoreNum(storeNum);
        return Result.ok(!aBoolean);

    }

    @PostMapping("/store-add")
    public Result addStore(@RequestBody Store store) {
        boolean save = storeService.addStore(store);
        if (save)
            return Result.ok("添加仓库成功");
        else
            return Result.err(Result.CODE_ERR_BUSINESS, "添加仓库失败,请检查服务器后重试");
    }




    @PutMapping("/store-update")
    public Result updateStore(@RequestBody Store store) {
        boolean b = storeService.updateStore(store);
        if (b)
            return Result.ok("仓库信息修改成功");
        return Result.err(Result.CODE_ERR_BUSINESS,"仓库信息修改失败,请检查服务器后重试");

    }

    @DeleteMapping("/store-delete/{storeId}")
    public Result deleteStore(@PathVariable Integer storeId){
        return storeService.deleteStore(storeId);
    }
}
