import net.sourceforge.pmd.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.*;
public class PMDTest {
	private static String RULESETS="java-basic,java-braces,java-clone,java-controversial,java-design";
	private static String REPORTLOCATION="D:\\PMDREPORT\\1.txt";
	public int getBugLocation(String filePath, PMDResult[] pmdResult){
		String[] test={"-dir","D:\\code4.java","-f","codeclimate","-R",RULESETS,"-r",REPORTLOCATION};
		if(filePath!=null)test[1]=filePath;
		PMD.run(test);
		String jsonText=FileHelper.readToString(REPORTLOCATION);
		//System.out.println(jsonText);
		String[] jsonText2=jsonText.split("\\n");
		for(int i=0;i<jsonText2.length;++i){
			if(jsonText2[i].isEmpty())continue;
			JSONTokener jsonTokener=new JSONTokener(jsonText2[i]);
			JSONObject jsonObject=(JSONObject)jsonTokener.nextValue();
			//System.out.println(jsonObject.get("check_name"));
			pmdResult[i].checkName=(String) jsonObject.get("check_name");
			//System.out.println(jsonObject.get("description"));
			pmdResult[i].description=(String) jsonObject.get("description");
			JSONObject locationObject=(JSONObject)jsonObject.get("location");
			//System.out.println(locationObject.get("path"));
			pmdResult[i].path=(String) locationObject.get("path");
			JSONObject linesObject=(JSONObject)locationObject.get("lines");
			//System.out.println("begin:"+linesObject.get("begin"));
			//System.out.println("end:"+linesObject.get("end"));
			pmdResult[i].begin=Integer.parseInt(linesObject.get("begin").toString());
			pmdResult[i].end=Integer.parseInt(linesObject.get("end").toString());
			//System.out.println(jsonObject.get("severity"));
			pmdResult[i].severity=(String) jsonObject.get("severity");
		}
		return jsonText2.length;	
	}
	public static void main(String[] args) throws FileNotFoundException{
		PMDResult[] result=new PMDResult[99];
		for(int i=0;i<99;++i)result[i]=new PMDResult();
		int length;
		PMDTest p=new PMDTest();
		length=p.getBugLocation("D:\\test\\code5.java", result);
		for(int i=0;i<length;++i){
			System.out.println(result[i].checkName);
			
			System.out.println(result[i].description);

			System.out.println(result[i].path);


			System.out.println(result[i].begin);
			System.out.println(result[i].end);

			System.out.println(result[i].severity);
		}
	}
}
