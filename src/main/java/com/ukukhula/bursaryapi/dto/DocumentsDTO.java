package com.ukukhula.bursaryapi.dto;



import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;



@Data
public class DocumentsDTO {

    int StudentID;
    String ResumeURL;
    String TranscriptURL;
    String IdURL;

    public DocumentsDTO(int studentID, String resumeURL, String transcriptURL, String idURL){

        this.StudentID = studentID;
        this.ResumeURL = resumeURL;
        this.TranscriptURL = transcriptURL;
        this.IdURL = idURL;
    }
    
}
