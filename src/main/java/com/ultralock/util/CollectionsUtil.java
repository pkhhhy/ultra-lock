package com.ultralock.util;

import java.util.Collection;
import java.util.Objects;

/**
 * Description:集合工具类
 *
 * @Author: pkh
 * @Date: 02-16
 */
public class CollectionsUtil {

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }
}
