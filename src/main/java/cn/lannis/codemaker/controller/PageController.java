package cn.lannis.codemaker.controller;

import cn.lannis.codemaker.core.CodeMakerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * <p>描述：页面跳转</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-11-30 11:02</p>
 */
@Controller
@Slf4j
public class PageController {
    @Resource
    CodeMakerConfig codeMakerConfig;

    @GetMapping("/{view}.html")
    public String toPage(@PathVariable("view")String view,Model model){
        log.info("{}",codeMakerConfig);
        model.addAttribute("test",codeMakerConfig);
        return view;
    }
}
