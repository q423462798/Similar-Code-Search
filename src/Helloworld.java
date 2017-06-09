import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMD;

//import com.beust.jcommander.JCommander;
//import com.beust.jcommander.ParameterException;

import net.sourceforge.pmd.cpd.*;

public class Helloworld {
	public static void main(String[] args) throws IOException{
		String fileWithBugs="D:\\code.java";
		String fileWithBugs2="D:\\code2.java";
		String codeBase="C:\\Users\\Jeffrey\\Desktop\\apache-tomcat-8.0.29-src\\java";
		String codeSlice;
		CPDConfiguration arguments = new CPDConfiguration();
		Iterator<Match> matchset;
		File file1 = new File(fileWithBugs);
		File codeBaseDir = new File(codeBase);
		arguments.setMinimumTileSize(Integer.parseInt("20"));
        arguments.setEncoding("GBK");
        arguments.setIgnoreIdentifiers(true);
//        arguments.setIgnoreLiterals(true);
//        arguments.setIgnoreAnnotations(true);
//        arguments.setIgnoreUsings(true);
        
		arguments.setLanguage(CPDConfiguration.getLanguageFromString("java"));
        arguments.setRendererName("text");
        arguments.setRenderer(CPDConfiguration.getRendererFromString(arguments.getRendererName(),"GBK"));
		CPDConfiguration.setSystemProperties(arguments);
		SourceCode file1Source=arguments.sourceCodeFor(file1); 
		Tokens file1Tokens=new Tokens();
		arguments.tokenizer().tokenize(file1Source, file1Tokens);
		System.out.println(file1Tokens.size());
		CPD cpd = new CPD(arguments);
		cpd.add(file1);
		//cpd.addAllInDirectory(dirPath2);
		cpd.addRecursively(codeBaseDir);
		System.out.println("Start");
		 try {
	            // Add files
//	            if (null != arguments.getFiles() && !arguments.getFiles().isEmpty()) {
//	                addSourcesFilesToCPD(arguments.getFiles(), cpd, !arguments.isNonRecursive());
//	            }
	            cpd.go();
	            matchset=cpd.getMatches();
	            while(matchset.hasNext()){
	            	StringBuilder rpt=new StringBuilder();
	            	Match m=matchset.next();
	                
	            	boolean flag=false;
	            	rpt.append("Found a ").append(m.getLineCount()).append(" line (").append(m.getTokenCount()).append(" tokens) duplication in the following files: ").append(PMD.EOL);
	            	for (Iterator<Mark> occurrences = m.iterator(); occurrences.hasNext();) {
	            		Mark mark = occurrences.next();
	           
	            		rpt.append("Starting at line ").append(mark.getBeginLine()).append(" of ").append(mark.getFilename()).append(PMD.EOL);
	            		if(mark.getFilename()==file1.getAbsolutePath()){
	            			flag=true;
	            		}
	            	}
	            	if(flag){
	                    rpt.append(PMD.EOL);	// add a line to separate the source from the desc above
	                    Set<Mark> markSet=m.getMarkSet();
	                    String source = SupportFunction.getMark(markSet.size(),markSet).getSourceCodeSlice();
	                    String source1=SupportFunction.getMark(0,markSet).getSourceCodeSlice();
//	                    if (trimLeadingWhitespace) {
//	                        String[] lines = source.split("[" + PMD.EOL + "]");
//	                  	  int trimDepth = StringUtil.maxCommonLeadingWhitespaceForAll(lines);
//	                  	  if (trimDepth > 0) {
//	                  		  lines = StringUtil.trimStartOn(lines, trimDepth);
//	                  	  }
//	                  	  for (int i=0; i<lines.length; i++) {
//	                  		  rpt.append(lines[i]).append(PMD.EOL);
//	                  	  }  
//	                  	  return;
//	                    }
	                    
	                  rpt.append(source).append("\n*****************************************\n").append(source1).append(PMD.EOL);
	            		System.out.println((rpt));
	            	}
	            }
	           // System.out.println(matchset.next());
	           // System.out.println(arguments.getRenderer().render(cpd.getMatches()));
	           // System.out.println(dirPath1.getName());
//	            if (cpd.getMatches().hasNext()) {
//	                if (arguments.isFailOnViolation()) {
//	                    setStatusCodeOrExit(DUPLICATE_CODE_FOUND);
//	                } else {
//	                    setStatusCodeOrExit(0);
//	                }
//	            } else {
//	                setStatusCodeOrExit(0);
//	            }
	            System.out.println("Finish");
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	        }
		 finally{
			 
		 }
	}
	
}
