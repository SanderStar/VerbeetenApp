package nl.ctac.verbeeten.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Domain object for notes.
 */
public class Note implements Serializable {

    /** Unique serial version UID. */
	private static final long serialVersionUID = 4139120950532654443L;

	/** Unique identification of note. */
    private Long id;
    
    /** Version of note. */
    private Long version;
    
    /** Description of note. */
    private String description;
    
    /** Date when note is made. */
    private Date date;
    
    /** Constructor. */
    public Note() {
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

}
