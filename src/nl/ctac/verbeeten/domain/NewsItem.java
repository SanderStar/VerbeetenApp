package nl.ctac.verbeeten.domain;

import java.util.Date;

/**
 * Domain object for news item.
 */
public class NewsItem {

	public final static String COMMON_TYPE = "common";
    
    public final static String GROUP_TYPE = "group";
    
    /** Unique identification of news item. */
    private Long id;
    
    /** Version of news. */
    private Long version;
    
    /** Description of news. */
    private String description;
    
    /** 
     * Type of news.
     *  
     * News item for all people or news item for a specific group of persons.
     *  
     */
    private String type;
    
    /** Date and time of news. */
    private Date date;
    
    /** Constructor. */
    public NewsItem() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
