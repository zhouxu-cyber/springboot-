package org.kob.backend.controller.pk;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {

    @RequestMapping("getinfo")

    public List<String> getinfo() {
        List<String> list = new ArrayList<String>();

        list.add("Doran");
        list.add("Oner");
        list.add("Faker");
        list.add("Gumayusi");
        list.add("Keria");

        return list;
    }
}
