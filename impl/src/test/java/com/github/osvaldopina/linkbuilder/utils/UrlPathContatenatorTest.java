package com.github.osvaldopina.linkbuilder.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UrlPathContatenatorTest {

    private UrlPathContatenator urlPathContatenator = new UrlPathContatenator();

    @Test
    public void concatOnePart() {
        assertEquals("path", urlPathContatenator.concat("path"));
    }

    @Test
    public void concatTwoPartsNoSlash() {
        assertEquals("a/b", urlPathContatenator.concat("a","b"));
    }

    @Test
    public void concatTwoPartsSlashFirst() {
        assertEquals("a/b", urlPathContatenator.concat("a/","b"));
    }

    @Test
    public void concatTwoPartsSlashSecond() {
        assertEquals("a/b", urlPathContatenator.concat("a","/b"));
    }

    @Test
    public void concatTwoPartsSlashFirstAndSecond() {
        assertEquals("a/b", urlPathContatenator.concat("a/","/b"));
    }

}