package com.aier.speech.recognizer.bean;

public class TopicsBean {
    /**
     * id : 202
     * question_id : 51
     * answer : 信丰
     * is_answer : 2
     */

    private int id;
    private int question_id;
    private String answer;
    private int is_answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getIs_answer() {
        return is_answer;
    }

    public void setIs_answer(int is_answer) {
        this.is_answer = is_answer;
    }
}
