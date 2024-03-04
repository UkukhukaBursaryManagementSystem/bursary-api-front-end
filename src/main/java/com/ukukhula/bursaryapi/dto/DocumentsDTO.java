package com.ukukhula.bursaryapi.dto;


import lombok.Data;



@Data
public class DocumentsDTO {

    int ApplicationID;
    String ResumeURL;
    String TranscriptURL;
    String IdURL;

    public DocumentsDTO(int applicationID, String resumeURL, String transcriptURL, String idURL){

        this.ApplicationID = applicationID;
        this.ResumeURL = resumeURL;
        this.TranscriptURL = transcriptURL;
        this.IdURL = idURL;
    }
    
}
