package com.libmis.controller;

import com.libmis.entity.OperationLog;
import com.libmis.service.OperationLogService;
import com.libmis.utils.PageQuery;
import com.libmis.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/operationLog") // 具体url待定
public class OperationController {

    @Autowired
    private PageQuery pageQuery;
    @Autowired
    private OperationLogService operationLogService;

    /**
     * 查询所有日志信息(没有考虑分页查询)
     *
     * @return 所有日志信息，封装在Result 的 data 中
     */
    @GetMapping("/logsList")
    public Result<?> logsList() {
        try {
            List<OperationLog> logsList = operationLogService.list();
            return Result.success(logsList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 根据userId 查询日志信息
     * @param operationLogId 前端发来的userId
     * @return 查询的结果, 一个user实例
     */
    @GetMapping("/find/{operationLogId}")
    public Result find(@PathVariable Integer operationLogId) {
        try {
            OperationLog log = operationLogService.getById(operationLogId);
            return Result.success(log);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     * 分页显示日志信息
     * @param pageNum 当前页数
     * @param pageSize 每页的日志记录数
     * @return 对应页的日志信息
     */
    @GetMapping("/operationLogsByPage")
    public Result<?> userListByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "5")Integer pageSize
                                    ) {
        try {
            return pageQuery.pageQuery("operationLog", pageNum, pageSize, null, null);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }

    /**
     *查询、检索, 分页显示日志信息
     * @param pageNum 当前页数
     * @param pageSize 每页的日志记录数
     * @param search  查询关键词
     * @return 对应页的日志信息
     */
    @GetMapping("/BySearchPage")
    public Result<?> operationLogsListByConditionPage(@RequestParam(defaultValue = "operationLogs")String type,
                                              @RequestParam(defaultValue = "1")Integer pageNum,
                                              @RequestParam(defaultValue = "5")Integer pageSize,
                                              @RequestParam(defaultValue = "")String search) {
        try{
        return pageQuery.pageQuery(type, pageNum, pageSize, search, null);}
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("501", e.getMessage());
        }
    }


}