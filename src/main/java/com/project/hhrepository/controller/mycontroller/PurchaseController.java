/**
 * @author Valar Morghulis
 * @Date 2023/8/7
 */
package com.project.hhrepository.controller.mycontroller;

import com.project.hhrepository.entity.*;
import com.project.hhrepository.entity.myentity.Purchase;
import com.project.hhrepository.page.Page;
import com.project.hhrepository.service.IBuyListService;
import com.project.hhrepository.service.IInStoreService;
import com.project.hhrepository.service.IStoreService;
import com.project.hhrepository.utils.WarehouseConstants;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Resource
    IBuyListService buyListService;

    @Resource
    IStoreService storeService;
    @Resource
    IInStoreService inStoreService;

    @PostMapping("/purchase-add")
    public Result addInStoreOrder(@RequestBody BuyList buyList,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {



      return buyListService.addBuyList(buyList,token);

    }

    @GetMapping("/store-list")
    public Result getStoreList() {
        List<Store> storeList = storeService.getStoreList();
        return Result.ok(storeList);
    }

    @GetMapping("/purchase-page-list")
    //用对象来接受url时不需要添加注解
    public Result getPurchasePageList(Page page, Purchase purchase) {
        Page purchasePageList = buyListService.getPurchasePageList(page, purchase);
        return Result.ok(purchasePageList);
    }

    @DeleteMapping("/purchase-delete/{buyId}")
    public Result deleteBuyListById(@PathVariable Integer buyId) {
        Boolean aBoolean = buyListService.deleteBuyListById(buyId);
        if (aBoolean)
            return Result.ok("删除对应采购单成功!");
        return Result.err(Result.CODE_ERR_BUSINESS, "删除对应采购单失败,请检查服务器后重试");
    }

    @PutMapping("/purchase-update")
    public Result updateBuyListById(@RequestBody BuyList buyList) {
        Boolean aBoolean = buyListService.updateBuyListById(buyList);
        if (aBoolean)
            return Result.ok("对应信息更新成功");
        return Result.err(Result.CODE_ERR_BUSINESS, "信息更新失败,请稍后重试");
    }

    @PostMapping("/in-warehouse-record-add")
    public Result addInStoreRecord(@RequestBody Purchase purchase,
                                   @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        InStore inStore = new InStore();

        inStore.setStoreId(purchase.getStoreId());
        inStore.setInNum(purchase.getFactBuyNum());
        inStore.setProductId(purchase.getProductId());
        inStore.setIsIn(String.valueOf(0));

        return inStoreService.addInStoreRecord(inStore, token, purchase.getBuyId());
    }
}
