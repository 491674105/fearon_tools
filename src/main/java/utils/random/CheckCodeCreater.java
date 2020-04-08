package utils.random;

import java.util.Random;

/**
 * @description: 随机验证码生成器
 * @author: Fearon
 * @create: 2018-04-14 16:24
 **/
public class CheckCodeCreater {
    private Long seed;

    public CheckCodeCreater() {}

    public CheckCodeCreater(long seed) {
        this.seed = seed;
    }

    public long codeCreate(long left, long right) {
        Random random;
        if (null == this.seed)
            random = new Random();
        else
            random = new Random(this.seed);

        return Math.abs(random.nextLong() % (right - left) + left - 1);
    }
}
