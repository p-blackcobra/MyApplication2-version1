package com.example.nk.myapplication;

import java.util.Comparator;

public class RateComparator implements Comparator<MessAbstract>
{
    @Override
    public int compare(MessAbstract messAbstract, MessAbstract t1) {
        return messAbstract.getMessRate().compareTo(t1.getMessRate());
    }
}
