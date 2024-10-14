package com.lottery.system.util;

import com.lottery.system.util.resultStrategy.ResultContext;
import com.lottery.system.util.resultStrategy.impl.AllSameStrategy;
import com.lottery.system.util.resultStrategy.impl.DefaultStrategy;
import com.lottery.system.util.resultStrategy.impl.FirstDifferentStrategy;
import com.lottery.system.util.resultStrategy.impl.SumTwoStrategy;

public class ResultCalculator {

    public static int calculateResult(int[] numbers) {
        ResultContext context = new ResultContext();

        int sum = numbers[0] + numbers[1] + numbers[2];
        if (sum == 2) {
            context.setStrategy(new SumTwoStrategy());
        } else if (numbers[0] == numbers[1] && numbers[1] == numbers[2]) {
            context.setStrategy(new AllSameStrategy());
        } else if (numbers[0] != numbers[1] && numbers[0] != numbers[2]) {
            context.setStrategy(new FirstDifferentStrategy());
        } else {
            context.setStrategy(new DefaultStrategy());
        }

        return context.executeStrategy(numbers);
    }
}
