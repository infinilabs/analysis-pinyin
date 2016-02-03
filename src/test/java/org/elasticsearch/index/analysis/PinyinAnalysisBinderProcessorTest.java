package org.elasticsearch.index.analysis;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

import org.elasticsearch.index.analysis.AnalysisModule.AnalysisBinderProcessor.AnalyzersBindings;
import org.elasticsearch.index.analysis.AnalysisModule.AnalysisBinderProcessor.TokenFiltersBindings;
import org.elasticsearch.index.analysis.AnalysisModule.AnalysisBinderProcessor.TokenizersBindings;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PinyinAnalysisBinderProcessor.class })
public class PinyinAnalysisBinderProcessorTest extends TestCase {
	
	PinyinAnalysisBinderProcessor pinyinAnalysisBinderProcessor;

	protected void setUp() throws Exception {
		pinyinAnalysisBinderProcessor = new PinyinAnalysisBinderProcessor();
	}

	public final void testProcessAnalyzers() {
		// given
		AnalyzersBindings analyzersBindingsMock = mock(AnalyzersBindings.class);
		
		// when
		pinyinAnalysisBinderProcessor.processAnalyzers(analyzersBindingsMock);
		
		// then
		verify(analyzersBindingsMock).processAnalyzer("pinyin", PinyinAnalyzerProvider.class);
	}

	public final void testProcessTokenizers() {
		// given
		TokenizersBindings tokenizersBindingsMock = mock(TokenizersBindings.class);
		
		// when
		pinyinAnalysisBinderProcessor.processTokenizers(tokenizersBindingsMock);
		
		// then
		verify(tokenizersBindingsMock).processTokenizer("pinyin", PinyinTokenizerFactory.class);
		verify(tokenizersBindingsMock).processTokenizer("pinyin_first_letter", PinyinAbbreviationsTokenizerFactory.class);
	}

	public final void testProcessTokenFilters() {
		// given
		TokenFiltersBindings tokenFiltersBindingsMock = mock(TokenFiltersBindings.class);
		
		// when
		pinyinAnalysisBinderProcessor.processTokenFilters(tokenFiltersBindingsMock);
		
		// then
		verify(tokenFiltersBindingsMock).processTokenFilter("pinyin", PinyinTokenFilterFactory.class);
	}

}
