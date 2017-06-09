import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import net.sourceforge.pmd.cpd.Language;
import net.sourceforge.pmd.cpd.LanguageFactory;
import net.sourceforge.pmd.cpd.Mark;
import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.Tokens;

public class SupportFunction {
	public static Mark getMark(int index,Set<Mark> markSet) {
        Mark result = null;
        int i = 0;
        for (Iterator<Mark> it = markSet.iterator(); it.hasNext() && i < index + 1; ){            
            result = it.next();
            i++;
        }
        return result;
    }
	public static int tokensCount(SourceCode sourceCode) throws IOException{
		Tokens sourceCodeTokens=new Tokens();
    	Language language = LanguageFactory.createLanguage("java");
    	language.getTokenizer().tokenize(sourceCode, sourceCodeTokens);
    	return sourceCodeTokens.size()-1;
	}
}
