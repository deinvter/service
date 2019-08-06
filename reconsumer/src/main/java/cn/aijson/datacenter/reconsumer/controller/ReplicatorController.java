package cn.aijson.datacenter.reconsumer.controller;

import cn.aijson.datacenter.reconsumer.entity.Banner;
import cn.aijson.datacenter.reconsumer.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class ReplicatorController {
    @Autowired
    RestTemplate restTemplate;


    @RequestMapping(value = "/banner",method = RequestMethod.POST)
    @ResponseBody
    public Json addBanner(@RequestBody @Valid Banner banner){
        return restTemplate.postForObject("http://localhost:7078/banner",banner, Json.class);
    }

}
