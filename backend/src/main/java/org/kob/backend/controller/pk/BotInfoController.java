package org.kob.backend.controller.pk;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {

    @RequestMapping("getinfo")

    public Map<String, String> getinfo() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("mid", "Faker");
        map.put("ADC", "Gumayusi");

        return map;
    }
}
