/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */


import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.cpd.*;
import net.sourceforge.pmd.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class SpecialRenderer implements Renderer {

	private String separator;
	private boolean trimLeadingWhitespace;

	public static final String DEFAULT_SEPARATOR = "=====================================================================";
	
	public SpecialRenderer() {
		this(false);
	}
	
	public SpecialRenderer(boolean trimLeadingWhitespace) {
		this(DEFAULT_SEPARATOR);
        this.trimLeadingWhitespace = trimLeadingWhitespace;
	}
	
	public SpecialRenderer(String theSeparator) {
		separator = theSeparator;
	}
	
	private void renderOn(StringBuilder rpt, Match match,int beginLine,int endLine,SourceCode sourceCodeSlice,int sourceCodeTokenNumber) throws IOException {
		
          rpt.append("Found a ").append(match.getLineCount()).append(" line (").append(match.getTokenCount()).append(" tokens) duplication in the following files: ").append(PMD.EOL);
          Iterator<Mark> occurrences = match.iterator();
//          int sourceBeginLine;
//      	  int sourceEndLine;
//      	  int upMove,downMove;
//      	  SourceCode sourceCode;
//      	  SourceCode sourceCodeSlice;
//      	  upMove=0;
//      	  downMove=0;
          if(occurrences.hasNext()){
        	Mark sourceMark=occurrences.next();
//        	sourceCode=new SourceCode(new SourceCode.FileCodeLoader(new File(sourceMark.getFilename()),"GBK"));
//        	System.out.println(beginLine+"  "+sourceMark.getBeginLine()+"  "+endLine+"  "+sourceMark.getEndLine());
//        	sourceBeginLine=beginLine<=sourceMark.getBeginLine()?beginLine:sourceMark.getBeginLine();
//        	if(sourceMark.getBeginLine()>beginLine){
//        		upMove=sourceMark.getBeginLine()-beginLine;
//        		sourceBeginLine=beginLine;
//        	}
//        	else {
//        		sourceBeginLine=sourceMark.getBeginLine();
//        	}        	
//        	
//        	sourceEndLine=endLine>=sourceMark.getEndLine()?endLine:sourceMark.getEndLine();
//        	if(sourceMark.getEndLine()<endLine){
//        		downMove=endLine-sourceMark.getEndLine();
//        		sourceEndLine=endLine;
//        	}
//        	else {
//        		sourceEndLine=sourceMark.getEndLine();
//        	}   
//        	System.out.println(sourceBeginLine+"  "+sourceEndLine);
//        	sourceCodeSlice=new SourceCode(new SourceCode.StringCodeLoader(sourceCode.getSlice(beginLine, endLine)));
//        	Tokens sourceCodeTokens=new Tokens();
//        	Language language = LanguageFactory.createLanguage("java");
//        	language.getTokenizer().tokenize(sourceCode, sourceCodeTokens);
    		
    		//System.out.println(sourceCodeTokens.size());
        	double similarity=Math.round(match.getTokenCount()*1.0/(sourceCodeTokenNumber)*10000);
        	similarity=similarity/100;
        	match.setSimilarity(similarity);
            rpt.append("Similarity: "+similarity+"%\n");
        	rpt.append("line ").append(beginLine).append(" to ").append(endLine).append(" of ").append(sourceMark.getFilename()).append(PMD.EOL);
        //	rpt.append("Source:"+sourceMark.getSourceCodeSlice()+"\n");
        	rpt.append("Source Code:").append("\n").append(sourceCodeSlice.getCodeBuffer()).append("\n").append("\n*****************************************\n");
          }
          
          SourceCode codeBase;
          
          while(occurrences.hasNext()) {
              Mark mark = occurrences.next();
         
          	  codeBase=new SourceCode(new SourceCode.FileCodeLoader(new File(mark.getFilename()),"GBK"));

          	  rpt.append("line ").append(mark.getBeginLine()).append(" to ").append(mark.getEndLine()).append(" of ").append(mark.getFilename()).append(PMD.EOL);

          
              rpt.append(PMD.EOL);	// add a line to separate the source from the desc above
              //Set<Mark> markSet=match.getMarkSet();
             // String source = SupportFunction.getMark(markSet.size(),markSet).getSourceCodeSlice();
              if (trimLeadingWhitespace) {
            	  String[] lines = codeBase.getSlice(mark.getBeginLine(), mark.getEndLine()).split("[" + PMD.EOL + "]");
            	  int trimDepth = StringUtil.maxCommonLeadingWhitespaceForAll(lines);
            	  if (trimDepth > 0) {
            		  lines = StringUtil.trimStartOn(lines, trimDepth);
            	  }
            	  for (int i=0; i<lines.length; i++) {
            		  rpt.append(lines[i]).append(PMD.EOL);
            	  }  
            	  rpt.append("\n*****************************************\n");
              }
              else {
            	 // rpt.append("SSSSS:"+mark.getSourceCodeSlice()+"\n");
            	  rpt.append(codeBase.getSlice(mark.getBeginLine(), mark.getEndLine())).append(PMD.EOL).append("\n*****************************************\n");
              }
          }
	}
	
	
    public String render(Iterator<Match> matches,int beginLine,int endLine,SourceCode sourceCode,int sourceCodeTokenNumber) throws IOException {
    	
    	StringBuilder rpt = new StringBuilder(300);
        
        if (matches.hasNext()) {
        	renderOn(rpt, matches.next(),beginLine,endLine,sourceCode,sourceCodeTokenNumber);
        }
        
        Match match;
        while (matches.hasNext()) {
            match = matches.next();
            rpt.append(separator).append(PMD.EOL);
            renderOn(rpt, match,beginLine,endLine,sourceCode,sourceCodeTokenNumber);
          
        }
        return rpt.toString();
    }
    
    private void renderOn(StringBuilder rpt, Match match) {
		
        rpt.append("Found a ").append(match.getLineCount()).append(" line (").append(match.getTokenCount()).append(" tokens) duplication in the following files: ").append(PMD.EOL);
        
        for (Iterator<Mark> occurrences = match.iterator(); occurrences.hasNext();) {
            Mark mark = occurrences.next();
            rpt.append("line ").append(mark.getBeginLine()).append(" to ").append(mark.getEndLine()).append(" of ").append(mark.getFilename()).append(PMD.EOL);

        
            rpt.append(PMD.EOL);	// add a line to separate the source from the desc above
            //Set<Mark> markSet=match.getMarkSet();
           // String source = SupportFunction.getMark(markSet.size(),markSet).getSourceCodeSlice();
            if (trimLeadingWhitespace) {
          	  String[] lines = mark.getSourceCodeSlice().split("[" + PMD.EOL + "]");
          	  int trimDepth = StringUtil.maxCommonLeadingWhitespaceForAll(lines);
          	  if (trimDepth > 0) {
          		  lines = StringUtil.trimStartOn(lines, trimDepth);
          	  }
          	  for (int i=0; i<lines.length; i++) {
          		  rpt.append(lines[i]).append(PMD.EOL);
          	  }  
          	  rpt.append("\n*****************************************\n");
            }
            else {
          	  rpt.append(mark.getSourceCodeSlice()).append(PMD.EOL).append("\n*****************************************\n");
            }
        }
	}
    
  public String render(Iterator<Match> matches) {
  	
  	StringBuilder rpt = new StringBuilder(300);
      
      if (matches.hasNext()) {
      	renderOn(rpt, matches.next());
      }
      
      Match match;
      while (matches.hasNext()) {
          match = matches.next();
          rpt.append(separator).append(PMD.EOL);
          renderOn(rpt, match);
        
      }
      return rpt.toString();
  }
  public double calculateSimilarity(Match match,Tokens sourceCodeTokens){
	double similarity=Math.round(match.getTokenCount()*1.0/(sourceCodeTokens.size()-1)*10000);
  	similarity=similarity/100;
  	return similarity;
  }
}
