package com.github.osvaldopina.linkbuilder.utils;


public class UrlPathContatenator {

    public static final UrlPathContatenator INSTANCE = new UrlPathContatenator();

    UrlPathContatenator() {

    }

    public String concat(String...parts) {
        StringBuilder tmp = new StringBuilder(parts[0]);
        for(int i=1;i<parts.length; i++) {
            if (!(parts[i-1].endsWith("/") || parts[i].startsWith("/"))) {
                tmp.append("/");
            }
            if (parts[i-1].endsWith("/") && parts[i].startsWith("/")) {
                tmp.append(parts[i].substring(1));
            }
            else {
                tmp.append(parts[i]);
            }
        }
        return tmp.toString();
    }

}
