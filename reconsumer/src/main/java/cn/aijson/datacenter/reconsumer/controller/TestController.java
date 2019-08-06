package cn.aijson.datacenter.reconsumer.controller;

import cn.aijson.datacenter.reconsumer.entity.SysPerm;
import cn.aijson.datacenter.reconsumer.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class TestController {
    @Autowired
    RestTemplate restTemplate;
//    @RequiresPermissions("replicator:banner:list")


    @GetMapping("/get")
    @ResponseBody
    public Json bannerList(){
        return restTemplate.getForObject("http://localhost:8000/get", Json.class);
    }

    @GetMapping("/all_banners")
    @ResponseBody
    public Json banne(){
        return restTemplate.getForObject("http://localhost:7078/all_banners", Json.class);
    }

    public static void main(String[] args) {
        SysPerm sysPerm = new SysPerm();
        SysPerm sysPerm1=new SysPerm();
        sysPerm1.setType(1);
        sysPerm1.setName("2321");
        sysPerm1.setPval("sys:opt");

        List<Long> perms = new ArrayList<>();
        perms.add(1L);
        perms.add(2L);
        perms.add(3L);
        sysPerm.setPval("sys:123");

        System.out.println(sysPerm.toString());
    }
}
