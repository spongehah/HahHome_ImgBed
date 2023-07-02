package com.spongehah.hahhome.controller.admin;

import com.spongehah.hahhome.pojo.User;
import com.spongehah.hahhome.service.UserService;
import com.spongehah.hahhome.utils.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    
    @Autowired
    private UserService userService;

    /**
     * 跳转到admin登录页面
     * @return
     */
    @GetMapping({"/toLogin","login"})
    public String toLogin(){
        return "admin/login";
    }

    /**
     * 登录验证
     * @param username
     * @param password
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/admin/login")
    public String login(String username, String password, Model model, HttpSession session){
        try{
            User user = userService.getUserByUsernameAndPassword(username,password);
            if (user!=null){
                session.setAttribute(SessionInfo.LOGIN_INFO,user);
                return "redirect:/admin/index";
            }else {
                model.addAttribute("msg","用户名或密码错误");
                return  "admin/login";
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg","系统繁忙,请稍后再试");
            return  "admin/login";
        }
    }

    /**
     * 登录成功后跳转到admin首页
     * @return
     */
    @GetMapping("/admin/index")
    public String adminIndex(){
        return "admin/index";
    }

    /**
     * 登出
     */
    @GetMapping("/admin/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/toIndex";
    }

    /**
     * 未授权页面返回
     * @return
     */
    @RequestMapping("/toUnauthorized")
    public  String toUnauthorized(){
        return "error/403";
    }



}
