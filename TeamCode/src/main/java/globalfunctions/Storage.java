package globalfunctions;

import android.graphics.Bitmap;
import android.os.Environment;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

//import globalfunctions.TimeData;

//Class used to store data
public class Storage {
    public int num = 0;
    File outputFile;

    //Saves a bitmap with name name
    public void saveBitmap(Bitmap in, String name){
        File f = new File(outputFile, name + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        in.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        }catch(IOException e1){

        }

    }
    //Converts the input string into JSON
    public String convertToJSON(TimeData in){
        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            objectMapper.writeValue(new File(outputFile.getAbsolutePath() + "/" + in.name + ".json"), in);
//        } catch (IOException e) { }

        String timeDataAsString = " ";
        try {timeDataAsString = objectMapper.writeValueAsString(in); } catch (IOException ignore) {}

        return timeDataAsString;
    }

    //Makes an output file
    public void makeOutputFile(String dirname){
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/FTC_Files/");
        dir.mkdir();
        File dir1= new File(dir.getAbsolutePath()+"/"+dirname+"/");
        dir1.mkdir();
        outputFile = dir1;
    }
    //Saves text to the output file
    public void saveText(String in,String name)  {
        try {
            PrintWriter out = new PrintWriter(outputFile.getAbsolutePath()+"/" + name + ".txt");
            out.println(in);
            out.flush();
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    //Reads text from the output file
    public String readText(String name) {
        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader(outputFile.getAbsolutePath()+"/" + name + ".txt")));
            return scan.nextLine();
        } catch (IOException ignore) {}
        return "";
    }
    //Saves video data
    public void saveVidData(TimeData in){
        try {
            PrintWriter out = new PrintWriter(outputFile.getAbsolutePath() + "/"+ in.name + ".txt");
            if(in.timeStamps == null) {
                for (String s : in.data) {
                    out.println(s);
                }
            }else{
                for (String s : in.getZipped()) {
                    out.println(s);
                }
            }
            out.flush();
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    //Saves a video
    public void saveVideo(ArrayList<Bitmap> vid, ArrayList<String> vidData, String dirname, double vidTime){
        makeOutputFile(dirname);
        saveText(Double.toString(vidTime/vid.size()), "time");
        for (Bitmap curr : vid) {
            saveBitmap(curr, Integer.toString(num));
            num++;
        }
        if(vidData != null){
            TimeData viddata = new TimeData("videoData", vidData);
            saveVidData(viddata);
        }
    }

    //Saves timedata
    public void saveTimeData(TimeData in){
        makeOutputFile("TimeData");
        int index = Integer.parseInt(readText("index"));
        index++;
        //int index = 1;
        saveText(convertToJSON(in), "Data"+Integer.toString(index));
        saveText(Integer.toString(index), "index");
    }

}
