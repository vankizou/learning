package com.zoufanqi.algorithm.bayes;

import java.util.HashMap;
import java.util.Map;

/**
 * 贝叶斯算法，单词错误纠正示例
 * <p>
 * P(A|B) = P(B|A) * P(A) / P(B)
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/2/6 18:08
 **/
public class Bayes {
    /**
     * 学习库，数据越大越准
     */
    private final static String LEARNS = "when you are old and gray and full of sleep, and nodding by the fire take down this book.";
    private final static Map<String, Integer> WORD_COUNT_MAP = new HashMap<>();
    /**
     * 加权因子
     * 比如：位置和字符都匹配则权重更大
     */
    private final static float MATCH_FACTOR = 2f;
    private static int TOTAL_WORD_NUM;

    public static void main(String[] args) {
        initWordPreProb();
        System.out.println(WORD_COUNT_MAP);
        print("when");
        print("you");
        print("whe");
        print("yuu");
        print("yoo");
        print("andd");
        print("abd");
        print("thas");
        print("dawn");
    }

    private static void print(String inputWord) {
        System.out.printf("%-20s", "输入：" + inputWord);
        System.out.println("推测：" + inferWord(inputWord));
    }

    private static String inferWord(String inputWord) {
        if (inputWord == null || (inputWord = inputWord.trim()).isEmpty()) {
            return inputWord;
        }
        final char[] inputWordArr = inputWord.toCharArray();
        // 最高得分
        final double maxScore = inputWordArr.length * MATCH_FACTOR;
        // 与学习库比对的最高得分
        double currMaxScore = 0;
        String currMaxScoreWord = inputWord;
        // 最高得分词先验概率（分数相同时取先验概率高分词）
        double currMaxScoreWordPreWord = 0;
        for (Map.Entry<String, Integer> entry : WORD_COUNT_MAP.entrySet()) {
            final String w = entry.getKey();
            final double currScore = calcScore(inputWordArr, w);
            if (currScore > currMaxScore ||
                    (currScore == currMaxScore && currMaxScoreWordPreWord < (entry.getValue() / TOTAL_WORD_NUM))) {
                currMaxScore = currScore;
                currMaxScoreWord = w;
                currMaxScoreWordPreWord = entry.getValue() / TOTAL_WORD_NUM;
            }
            if (currMaxScore >= maxScore) {
                return w;
            }
        }
        return currMaxScoreWord;
    }

    private static double calcScore(char[] inputWordArr, String dbWord) {
        final char[] dbWordArr = dbWord.toCharArray();
        final int dbWordLen = dbWordArr.length;
        double score = 0;
        for (int i = 0, len = inputWordArr.length; i < len; i++) {
            final char iwc = inputWordArr[i];
            if (dbWordLen > i && iwc == dbWordArr[i]) {
                score += MATCH_FACTOR;
            } else {
                for (char dbw : dbWordArr) {
                    if (dbw == iwc) {
                        score += 1;
                        break;
                    }
                }
            }
        }
        return score;
    }


    /**
     * 计算先验概率
     */
    private static void initWordPreProb() {
        for (String s : LEARNS.split("\\s|,|\\.|\\n|;")) {
            if ((s = s.trim()).isEmpty()) {
                continue;
            }
            WORD_COUNT_MAP.compute(s, (k, v) -> v == null ? 1 : ++v);
        }
        TOTAL_WORD_NUM = WORD_COUNT_MAP.size();
    }

}
