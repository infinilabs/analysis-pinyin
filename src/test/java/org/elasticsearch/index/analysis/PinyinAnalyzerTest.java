package org.elasticsearch.index.analysis;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PinyinAnalyzer.class, TokenStreamComponents.class, PinyinTokenizer.class })
public class PinyinAnalyzerTest extends TestCase {

	PinyinAnalyzer pinyinAnalyzer;
	
	@Test
	public final void testCreateComponentsOnly() throws Exception {
		// given
		TokenStreamComponents tokenStreamComponentsMock = mock(TokenStreamComponents.class);
		whenNew(TokenStreamComponents.class).withAnyArguments().thenReturn(tokenStreamComponentsMock);
		pinyinAnalyzer = new PinyinAnalyzer("only", "paddingChar");
		
		// when
		TokenStreamComponents result = pinyinAnalyzer.createComponents(null);
		
		// then
		assertEquals("CreateComponents creates new TokenStreamComponents therefore result must be equal to " + tokenStreamComponentsMock, tokenStreamComponentsMock, result);		
	}
	
	@Test
	public final void testCreateComponents() throws Exception {
		// given
		String first_letter = "first_letter";
		String paddingChar = "paddingChar";
		PinyinTokenizer pinyinTokenizerMock = mock(PinyinTokenizer.class);
		TokenStreamComponents tokenStreamComponentsMock = mock(TokenStreamComponents.class);
		whenNew(TokenStreamComponents.class).withAnyArguments().thenReturn(tokenStreamComponentsMock);
		whenNew(PinyinTokenizer.class).withArguments(first_letter, paddingChar).thenReturn(pinyinTokenizerMock);
		pinyinAnalyzer = new PinyinAnalyzer(first_letter, paddingChar);
		
		// when
		TokenStreamComponents result = pinyinAnalyzer.createComponents(null);
		
		// then
		assertEquals("CreateComponents creates new TokenStreamComponents therefore result must be equal to " + tokenStreamComponentsMock, tokenStreamComponentsMock, result);		
	}
}
