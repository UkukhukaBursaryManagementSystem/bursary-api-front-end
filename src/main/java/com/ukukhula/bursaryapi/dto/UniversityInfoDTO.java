package com.ukukhula.bursaryapi.dto;

public class UniversityInfoDTO {
    int HeadOfDepartmentID;
    int DepartmentID;
    String UniversityName;
    int UniversityID;

    public UniversityInfoDTO(int headOfDepartmentID,int departmentID, int universityID,String universityName){
        this.HeadOfDepartmentID = headOfDepartmentID;
        this.DepartmentID = departmentID;
        this.UniversityID = universityID;
        this.UniversityName = universityName;
    }
}
