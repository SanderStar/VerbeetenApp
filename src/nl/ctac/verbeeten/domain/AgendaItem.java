package nl.ctac.verbeeten.domain;


import java.util.Date;

public class AgendaItem {

    /** Unique identification of agenda item. */
    private Long id;
    
    /** Version of news. */
    private Long version;
    
    /** Description of news. */
    private String description;
    
    /** Date and time of agenda item. */
    private Date date;
    
    /** Full name of contact for appointment. */
    private String person;
    
    /** Full description of location for appointment. */
    private String location;
    
    /** Constructor. */
    public AgendaItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
