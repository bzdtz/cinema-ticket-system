package com.saodi.query;

/**
 * 报表区间查询。start/end 均为 yyyy-MM-dd，可空表示不限。
 */
public class ReportQuery {

    private String start;

    private String end;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
