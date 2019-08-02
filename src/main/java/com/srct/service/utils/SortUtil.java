/**
 * Title: SortUtil.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 22:35
 * @description Project Name: Grote
 * @Package: com.srct.service.utils
 */
package com.srct.service.utils;

import com.srct.service.exception.ServiceException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.comparators.ComparableComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortUtil {

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    public static void sortList(List list, String field, String sortType) {
        sortList(list, new SortEntry(field, sortType));
    }

    public static void sortList(List list, SortEntry... sortEntry) {
        Comparator comparator = new PropertyComparator(sortEntry);
        Collections.sort(list, comparator);
    }

}


@Data
class SortEntry {
    private String field;
    private String sortType;

    public SortEntry(String field, String sortType) {
        super();
        this.field = field;
        this.sortType = sortType;
    }
}


@Slf4j
class PropertyComparator implements Comparator {

    private SortEntry[] sortEntry;
    private Comparator comp = new ComparableComparator();

    public PropertyComparator(SortEntry... sortEntry) {
        this.sortEntry = sortEntry;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }
        int result = 0;
        for (SortEntry entry : sortEntry) {
            try {
                String field = entry.getField();
                Object ret1 = ReflectionUtil.getFieldValue(o1, field);
                Object ret2 = ReflectionUtil.getFieldValue(o2, field);
                if ("DESC".equals(entry.getSortType())) {
                    result = this.comp.compare(ret2, ret1);
                } else {
                    result = this.comp.compare(ret1, ret2);
                }
            } catch (IllegalAccessException e) {
                throw new ServiceException("反射发生异常", e);
            }
            if (result != 0) {
                break;
            }
        }
        return result;
    }
}
