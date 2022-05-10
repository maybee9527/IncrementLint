package com.exam.bean.module.login.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GeeInfo implements Serializable {
    String name;
    String age;
    List<JobInfo> mJobInfos;
    Map<String, Edu> eductions;
}
