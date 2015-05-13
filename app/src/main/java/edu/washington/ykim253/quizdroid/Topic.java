package edu.washington.ykim253.quizdroid;

import java.util.List;

public class Topic {
    private String title;
    private String short_desc;
    private String long_desc;
    private List<Question> questions;

    public Topic() {

    }

    public Topic(String title, String short_desc, String long_desc, List<Question> questions) {
        this.title = title;
        this.short_desc = short_desc;
        this.long_desc = long_desc;
        this.questions = questions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle () {
        return title;
    }

    public void setShort(String shortD) {
        this.short_desc = shortD;
    }

    public String getShort() {
        return short_desc;
    }

    public void setLong(String longD) {
        this.long_desc = longD;
    }

    public String getLong() {
        return long_desc;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }


}
