package com.example.ecake;

public class feedModel {
    //Udate and deleteing data
    String fid,fname,fmessage;

    public feedModel(){}

    public feedModel(String fid, String fname, String fmessage) {
        this.fid = fid;
        this.fname = fname;
        this.fmessage = fmessage;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFmessage() {
        return fmessage;
    }

    public void setFmessage(String fmessage) {
        this.fmessage = fmessage;
    }
}
