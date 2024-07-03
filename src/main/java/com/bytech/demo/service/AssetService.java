package com.bytech.demo.service;

import com.bytech.demo.dao.AssetRepository;
import com.bytech.demo.entity.Asset;
import com.bytech.demo.entity.ChartResponse;
import com.bytech.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private FinanceService financeService;


    public Asset updateAmount(Asset asset, Integer amount) {
        Integer currentAmount = asset.getAmount();
        asset.setAmount(currentAmount + amount);
        return assetRepository.save(asset);
    }

    public Asset create(String symbol, Integer amount, Person person) {
        Asset asset = new Asset();
        asset.setSymbol(symbol);
        asset.setAmount(amount);
        asset.setPerson(person);
        ChartResponse chartData = financeService.getChartData(symbol);
        asset.setLastPrice(chartData.getChart().getResult().get(0).getMeta().getRegularMarketPrice());
        asset.setCurrency(chartData.getChart().getResult().get(0).getMeta().getCurrency());
        return assetRepository.save(asset);
    }

    public Asset updateAsset(Asset asset) {
        ChartResponse chartData = financeService.getChartData(asset.getSymbol());
        asset.setLastPrice(chartData.getChart().getResult().get(0).getMeta().getRegularMarketPrice());
        return assetRepository.save(asset);
    }

    public Asset customUpdateAsset(Asset asset, String symbol, String currency, Integer amount, Integer lastPrice) {
        asset.setSymbol(symbol);
        asset.setCurrency(currency);
        asset.setLastPrice(lastPrice);
        asset.setAmount(amount);
        return assetRepository.save(asset);
    }

    public void delete(Asset asset) {
        assetRepository.delete(asset);
    }
}
