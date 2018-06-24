package com.faye.tmall.service;

import com.faye.tmall.pojo.Category;
import com.faye.tmall.utils.Page;

import java.util.List;

public interface CategoryService {
    Category get(int id);
    List<Category> list();
//    int total();
    void add(Category category);
    void delete(int id);
    void update(Category category);
}
