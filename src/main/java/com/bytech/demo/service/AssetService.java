package com.bytech.demo.service;

import com.bytech.demo.dao.AssetRepository;
import com.bytech.demo.entity.Asset;
import com.bytech.demo.entity.ChartResponse;
import com.bytech.demo.entity.Person;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    private static final Logger logger = LoggerFactory.getLogger(AssetService.class);


    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private WebSocketService webSocketService;


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
        asset.setLastPrice(chartData.getChart().getResult().getFirst().getMeta().getRegularMarketPrice());
        asset.setCurrency(chartData.getChart().getResult().getFirst().getMeta().getCurrency());
        return assetRepository.save(asset);
    }

    @Transactional
    public Asset updateAsset(Asset asset) throws Exception {
        ChartResponse chartData = financeService.getChartData(asset.getSymbol());
        double lastPrice = chartData.getChart().getResult().get(0).getMeta().getRegularMarketPrice();
        if (lastPrice != asset.getLastPrice()) {
            asset.setLastPrice(lastPrice);
            notifyPriceChangeIfNeeded(asset, lastPrice);
        }
        return assetRepository.save(asset);
    }

    @Transactional
    public Asset customUpdateAsset(Asset asset, String symbol, String currency, Integer amount, double lastPrice) throws Exception {
        asset.setSymbol(symbol);
        asset.setCurrency(currency);
        logger.info("test customUpdateAsset a");
        if (lastPrice != asset.getLastPrice()) {
            logger.info("test customUpdateAsset b");
            asset.setLastPrice(lastPrice);
            notifyPriceChangeIfNeeded(asset, lastPrice);
        }
        asset.setAmount(amount);
        return assetRepository.save(asset);
    }

    public void delete(Asset asset) {
        assetRepository.delete(asset);
    }

    @Cacheable(value = "assetsCache", key = "#assetId")
    public Optional<Asset> findById(Integer assetId) {
        return assetRepository.findById(assetId);
    }

    @Cacheable(value = "assetsCache", key = "#symbol + '-' + #personId")
    public Asset findBySymbolAndPersonId(String symbol, Integer personId) {
        return assetRepository.findBySymbolAndPerson_id(symbol, personId).orElse(null);
    }

    public void notifyPriceChangeIfNeeded(Asset asset, Double newPrice) {
        logger.info("Checking price change for asset {}", asset.getSymbol());
        if (newPrice != null ) {
            Integer userId = asset.getPerson().getId();
            String notification = String.format("Asset %s new price: %.2f", asset.getSymbol(), newPrice);
            webSocketService.sendMessageToUser(String.valueOf(userId), notification);
        } else {
            logger.info("Price did not change or newPrice is null for asset {}", asset.getSymbol());
        }
    }

    @Cacheable(value = "assetsCache")
    public List<Asset> findAll() {
        return assetRepository.findAll();
    }
}
