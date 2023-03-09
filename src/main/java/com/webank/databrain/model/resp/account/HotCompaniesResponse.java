package com.webank.databrain.model.resp.account;

import com.webank.databrain.model.resp.IdName;
import com.webank.databrain.model.resp.BaseHotResponse;
import lombok.Data;

import java.util.List;

@Data
public class HotCompaniesResponse extends BaseHotResponse<IdNameWithType> {

    public HotCompaniesResponse(List<IdNameWithType> idNames) {
        super(idNames);
    }
}
