package com.faye.tmall.controller;

import com.faye.tmall.pojo.Category;
import com.faye.tmall.service.CategoryService;
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
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category> categoryList= categoryService.list();
        int total = (int) new PageInfo<Category>(categoryList).getTotal();
        page.setTotal(total);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(Category category, HttpSession httpSession, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.add(category);
        File imageFolder= new File(httpSession.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,category.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs(); //如果/img/category目录不存在，则创建该目录
        uploadedImageFile.getImage().transferTo(file); //通过UploadedImageFile 把浏览器传递过来的图片保存在上述指定的位置
        BufferedImage img = ImageUtil.change2jpg(file);//通过ImageUtil.change2jpg(file); 确保图片格式一定是jpg，而不仅仅是后缀名是jpg
        ImageIO.write(img, "jpg", file); //图片就会存放在:mytmall\target\mytmall\img\category 这里
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession httpSession){
        categoryService.delete(id);
        File imgFilePath = new File(httpSession.getServletContext().getRealPath("img/category"));
        File file = new File(imgFilePath, id + ".jpg");
        file.delete();
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String editPage(int id, Model model){
        model.addAttribute("category", categoryService.get(id));
        return "admin/editCategory";
    }
    @RequestMapping("admin_category_update")
    public String update(Category category,HttpSession httpSession, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(category);
        MultipartFile image = uploadedImageFile.getImage();
        if (null != image && !image.isEmpty()) {
            File file = new File(httpSession.getServletContext().getRealPath("img/category"), category.getId() + ".jpg");
            image.transferTo(file);
            ImageIO.write(ImageUtil.change2jpg(file), "jpg", file);
        }
        return "redirect:/admin_category_list";
    }
}
