package com.faye.tmall.utils;

public class Page {
    private int start; //开始页数
    private int count; //每页显示个数
    private int total; //总个数
    private String param; //参数,这个属性在后续有用到，但是分类的分页查询里并没有用到，请忽略
    private static final int defaultCount = 5; //默认每页显示5条

    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public Page (){
        count = defaultCount;
    }
    public Page(int start, int count) {
        this();
        this.start = start;
        this.count = count;
    }

    public boolean isHasPreviouse(){ //判断是否有前一页
        if(start==0) return false;
        return true;
    }
    public boolean isHasNext(){
        if(start==getLast()) return false;
        return true;
    }

    public int getTotalPage(){ // 根据每页显示的数量count以及总共有多少条数据total，计算出总共有多少页
        int totalPage;
        if (0 == total % count) totalPage = total /count;// 假设总数是50，是能够被5整除的，那么就有10页
        else totalPage = total / count + 1;// 假设总数是51，不能够被5整除的，那么就有11页

        if(0==totalPage) totalPage = 1;
        return totalPage;
    }

    public int getLast(){ // 算出最后一页的数值是多少
        int last;
        if (0 == total % count) last = total - count;// 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
        else last = total - total % count;// 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
        last = last<0?0:last;
        return last;
    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPreviouse()=" + isHasPreviouse() + ", isHasNext()="
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public String getParam() {
        return param;
    }
    public void setParam(String param) {
        this.param = param;
    }

}