package com.chaojilaji.messageborad.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Token {

    /**
     * 获取 token
     */

    private static final String[] chars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final String[] zero = {"0", "00", "000", "0000", "00000", "000000", "0000000", "00000000"};

    private static String shuzi(String a) {
        String ans = a.replaceAll("[^0-9]+", "");
        return ans;
    }

    private static String zimu(String a) {
        String ans = a.toLowerCase().replaceAll("https", "http").replaceAll("[^a-zA-Z]+", "");
        return ans;
    }


    private static Integer round(int a, int b) {
        int c = 1 << 4 * b;
        Integer ans = 0 > a ? a % c + c : a % c;
        return ans;
    }

    private static String to(Integer a, Integer c) {
        String e = "" + Integer.toHexString(round(a + 88, c));
        int f = c - e.length();
        String ans = f > 0 ? zero[f - 1] + e : e;
        return ans;
    }

    private static String rd(int a) {
        String res = "";
        for (int i = 0; i < a; i++) {
            res = res + chars[(int) Math.ceil(Math.random() * 35)];
        }
        return res;
    }

    public static String md5Encode(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    private static String rnd(String a, int b) {
        String ans = rd(b) + md5Encode(a) + rd((int) Math.ceil(Math.random() * 10));
        return ans;
    }

    private static String stringJoin(List<String> a, String join) {
        int n = a.size();
        String res = "";
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                res = res + a.get(i);
            } else {
                res = res + join + a.get(i);
            }
        }
        return res;
    }


    private static String strReverse(String a) {
        List<String> c = new ArrayList<>();
        int n = a.length();
        for (int b = 0, l = n; b < l; b++) {
            c.add(String.valueOf(a.charAt(b)));
        }
        List<String> d = new ArrayList<>();
        int m = c.size();
        for (int i = 0; i < m; i++) {
            d.add(c.get(m - i - 1));
        }

        String ans = stringJoin(d, "");
        return ans;
    }


    public static String encrypt(String a, int b, boolean e) {
        String a1 = shuzi(a);
        String a2 = zimu(a);
        a = a2 + a1;
        List<String> g = new ArrayList<>();
        for (int l = a.length(), f = 0; f < l; f++) {
            String ans = to((int) a.charAt(f), b);
            g.add(ans);
        }
        return rnd(e ? strReverse(stringJoin(g, "")) : stringJoin(g, ""), 4);
    }
}