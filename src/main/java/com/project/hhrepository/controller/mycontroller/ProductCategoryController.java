/**
 * @author Valar Morghulis
 * @Date 2023/8/13
 */
package com.project.hhrepository.controller.mycontroller;

import com.project.hhrepository.entity.ProductType;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.service.IProductTypeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Resource
    IProductTypeService productTypeService;

    @GetMapping("/product-category-tree")
    public Result getProductCatagoryTree(){
        List<ProductType> productTypeListTree = productTypeService.getProductTypeListTree();
        return Result.ok(productTypeListTree);
    }
}
