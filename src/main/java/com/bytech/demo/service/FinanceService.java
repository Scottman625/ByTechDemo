package com.bytech.demo.service;


import com.bytech.demo.entity.ChartResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FinanceService {

    private final RestTemplate restTemplate;

    public FinanceService() {
        this.restTemplate = new RestTemplate();
    }

    public ChartResponse getChartData(String symbol) {
        String url = "https://query1.finance.yahoo.com/v8/finance/chart/" + symbol;
        return restTemplate.getForObject(url, ChartResponse.class);
    }

    public double getRegularMarketPrice(String symbol) {
        ChartResponse chartResponse = getChartData(symbol);
        if (chartResponse != null && chartResponse.getChart() != null &&
                chartResponse.getChart().getResult() != null && !chartResponse.getChart().getResult().isEmpty()) {
            return chartResponse.getChart().getResult().get(0).getMeta().getRegularMarketPrice();
        }
        throw new RuntimeException("Unable to retrieve regularMarketPrice for symbol: " + symbol);
    }
}

