package com.fechin.domain;

import java.util.List;

/**
 *
 * @param <E>
 */
public class PageBean<E> {
    private Integer pageIndex;//当前的页数
    private Integer showEveryPageNumber;//每页展示的个数
    private Integer firstNumberOnPage;//当前页第一个
    private Integer allListNumbers;//总记录数
    private Integer allPages;//总页数

    private Integer leftNumber;//左边的页码
    private Integer rightNumber;//右边的页码

    private List<E> list;

    private static Integer showNumber = 5;//导航栏默认显示的页数

    public PageBean(Integer pageIndex, Integer showEveryPageNumber, Integer allListNumbers) {
        this.pageIndex = pageIndex;
        this.showEveryPageNumber = showEveryPageNumber;
        this.allListNumbers = allListNumbers;
        init();
    }

    public void init() {
        //当前页的第一个索引就可以计算出来
        firstNumberOnPage = (pageIndex - 1) * showEveryPageNumber;
        //总的页数就可以计算出来
        allPages = (int) (Math.ceil(allListNumbers * 1.0 / showEveryPageNumber));
        //导航栏默认显示5页
        if (allPages <= showNumber) {
            leftNumber = 1;
            rightNumber = allPages;
        } else if (pageIndex + showNumber / 2 > allPages) {
            leftNumber = allPages - 4;
            rightNumber = allPages;
        } else if (pageIndex - showNumber / 2 < 1) {
            leftNumber = 1;
            rightNumber = showNumber;
        } else {
            leftNumber = pageIndex - showNumber / 2;
            leftNumber = pageIndex + showNumber / 2;
        }
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getShowEveryPageNumber() {
        return showEveryPageNumber;
    }

    public void setShowEveryPageNumber(Integer showEveryPageNumber) {
        this.showEveryPageNumber = showEveryPageNumber;
    }

    public Integer getFirstNumberOnPage() {
        return firstNumberOnPage;
    }

    public void setFirstNumberOnPage(Integer firstNumberOnPage) {
        this.firstNumberOnPage = firstNumberOnPage;
    }

    public Integer getAllListNumbers() {
        return allListNumbers;
    }

    public void setAllListNumbers(Integer allListNumbers) {
        this.allListNumbers = allListNumbers;
    }

    public Integer getAllPages() {
        return allPages;
    }

    public void setAllPages(Integer allPages) {
        this.allPages = allPages;
    }

    public Integer getLeftNumber() {
        return leftNumber;
    }

    public void setLeftNumber(Integer leftNumber) {
        this.leftNumber = leftNumber;
    }

    public Integer getRightNumber() {
        return rightNumber;
    }

    public void setRightNumber(Integer rightNumber) {
        this.rightNumber = rightNumber;
    }

    public static Integer getShowNumber() {
        return showNumber;
    }

    public static void setShowNumber(Integer showNumber) {
        PageBean.showNumber = showNumber;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "pageIndex=" + pageIndex +
                ", showEveryPageNumber=" + showEveryPageNumber +
                ", firstNumberOnPage=" + firstNumberOnPage +
                ", allListNumbers=" + allListNumbers +
                ", allPages=" + allPages +
                ", leftNumber=" + leftNumber +
                ", rightNumber=" + rightNumber +
                ", lists=" + list +
                '}';
    }
}
