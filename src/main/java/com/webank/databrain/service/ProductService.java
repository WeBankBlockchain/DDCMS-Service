package com.webank.databrain.service;

import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.common.HotDataRequest;
import com.webank.databrain.vo.request.product.ApproveProductRequest;
import com.webank.databrain.vo.request.product.CreateProductRequest;
import com.webank.databrain.vo.request.product.PageQueryProductRequest;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;

public interface ProductService {
    CommonResponse getHotProducts(HotDataRequest request);

    CommonResponse pageQueryProducts(PageQueryProductRequest request);

    CommonResponse getProductDetail(Long productId);

    CommonResponse createProduct(CreateProductRequest productRequest) throws TransactionException;

    CommonResponse approveProduct(ApproveProductRequest productRequest) throws TransactionException;

    CommonResponse pageQueryMyProduct(PageQueryProductRequest request);

    CommonResponse getProductsByProviderId();
}
