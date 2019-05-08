package com.example.nk.myapplication;

import java.util.Comparator;

public class NameComparator implements Comparator<MessAbstract>
{
    @Override
    public int compare(MessAbstract messAbstract, MessAbstract t1) {
        return messAbstract.getMessName().compareTo(t1.getMessName());
    }
}
