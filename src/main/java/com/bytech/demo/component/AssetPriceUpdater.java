package com.bytech.demo.component;

import com.bytech.demo.dao.AssetRepository;
import com.bytech.demo.entity.Asset;
import com.bytech.demo.entity.ChartResponse;
import com.bytech.demo.service.AssetService;
import com.bytech.demo.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssetPriceUpdater {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetService assetService;

    @Scheduled(fixedRate = 10000)
    public void updateAssetPrices() throws Exception {
        List<Asset> assets = assetRepository.findAll();
        for (Asset asset : assets) {
            assetService.updateAsset(asset);
        }
    }

}

