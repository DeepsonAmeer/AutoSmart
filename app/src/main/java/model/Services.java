package model;

public class Services {
    public String Name;
    public String Description;
    public String Status;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getEngineer() {
        return Engineer;
    }

    public void setEngineer(String engineer) {
        Engineer = engineer;
    }

    public String DateCreated;
    public String Engineer;

}
