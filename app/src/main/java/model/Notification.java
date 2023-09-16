package model;

public class Notification {
    public String Make;

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getEngineer() {
        return Engineer;
    }

    public void setEngineer(String engineer) {
        Engineer = engineer;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String Engineer;
    public String Status;

    public Notification(){}
    public Notification(String Make,String Engineer,String Status) {
        this.Make = Make;
        this.Engineer = Engineer;
        this.Status = Status;
    }
}
