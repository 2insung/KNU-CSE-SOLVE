package com.project.web.util;

import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {
    private PageUtil() {
    }

    private static final int PAGE_NUMBER_LIST_SIZE = 2;
    private static final int PAGE_NUMBER_LIMIT = 1000;

    public static Integer makePrevPageNumber(Integer currentPageNumber){
        int prevPageNumber = ((currentPageNumber - 1) / PAGE_NUMBER_LIST_SIZE) * PAGE_NUMBER_LIST_SIZE - PAGE_NUMBER_LIST_SIZE + 1;
        return prevPageNumber > 0 ? prevPageNumber : null;
    }

    public static Integer makeNextPageNumber(Integer entityCount, Integer pageSize, Integer currentPageNumber){
        int totalPage = ((entityCount - 1) / pageSize) + 1;
        int nextPageNumber =  (((currentPageNumber - 1) / PAGE_NUMBER_LIST_SIZE) + 1) * PAGE_NUMBER_LIST_SIZE + 1;
        return nextPageNumber <= totalPage ? nextPageNumber : null;
    }

    public static List<Integer> makePageNumberList(Integer entityCount, Integer pageSize, Integer currentPageNumber){
        int totalPage = ((entityCount - 1) / pageSize) + 1;
        int firstPageNumber = ((currentPageNumber - 1) / PAGE_NUMBER_LIST_SIZE) * PAGE_NUMBER_LIST_SIZE + 1;
        int lastPageNumber = (((currentPageNumber - 1) / PAGE_NUMBER_LIST_SIZE) + 1) * PAGE_NUMBER_LIST_SIZE;
        int endPageNumber = totalPage < lastPageNumber ? totalPage : lastPageNumber;
        List<Integer> pageNumberList = new ArrayList<>();
        for(int i = firstPageNumber ; i < endPageNumber + 1; i++){
            pageNumberList.add(i);
        }
        return pageNumberList;
    }

    public static Integer processPageNumber(Integer entityCount, Integer pageSize, Integer currentPageNumber){
        int totalPage = ((entityCount - 1) / pageSize) + 1;
        int maxPage = totalPage < PAGE_NUMBER_LIMIT ? totalPage : PAGE_NUMBER_LIMIT;

        if(currentPageNumber > maxPage){
            return maxPage;
        }
        else if(currentPageNumber < 1){
            return 1;
        }
        else{
            return currentPageNumber;
        }
    }
}
