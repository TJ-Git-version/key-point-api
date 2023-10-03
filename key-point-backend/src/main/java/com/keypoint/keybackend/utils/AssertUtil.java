package com.keypoint.keybackend.utils;

import com.keypoint.keybackend.exception.BusinessException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 优雅实现if判断
 *
 * @author key point
 * @date 2023/10/01
 */
public final class AssertUtil {

    /**
     * @param expression
     * @date 2023/10/01
     */
    public static void isTrue(boolean expression, BusinessException businessException) {
        if (expression) {
            throw businessException;
        }
    }

    /**
     * @param object
     * @param businessException
     * @date 2023/10/02
     */
    public static void isNull(Object object, BusinessException businessException) {
        if (object == null) {
            throw businessException;
        }
    }

    /**
     * @param businessException
     * @param value
     * @date 2023/10/02
     */
    public static void anyNull(BusinessException businessException, final Object... value) {
        if (ObjectUtils.anyNull(value)) {
            throw businessException;
        }
    }


    /**
     * @param text
     * @date 2023/10/01
     */
    public static void hasLength(String text, BusinessException businessException) {
        if (!StringUtils.hasLength(text)) {
            throw businessException;
        }
    }

    /**
     * @param text
     * @date 2023/10/01
     */
    public static void hasText(String text, BusinessException businessException) {
        if (!StringUtils.hasText(text)) {
            throw businessException;
        }
    }

    /**
     * @param textToSearch
     * @param substring
     * @date 2023/10/01
     */
    public static void doesNotContain(String textToSearch, String substring, BusinessException businessException) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw businessException;
        }
    }

    /**
     * @param array
     * @date 2023/10/01
     */
    public static void notEmpty(Object[] array, BusinessException businessException) {
        if (ObjectUtils.isEmpty(array)) {
            throw businessException;
        }
    }

    /**
     * @param array
     * @date 2023/10/01
     */
    public static void noNullElements(Object[] array, BusinessException businessException) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw businessException;
                }
            }
        }
    }

    /**
     * @param collection
     * @date 2023/10/01
     */
    public static void notEmpty(Collection<?> collection, BusinessException businessException) {
        if (CollectionUtils.isEmpty(collection)) {
            throw businessException;
        }
    }

    /**
     * @param collection
     * @date 2023/10/01
     */
    public static void noNullElements(Collection<?> collection, BusinessException businessException) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw businessException;
                }
            }
        }
    }

    /**
     * @param map
     * @date 2023/10/01
     */
    public static void notEmpty(Map<?, ?> map, BusinessException businessException) {
        if (CollectionUtils.isEmpty(map)) {
            throw businessException;
        }
    }

    /**
     * @param type
     * @param obj
     * @date 2023/10/01
     */
    public static void isInstanceOf(Class<?> type, Object obj, BusinessException businessException) {
        isNull(type, businessException);
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, businessException.getMessage());
        }
    }

    /**
     * @param superType
     * @param subType
     * @date 2023/10/01
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, BusinessException businessException) {
        isNull(superType, businessException);
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, businessException.getMessage());
        }
    }

    /**
     * @param type
     * @param obj
     * @param msg
     * @date 2023/10/01
     */
    private static void instanceCheckFailed(Class<?> type, Object obj, String msg) {
        String className = (obj != null ? obj.getClass().getName() : "null");
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + ("Object of class [" + className + "] must be an instance of " + type);
        }
        throw new IllegalArgumentException(result);
    }

    /**
     * @param superType
     * @param subType
     * @param msg
     * @date 2023/10/01
     */
    private static void assignableCheckFailed(Class<?> superType, Class<?> subType, String msg) {
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, subType);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + (subType + " is not assignable to " + superType);
        }
        throw new IllegalArgumentException(result);
    }

    /**
     * @param msg
     * @return boolean
     * @date 2023/10/01
     */
    private static boolean endsWithSeparator(String msg) {
        return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
    }

    /**
     * @param msg
     * @param typeName
     * @return
     * @date 2023/10/01
     */
    private static String messageWithTypeName(String msg, Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }

    public static void isAnyBlank(BusinessException businessException, final CharSequence... css) {
        if (org.apache.commons.lang3.StringUtils.isAnyBlank(css)) {
            throw businessException;
        }
    }
}
