package com.lottery.system.util.resultStrategy;

public class ResultContext {
    private ResultStrategy strategy;

    public void setStrategy(ResultStrategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int[] numbers) {
        return strategy.calculateResult(numbers);
    }
}