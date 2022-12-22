package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;

public class SceneFile {

    private static final boolean IS_DIST_VERSION = false;
    private ArrayList<ObjectProperties> objList;

    private static final String START_OBJ = "New Obj Prop";
    private static final String END_OBJ = "End Obj Prop";

    public SceneFile() {
        this.objList = new ArrayList<ObjectProperties>();
    }

    public void saveSceneToFile(String filepath) {  

        File file = new File(filepath + ".scn");
        PrintWriter printWriter = null;
        
        try { 
            printWriter = new PrintWriter(
                new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(file), "UTF-8")), true);
            for (ObjectProperties op : objList) {
                printWriter.write(START_OBJ + "=" + "start new obj" + "\n");
    
                printWriter.write(ObjectProperties.ID_STRING + "=" + op.getId() + "\n");
    
                printWriter.write(ObjectProperties.NAME_STRING + "=" + op.getName() + "\n");
    
                printWriter.write(ObjectProperties.TYPE_STRING + "=" + op.getType() + "\n");
    
                printWriter.write(ObjectProperties.POSITION_X_STRING + "=" + op.getPosition().getX() + "\n");
                printWriter.write(ObjectProperties.POSITION_Y_STRING + "=" + op.getPosition().getY() + "\n");
                printWriter.write(ObjectProperties.POSITION_Z_STRING + "=" + op.getPosition().getZ() + "\n");
    
                printWriter.write(ObjectProperties.ROTATION_X_STRING + "=" + op.getRotation().getX() + "\n");
                printWriter.write(ObjectProperties.ROTATION_Y_STRING + "=" + op.getRotation().getY() + "\n");
                printWriter.write(ObjectProperties.ROTATION_Z_STRING + "=" + op.getRotation().getZ() + "\n");
    
                printWriter.write(ObjectProperties.TRANSPARENCY_STRING + "=" + op.getTransparency() + "\n");
                printWriter.write(ObjectProperties.ROUGHNESS_STRING + "=" + op.getRoughness() + "\n");
                printWriter.write(ObjectProperties.EMISSION_STRING + "=" + op.getEmission() + "\n");
                printWriter.write(ObjectProperties.REFRACTIVE_INDEX_STRING + "=" + op.getRefractiveIndex() + "\n");
    
                printWriter.write(ObjectProperties.COLOR_R_STRING + "=" + op.getColor().getR() + "\n");
                printWriter.write(ObjectProperties.COLOR_G_STRING + "=" + op.getColor().getG() + "\n");
                printWriter.write(ObjectProperties.COLOR_B_STRING + "=" + op.getColor().getB() + "\n");
    
                printWriter.write(ObjectProperties.RADIUS_STRING + "=" + op.getRadius() + "\n");
    
                printWriter.write(END_OBJ + "=" + "end new obj" + "\n");
            }
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if(printWriter != null)
                printWriter.close();
        }
    }

    public static SceneFile getSceneFileFromFile(String filepath) throws IOException {
        SceneFile sf = new SceneFile();

        LinkedList<String[]> lstRows = null;
        BufferedReader buffRead = null;
        try {
            buffRead = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(filepath), "UTF-8"));

            lstRows = new LinkedList<String[]>();
            String s = null;

            while ((s = buffRead.readLine()) != null)
                if (!s.isEmpty())
                    lstRows.add(s.trim().split("="));
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        finally {
            if (buffRead != null)
                buffRead.close();
        }

        ObjectProperties op = new ObjectProperties();
        for (String[] row : lstRows) {
            if(row[0].equals(START_OBJ)){
                op = new ObjectProperties();  
            }
            if(row[0].equals(ObjectProperties.ID_STRING)){
                op.setId(Integer.parseInt(row[1]));
            }
            if(row[0].equals(ObjectProperties.NAME_STRING)){
                op.setName(row[1]);
            }
            if(row[0].equals(ObjectProperties.TYPE_STRING)){
                op.setType(row[1]);
            }

            if(row[0].equals(ObjectProperties.POSITION_X_STRING)){
                op.getPosition().setX(Double.parseDouble(row[1]));
            }
            if(row[0].equals(ObjectProperties.POSITION_Y_STRING)){
                op.getPosition().setY(Double.parseDouble(row[1]));
            }
            if(row[0].equals(ObjectProperties.POSITION_Z_STRING)){
                op.getPosition().setZ(Double.parseDouble(row[1]));
            }

            if(row[0].equals(ObjectProperties.ROTATION_X_STRING)){
                op.getRotation().setX(Double.parseDouble(row[1]));
            }
            if(row[0].equals(ObjectProperties.ROTATION_Y_STRING)){
                op.getRotation().setY(Double.parseDouble(row[1]));
            }
            if(row[0].equals(ObjectProperties.ROTATION_Z_STRING)){
                op.getRotation().setZ(Double.parseDouble(row[1]));
            }

            if(row[0].equals(ObjectProperties.TRANSPARENCY_STRING)){
                op.setTransparency(Float.parseFloat(row[1]));
            }
            if(row[0].equals(ObjectProperties.EMISSION_STRING)){
                op.setEmission(Float.parseFloat(row[1]));
            }
            if(row[0].equals(ObjectProperties.ROUGHNESS_STRING)){
                op.setRoughness(Float.parseFloat(row[1]));
            }
            if(row[0].equals(ObjectProperties.REFRACTIVE_INDEX_STRING)){
                op.setRefractiveIndex(Float.parseFloat(row[1]));
            }

            if(row[0].equals(ObjectProperties.COLOR_R_STRING)){
                op.getColor().setR(Float.parseFloat(row[1]));
            }
            if(row[0].equals(ObjectProperties.COLOR_G_STRING)){
                op.getColor().setG(Float.parseFloat(row[1]));
            }
            if(row[0].equals(ObjectProperties.COLOR_B_STRING)){
                op.getColor().setB(Float.parseFloat(row[1]));
            }

            if(row[0].equals(ObjectProperties.RADIUS_STRING)){
                op.setRadius(Double.parseDouble(row[1]));
            }

            if(row[0].equals(END_OBJ)){
                sf.add(op); 
            }
        }

        return sf;
    }

    public void add(ObjectProperties op){
        this.objList.add(op);
    }

    public ArrayList<ObjectProperties> getObjList() {
        return this.objList;
    }

    public static String getDefaultSceneFileFullPath() {
        String configFile = null;
        String relPath = "\\resources\\DefaultScene.scn";
        if (System.getProperty("os.name").startsWith("Linux")) {
            relPath = "/resources/DefaultScene.scn";
        }
        if (IS_DIST_VERSION) {
            try {
                configFile = getHomeFolderForDistVersion() + relPath;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                configFile = getHomeFolderForDevVersion() + relPath;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return configFile;
    }

    private static String getHomeFolderForDistVersion() throws URISyntaxException {
        String homeDir = null;
        String jarPath = SceneFile.class.getResource("SceneFile.class").toURI().toString();
        int indexOfExclamationMark = jarPath.indexOf("!");
        String prefix = "jar:file:/"; // this is the prefix for Windows OS platform
        if (System.getProperty("os.name").startsWith("Linux")) {
            prefix = "jar:file:";
        }
        homeDir = jarPath.substring(prefix.length(), indexOfExclamationMark);
        int lastIndexOfSlash = homeDir.lastIndexOf("/");
        homeDir = homeDir.substring(0, lastIndexOfSlash);
        return homeDir;
    }

    private static String getHomeFolderForDevVersion() throws URISyntaxException {
        File configFile = null;
        File byteCodeFileOfThisClass = new File(SceneFile.class.getResource("SceneFile.class").toURI());
        //System.out.println("byteCodeFileOfThisClass: " + byteCodeFileOfThisClass);
        configFile = byteCodeFileOfThisClass.getParentFile().getParentFile().getParentFile().getParentFile();
        //System.out.println("configFile: " + configFile.toString());
        return configFile.toString();
    }

}
