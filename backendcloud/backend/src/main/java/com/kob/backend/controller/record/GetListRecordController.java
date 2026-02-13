package com.kob.backend.controller.record;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.service.reocord.GetListRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetListRecordController {

    @Autowired
    private GetListRecordService getListRecordService;

    @GetMapping("/api/record/getlist/")
    JSONObject getlist(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getListRecordService.GetList(page);
    }

}
