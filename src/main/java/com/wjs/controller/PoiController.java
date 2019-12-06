package com.wjs.controller;

import com.wjs.task.PoiTask;
import com.wjs.util.HttpFilePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("timingPoi")
public class PoiController {
    @Autowired
    private PoiTask poiTask;

    @RequestMapping("export")
    public String export(HttpServletRequest request){
        String realPath = HttpFilePath.getRealPath(request, "/statics/Excel");
        poiTask.run(realPath);
        return "ok";
    }


}
