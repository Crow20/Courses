package com.sike.courses;

import com.sike.courses.mvp.model.Course;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by agritsenko on 18.09.2017.
 */

public final class Singleton {

    private static final Singleton ourInstance = new Singleton();
    public HashMap<Object, ArrayList<Course>> list;

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

}
