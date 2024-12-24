package com.libmis.controller;

import com.libmis.entity.Comment;
import com.libmis.service.CommentService;
import com.libmis.service.UserService;
import com.libmis.utils.PageQuery;
import com.libmis.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
@RestController
@RequestMapping("/books")
public class CommentController {
    /*
    ** 分页查询 Book只查title
    ** User只查Username
    ** 借阅借出的书不能再借了
    ** 评论也要返回UserName
    ** borrow只发bookId
    ** Comment和Borrow要新建Controller，return放在Borrow里
    ** Borrow检查的时候多带一个条件userId。
    ** 操纵某对象对应的表的
    ** 预约也要controller
    ** 评论也做Controller
    ** 新增评论要返回用户名，
     */
    @Autowired
    private CommentService commentService;
    @Autowired
    private PageQuery pageQuery;
    @Autowired
    private UserService userService;

    /**
     * 增加评论
     */
    @PostMapping("/sendComment")
    public Result<?> sendComment(@RequestBody Comment comment) {
        try {
            comment.setCommentDate(new Date());
//            Map<Object, String> userMap = Jwt.verifyToken(token);
//            int userId = userService.getByUserName(userMap.get("userName")).getUserId();
            int userId = 1;
            comment.setUserId(userId); // 之后从令牌获取
            String userName = userService.getById(userId).getUserName();
            commentService.saveOrUpdate(comment);
            return Result.success(userName);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/delComment")
    public Result<?> del(@RequestBody Comment comment) {
        try {
            commentService.removeById(comment.getCommentId());
//            operationLogService.saveOperationLog(token, "deleteComment");
            return Result.success("评论删除成功了喵。");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 查看评论，这里是分页查询
     * 理论上前端也要提供分页参数
     * @return
     */
    @GetMapping("/getCommentByPage")
    public Result<?> getCommentByPage() {
        try {
            return pageQuery.pageQuery("comment", 2, 3, null, null);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 非分页查询的查看评论
     * @return
     */
    @GetMapping("getComment")
    public Result<?> getComment(){
        try {
            return Result.success(commentService.list());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 给评论点赞
     * @param comment
     * @return
     */
    @PostMapping("/likeComment")
    public Result<?> likeComment(@RequestBody Comment comment) {
        try {
            comment.setLikes(comment.getLikes() + 1);
            commentService.saveOrUpdate(comment);
            return Result.success("点赞成功了喵。");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

}
