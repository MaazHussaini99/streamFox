package databasepackage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * 
 */
import java.util.List;
public class StreamingService {


    private Netflix uNetflix;
    private Disney uDisney;
    private Youtube uYoutube;



    public Netflix getuNetflix() {
        return uNetflix;
    }

    public void setuNetflix(Netflix uNetflix) {
        this.uNetflix = uNetflix;
    }

    public Disney getuDisney() {
        return uDisney;
    }

    public void setuDisney(Disney uDisney) {
        this.uDisney = uDisney;
    }

    public Youtube getuYoutube() {
        return uYoutube;
    }

    public void setuYoutube(Youtube uYoutube) {
        this.uYoutube = uYoutube;
    }




    public List<Object> getStreaming_Service() {
        return Streaming_Service;
    }

    public void setStreaming_Service(List<Object> streaming_Service) {
        Streaming_Service = Streaming_Service;
    }

    private List<Object> Streaming_Service;


    public StreamingService(List<Object> streaming_Service) {
        this.Streaming_Service = streaming_Service;
        this.uNetflix=uNetflix;
        this.uDisney=uDisney;
        this.uYoutube=uYoutube;
    }








}