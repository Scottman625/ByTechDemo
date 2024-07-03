package com.bytech.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChartResponse {

    private Chart chart;

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Chart {
        private List<Result> result;

        public List<Result> getResult() {
            return result;
        }

        public void setResult(List<Result> result) {
            this.result = result;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Result {
            private Meta meta;

            public Meta getMeta() {
                return meta;
            }

            public void setMeta(Meta meta) {
                this.meta = meta;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Meta {
                private String currency;
                private String symbol;
                private String exchangeName;
                private String fullExchangeName;
                private String instrumentType;
                private double regularMarketPrice;

                // Getters and Setters

                public String getCurrency() {
                    return currency;
                }

                public void setCurrency(String currency) {
                    this.currency = currency;
                }

                public String getSymbol() {
                    return symbol;
                }

                public void setSymbol(String symbol) {
                    this.symbol = symbol;
                }

                public String getExchangeName() {
                    return exchangeName;
                }

                public void setExchangeName(String exchangeName) {
                    this.exchangeName = exchangeName;
                }

                public String getFullExchangeName() {
                    return fullExchangeName;
                }

                public void setFullExchangeName(String fullExchangeName) {
                    this.fullExchangeName = fullExchangeName;
                }

                public String getInstrumentType() {
                    return instrumentType;
                }

                public void setInstrumentType(String instrumentType) {
                    this.instrumentType = instrumentType;
                }

                @JsonProperty("regularMarketPrice")
                public double getRegularMarketPrice() {
                    return regularMarketPrice;
                }

                public void setRegularMarketPrice(double regularMarketPrice) {
                    this.regularMarketPrice = regularMarketPrice;
                }
            }
        }
    }
}
