package com.lottery.system.util.resultStrategy.impl;

import com.lottery.system.util.resultStrategy.ResultStrategy;

public class FirstDifferentStrategy implements ResultStrategy {
    @Override
    public int calculateResult(int[] numbers) {
        return 1;
    }
}
