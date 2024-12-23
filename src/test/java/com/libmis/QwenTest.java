package com.libmis;

import java.util.*;
import java.lang.System;
import com.libmis.utils.QwenQuery;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.libmis.entity.Book;
import com.libmis.service.BookService;
import com.libmis.service.BorrowRecordService;
import com.libmis.service.UserService;
import com.libmis.utils.Jwt;
import com.libmis.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest
@Component
public class QwenTest {
    @Autowired
    UserService userService;
    @Autowired
    BorrowRecordService borrowRecordService;
    @Autowired
    private BookService bookService;

    @Test
    public void callWithMessage() throws ApiException, NoApiKeyException, InputRequiredException {
        String systemPrompt = "你是一个高校图书馆管理系统的数据分析助手";
        String materials = "[数据结构与算法分析（C语言描述）《数据结构与算法分析》详细介绍了数据结构与算法设计的基本概念，结合C语言的实现方式，帮助读者提高编程能力。, 线性代数（第5版）《线性代数》是由麻省理工学院教授Gilbert Strang所著的经典教材，内容涉及线性代数的基本概念、矩阵运算、特征值等。, 物理学教程（第8版）《物理学教程（第8版）》内容覆盖了经典力学、热力学、电磁学、光学等多个领域，是大学物理课程的基础教材。, 大学英语（第4版）《大学英语（第4版）》是面向大学生英语学习的经典教材，涵盖基础语法、听说读写技巧，并附带大量练习。]\n";
        String searchKeyWord = "根据这个用户的借书记录，分析用户的借阅偏好，并且给出符合其偏好的借阅建议";
        try {
            Generation gen = new Generation();
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content(systemPrompt)
                    .build();
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content("根据以下这份材料"+ searchKeyWord+ "\n" +materials)
                    .build();
            GenerationParam param = GenerationParam.builder()
                    // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                    .apiKey("sk-246d78b4c3a0444da6c1ede7cec37ba4")
                    // 模型列表：https://help.aliyun.com/zh/model-studio/getting-started/models
                    .model("qwen-turbo")
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();
            GenerationResult result = gen.call(param);
            System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent());
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.err.println("错误信息："+e.getMessage());
            System.out.println("请参考文档：https://help.aliyun.com/zh/model-studio/developer-reference/error-code");
        }
        System.exit(0);

    }

    @Test
    void userStaticAnalysis() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySW5mbyI6eyJ1c2VyTmFtZSI6ImVseXNpYSIsInVzZXJJZCI6IjExNDUxNCJ9LCJleHAiOjE3MzQ5NDQ1MTR9.683PlUHDtS8bRXR-h1mtixY_l2olWcNAEXib99qwqH8";
        Map<String, Object> userMap = Jwt.verifyToken(token);
        String userName = (String) userMap.get("userName");
        int userId = userService.getByUserName(userName).getUserId();
        List<Integer> bRList = borrowRecordService.getByUserId(userId);
        System.out.println("**********"+ bRList);
        List<Book> booksList = bookService.listByIds(bRList);
        List<String> inputList = new ArrayList<>();
        for (Book book : booksList) {
            inputList.add("书籍标题："+ book.getTitle()+ "书籍简介："+ book.getIntroduction());
        }
//        System.out.println(inputList);
        Result<?> result = QwenQuery.callWithMessage("你是一个高校图书馆管理系统的数据分析助手", inputList, "根据这个用户的借书记录，分析用户的借阅偏好，并且给出符合其偏好的借阅建议");
        System.out.println(result.getData());
        // userBR是一个带有bookId们的entity
//        List<?> books = borrowRecordService.listByIds(userBR.getBookId())
//        System.out.println("userBR:"+ userBR);
//        return Result.success(userBR);
    }
}