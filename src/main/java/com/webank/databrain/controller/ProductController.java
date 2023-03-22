package com.webank.databrain.controller;

import com.webank.databrain.service.ProductService;
import com.webank.databrain.vo.common.CommonPageQueryRequest;
import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.common.HotDataRequest;
import com.webank.databrain.vo.request.product.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/pageQueryProduct")
    public CommonResponse pageQueryProduct(@RequestBody @Valid PageQueryProductRequest request){
        return productService.pageQueryProducts(request);
    }
    @PostMapping(value = "/pageQueryMyProduct")
    public CommonResponse pageQueryMyProduct(@RequestBody @Valid PageQueryProductRequest request){
        return productService.pageQueryMyProduct(request);
    }

    @PostMapping(value = "/queryProductById")
    public CommonResponse queryProductById(@RequestBody @Valid QueryProductByIdRequest queryProductRequest) {
        return productService.getProductDetail(queryProductRequest.getProductId());
    }

    @PostMapping(value = "/getProductsByProviderId")
    public CommonResponse getProductsByProviderId() {
        return productService.getProductsByProviderId();
    }

    @PostMapping(value = "/getHotProducts")
    public CommonResponse getHotProducts(@RequestBody @Valid HotDataRequest request){
        return productService.getHotProducts(request);
    }
    @PostMapping(value = "/createProduct")
    public CommonResponse createProduct(@RequestBody @Valid CreateProductRequest createProductRequest) throws Exception{
        return productService.createProduct(createProductRequest);
    }

    @PostMapping(value = "/approveProduct")
    public CommonResponse approveProduct(@RequestBody @Valid ApproveProductRequest approveProductRequest) throws Exception{
        return productService.approveProduct(approveProductRequest);
    }

    @PostMapping(value = "/updateProduct")
    public CommonResponse updateProduct(@RequestBody @Valid UpdateProductRequest updateProductRequest) throws Exception{
        return productService.updateProduct(updateProductRequest);
    }
}
