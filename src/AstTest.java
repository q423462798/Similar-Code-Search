import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.SourceLoader;

import net.sourceforge.pmd.cpd.SourceCode;

import org.eclipse.jdt.core.JavaCore;

public class AstTest{
	public CompilationUnit getCompilationUnit(String sourceCodeFile){
		File file = new File(sourceCodeFile);
		
		ASTParser parser = ASTParser.newParser(AST.JLS8); //设置Java语言规范版本
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		Map<String, String> compilerOptions = JavaCore.getOptions();
		compilerOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8); //设置Java语言版本
		compilerOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
		compilerOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(compilerOptions); //设置编译选项
		
		SourceCode sc=new SourceCode(new SourceCode.FileCodeLoader(file, "GBK"));
		StringBuilder sb=sc.getCodeBuffer();
		char[] src=sb.toString().toCharArray();
		parser.setSource(src);
		parser.setResolveBindings(true);
		CompilationUnit cu =  (CompilationUnit)parser.createAST(null);//这个参数是IProgessMonitor,用于GUI的进度显示,我们不需要，填个null. 返回值是AST的根结点
		return cu;
	}
	public  ASTNode getASTParent(String sourceCodeFile,CompilationUnit cu,int line){
		File file = new File(sourceCodeFile);
			
		SourceCode sc=new SourceCode(new SourceCode.FileCodeLoader(file, "GBK"));
		StringBuilder sb=sc.getCodeBuffer();
		List<String> sl=sc.getCode();
		int[] a=new int[sl.size()+1];
		a[0]=0;
		for(int i=0;i<sl.size();++i){
			a[i+1]=sl.get(i).length()+a[i]+2;
		}
		char[] src3=sb.toString().toCharArray();
		
	
		TypeDeclaration t=(TypeDeclaration)cu.types().get(0);
	//	MethodDeclaration m=(MethodDeclaration) t.bodyDeclarations().get(1);
	//	Statement stat=(Statement) m.getBody().statements().get(4);
	//	System.out.println(stat.getNodeType()==ASTNode.WHILE_STATEMENT);
		NodeFinder finder= new NodeFinder(cu,a[line], a[line+1]-a[line]);
//		System.out.println("Line:"+line+"\nCode: "+sl.get(line));
//		System.out.println("Start Position: "+finder.getCoveredNode().getStartPosition());
//		System.out.println("Length:"+finder.getCoveredNode().getLength());
//		System.out.println("Covered Node:"+finder.getCoveredNode());
//		System.out.println("Parenet Node:"+finder.getCoveredNode().getParent());
//		System.out.println("Pareent Begin Line:"+cu.getLineNumber(finder.getCoveredNode().getParent().getStartPosition()));
//		System.out.println("Pareent End Line:"+cu.getLineNumber(finder.getCoveredNode().getParent().getStartPosition()+finder.getCoveredNode().getParent().getLength()));
		//System.out.println(cu1);
//		parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
//		parser.setSource(src2);
//		ASTNode as2=parser.createAST(null);
//		System.out.println(as2);
		
		//System.out.println(as2.subtreeMatch(new ASTMatcher(), as1));
		return finder.getCoveredNode().getParent();
	}
	public static void main(String[] args) throws IOException {
		AstTest t=new AstTest();
		CompilationUnit cu=t.getCompilationUnit("D:\\code4.java");
		ASTNode ast=t.getASTParent("D:\\code4.java",cu, 25);
		System.out.println("Pareent Begin Line:"+cu.getLineNumber(ast.getStartPosition()));
		System.out.println("Pareent End Line:"+cu.getLineNumber(ast.getStartPosition()+ast.getLength()));
	}
}
