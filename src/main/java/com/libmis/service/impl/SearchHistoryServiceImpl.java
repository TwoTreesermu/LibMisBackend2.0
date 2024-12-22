package com.libmis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libmis.entity.SearchHistory;
import com.libmis.mapper.SearchHistoryMapper;
import com.libmis.service.SearchHistoryService;
import org.springframework.stereotype.Service;


@Service
public class SearchHistoryServiceImpl extends ServiceImpl<SearchHistoryMapper, SearchHistory>
        implements SearchHistoryService {


}
