package com.insung.knucsesolve.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {
    private PageUtil() {
    }

    private static final int PAGE_NUMBER_LIST_SIZE = 10;
    private static final int PAGE_NUMBER_LIMIT = 1000;


    public static List<Integer> makePageNumberList(Integer entityCount, Integer pageSize, Integer currentPageNumber) {
        List<Integer> pageNumberList = new ArrayList<>();
        int prevPageNumber = ((currentPageNumber - 1) / PAGE_NUMBER_LIST_SIZE) * PAGE_NUMBER_LIST_SIZE - PAGE_NUMBER_LIST_SIZE + 1;
        if (prevPageNumber > 0) {
            pageNumberList.add(prevPageNumber);
        }
        else {
            pageNumberList.add(null);
        }

        int totalPage = ((entityCount - 1) / pageSize) + 1;
        int firstPageNumber = ((currentPageNumber - 1) / PAGE_NUMBER_LIST_SIZE) * PAGE_NUMBER_LIST_SIZE + 1;
        int lastPageNumber = (((currentPageNumber - 1) / PAGE_NUMBER_LIST_SIZE) + 1) * PAGE_NUMBER_LIST_SIZE;
        int endPageNumber = Math.min(totalPage, lastPageNumber);

        for (int i = firstPageNumber; i < endPageNumber + 1; i++) {
            pageNumberList.add(i);
        }


        int nextPageNumber = (((currentPageNumber - 1) / PAGE_NUMBER_LIST_SIZE) + 1) * PAGE_NUMBER_LIST_SIZE + 1;
        if (nextPageNumber <= totalPage && nextPageNumber <= PAGE_NUMBER_LIMIT) {
            pageNumberList.add(nextPageNumber);
        }
        else {
            pageNumberList.add(null);
        }
        return pageNumberList;
    }

    public static Integer processPageNumber(Integer entityCount, Integer pageSize, Integer currentPageNumber) {
        int totalPage = ((entityCount - 1) / pageSize) + 1;
        int maxPage = Math.min(totalPage, PAGE_NUMBER_LIMIT);

        if (currentPageNumber > maxPage) {
            return maxPage;
        }
        else if (currentPageNumber < 1) {
            return 1;
        }
        else {
            return currentPageNumber;
        }
    }
}
