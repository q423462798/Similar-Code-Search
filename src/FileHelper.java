import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class FileHelper {
	public static boolean fileWriteHelper(String filePath,String content) throws IOException{
		FileWriter fw = new FileWriter(filePath);          
	  
    	//for (String line:content) {     

        	fw.write(content);     

    	//}    
    	fw.close();
    	return true;
	}
	public static String sourceLoader(File file) throws IOException
	{
		InputStream in = new FileInputStream(file);
		//StringBuilder s=new StringBuilder();
		byte b[] = new byte[4096];   
        int len = 0;   
        int temp=0;          //所有读取的内容都使用temp接收   
        while((temp=in.read())!=-1){    //当没有读取完时，继续读取   
            b[len]=(byte)temp;   
            len++;   
        }   
        in.close();   
        return new String(b,0,len);   
	}
	public static String readToString(String fileName) {  
        String encoding = "UTF-8";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
    }  
}
