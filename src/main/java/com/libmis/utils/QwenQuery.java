package com.libmis.utils;

import java.util.Arrays;
import java.lang.System;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

public class QwenQuery {
    /**
     * 通义千问
     * @param systemPrompt 给大模型的提示词，这个很简单，默认可以设置balabala
     * @param materials 给大模型解析的数据，这个需要进一步处理，有两个难点
     *                  1. 现在查询得到的sql数据基本都是字典格式或者字典列表模式，直接输出String它是否能看懂？
     *                  2. 根据前端的需要，需要编写更复杂的sql语句。
     *                  可以先编一两个方法打个样给前端。
     * @param searchKeyWord 要求大模型做的
     * @return
     * @throws ApiException
     * @throws NoApiKeyException
     * @throws InputRequiredException
     */
    public static Result<?> callWithMessage(String systemPrompt, Object materials, String searchKeyWord){
        try {
            Generation gen = new Generation();
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content(systemPrompt)
                    .build();
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content("根据以下这份材料, "+ searchKeyWord+ "\n" +materials)
                    .build();
            GenerationParam param = GenerationParam.builder()
                    .apiKey("sk-246d78b4c3a0444da6c1ede7cec37ba4")
                    .model("qwen-turbo")
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();
            GenerationResult result = gen.call(param);
            String output = result.getOutput().getChoices().get(0).getMessage().getContent();
            return Result.success(output);
        } catch (Exception e) {
            System.exit(0);
            return Result.error("500", "请参考文档：https://help.aliyun.com/zh/model-studio/developer-reference/error-code"
            + "错误信息："+e.getMessage());
        }

    }

}