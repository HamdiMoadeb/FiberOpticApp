package com.outsider.networkapplication;

public class Routes {

    private String event;
    private String loss;
    private String distance;

    public Routes() {
    }

    public Routes(String event, String loss, String distance) {
        this.event = event;
        this.loss = loss;
        this.distance = distance;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
