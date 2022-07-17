package com.flapps.web.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    public static String substring(String str, int begin) {
        if (str == null) return null;
        int len = str.length();
        if (begin < 0) begin = len + begin;
        if (begin >= len) return "";
        return str.substring(begin);
    }

    public static String substring(String str, int begin, int end) {
        if (str == null) return null;
        int len = str.length();
        if (begin < 0) begin = len + begin;
        if (end < 0) end = len + end;
        if (end > len) end = len;
        if (begin >= end || begin >= len) return "";
        return str.substring(begin, end);
    }

    public static String firstUpperCase(String str) {
        if (str == null) return null;
        if (str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String firstLowerCase(String str) {
        if (str == null) return null;
        if (str.length() == 0) return str;
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static String getterName(String propertyName) {
        return "get" + firstUpperCase(propertyName);
    }

    public static String getterNameWithIs(String propertyName) {
        return "is" + firstUpperCase(propertyName);
    }

    public static String setterName(String propertyName) {
        return "set" + firstUpperCase(propertyName);
    }

    public static String lastToken(String str, char delim) {
        if (str == null) return null;
        int i = str.lastIndexOf(delim);
        if (i < 0) return str;
        if (i == str.length() - 1) return "";
        return str.substring(i + 1);
    }

    public static List<String> tokenizeTrimString(String text, String delimiter) {
        List<String> l = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(text, delimiter);
        while (st.hasMoreElements()) {
            String el = st.nextToken();
            if (el == null) continue;
            el = el.trim();
            if (el.length() > 0) l.add(el);
        }
        return l;
    }

    public static String[] tokenizeTrimStringA(String text, String delimiter) {
        List<String> l = tokenizeTrimString(text, delimiter);
        return (String[]) l.toArray(new String[l.size()]);
    }

    public static String listToString(List<?> items, String delim) {
        StringBuffer sb = new StringBuffer();
        Iterator<?> i = items.iterator();
        while (i.hasNext()) {
            sb.append(i.next());
            if (i.hasNext()) sb.append(delim);
        }
        return sb.toString();
    }

    public static String arrayToString(Object[] items, String delim) {
        if (items == null) return null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < items.length; i++) {
            sb.append(items[i]);
            if (i + 1 < items.length) sb.append(delim);
        }
        return sb.toString();
    }

    public static boolean contains(String[] sarray, String value) {
        if (sarray == null || value == null) return false;
        for (int i = 0; i < sarray.length; i++)
            if (value.equals(sarray[i])) return true;
        return false;
    }

    public static String contains(String[] items1, String[] items2) {
        if (items1 == null || items2 == null) return null;
        for (int i1 = 0; i1 < items1.length; i1++) {
            for (int i2 = 0; i2 < items2.length; i2++) {
                if (items1[i1].equals(items2[i2])) return items1[i1];
            }
        }
        return null;
    }

    public static String trim(String str, char ch) {
        if (str == null) return null;
        int start = 0;
        int stop = str.length() - 1;
        while (start < stop && str.charAt(start) == ch)
            start++;
        while (stop > start && str.charAt(stop) == ch)
            stop--;
        return start < stop ? str.substring(start, stop + 1) : "";
    }

    public static String cutMax(String str, int maxLength, char cutter, int maxCutBefore, String ending) {
        if (ending == null) {
            ending = "...";
        }

        int strLen = str.length();
        int endLen = ending.length();

        if (strLen > maxLength) {
            if (cutter != 0) {
                int cutLen = str.substring(0, maxLength - endLen).lastIndexOf(cutter) + 1;

                if (cutLen > maxLength - maxCutBefore) {
                    return str.substring(0, cutLen) + ending;
                }
            }

            return str.substring(0, maxLength - endLen) + ending;
        } else {
            return str;
        }
    }

    public static List<String[]> matchList(String exp, String content) {
        return matchList(exp, content, Pattern.DOTALL);
    }

    public static List<String[]> matchList(String exp, String content, int flags) {
        List<String[]> res = new ArrayList<String[]>();
        Pattern p = Pattern.compile(exp, flags);

        Matcher m = p.matcher(content);
        while (m.find()) {
            String[] row = new String[m.groupCount()];
            for (int i = 0; i < row.length; i++) {
                row[i] = m.group(i + 1);
            }
            res.add(row);
        }
        return res;
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) return true;
        return str1 != null && str1.equals(str2);
    }

}
