package uk.ac.ebi.biosamples.certservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Checklist {

    private String name;
    private String version;
    @JsonProperty(value = "file")
    private String fileName;

    public Checklist(String name, String version, String fileName) {
        this.name = name;
        this.version = version;
        this.fileName = fileName;
    }

    private Checklist() {

    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @JsonIgnore
    public String getFileName() {
        return fileName;
    }

    @JsonIgnore
    public String getID() {
        return String.format("%s-%s", name, version);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return getID();
    }
}
