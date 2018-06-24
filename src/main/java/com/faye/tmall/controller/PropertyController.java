package com.faye.tmall.controller;

import com.faye.tmall.pojo.Category;
import com.faye.tmall.pojo.Property;
import com.faye.tmall.service.CategoryService;
import com.faye.tmall.service.PropertyService;
import com.faye.tmall.utils.ImageUtil;
import com.faye.tmall.utils.Page;
import com.faye.tmall.utils.UploadedImageFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_property_list")
    public String list(Model model, Page page, int cid){
        Category c = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Property> propertyList= propertyService.list(cid);
        int total = (int) new PageInfo<Property>(propertyList).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + cid);
        model.addAttribute("propertyList", propertyList);
        model.addAttribute("page", page);
        model.addAttribute("c", c);
        return "admin/listProperty";
    }

    @RequestMapping("admin_property_add")
    public String add(Property property) {
        propertyService.add(property);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String delete(int id){
        String cid = String.valueOf(propertyService.get(id).getCid());
        propertyService.delete(id);
        return "redirect:/admin_property_list?cid=" + cid;
    }

    @RequestMapping("admin_property_edit")
    public String editPage(Model model,int id){
        Property property = propertyService.get(id);
        property.setCategory(categoryService.get(property.getCid()));
        model.addAttribute("property", property);
        return "admin/editProperty";
    }
    @RequestMapping("admin_property_update")
    public String update(Property property) {
        propertyService.update(property);
        return "redirect:/admin_property_list?cid=" +property.getCid();
    }
}
