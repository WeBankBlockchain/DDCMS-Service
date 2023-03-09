package com.webank.databrain.model.resp.account;

import com.webank.databrain.model.resp.IdName;
import com.webank.databrain.model.resp.BasePageQueryResult;
import com.webank.databrain.model.resp.PagedResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PageQueryCompanyResponse extends BasePageQueryResult<IdNameWithType> {

    public PageQueryCompanyResponse(PagedResult<IdNameWithType> pagedResult) {
        super(pagedResult);
    }


}