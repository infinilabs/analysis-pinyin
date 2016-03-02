package org.elasticsearch.analysis;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.doReturn;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.analysis.PreBuiltAnalyzerProviderFactory;
import org.elasticsearch.index.analysis.PreBuiltTokenFilterFactoryFactory;
import org.elasticsearch.index.analysis.PreBuiltTokenizerFactoryFactory;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PinyinIndicesAnalysis.class, Settings.class, IndicesAnalysisService.class, Environment.class})
public class PinyinIndicesAnalysisTest extends TestCase {

	@SuppressWarnings("unused")
	@Test
	public final void testConstructor() {
		// given
		Settings settingsMock = Settings.EMPTY;
		IndicesAnalysisService indicesAnalysisServiceMock = mock(IndicesAnalysisService.class);
		Environment environmentMock = mock(Environment.class);
		
		Map<String, PreBuiltAnalyzerProviderFactory> analyzerProviderFactories = new HashMap<String, PreBuiltAnalyzerProviderFactory>();
		Map<String, PreBuiltTokenizerFactoryFactory> tokenizerFactories = new HashMap<String, PreBuiltTokenizerFactoryFactory>();
		Map<String, PreBuiltTokenFilterFactoryFactory> tokenFilterFactories = new HashMap<String, PreBuiltTokenFilterFactoryFactory>();
		
		doReturn(analyzerProviderFactories).when(indicesAnalysisServiceMock).analyzerProviderFactories();
		doReturn(tokenizerFactories).when(indicesAnalysisServiceMock).tokenizerFactories();
		doReturn(tokenFilterFactories).when(indicesAnalysisServiceMock).tokenFilterFactories();
		
		// when
		PinyinIndicesAnalysis pinyinIndicesAnalysis = new PinyinIndicesAnalysis(settingsMock, indicesAnalysisServiceMock, environmentMock);
		
		// then
		verify(indicesAnalysisServiceMock, times(2)).analyzerProviderFactories();
		verify(indicesAnalysisServiceMock, times(2)).tokenizerFactories();
		verify(indicesAnalysisServiceMock, times(2)).tokenFilterFactories();
		assertTrue("", analyzerProviderFactories.containsKey("pinyin_first_letter") && analyzerProviderFactories.containsKey("pinyin"));
		assertTrue("", tokenizerFactories.containsKey("pinyin_first_letter") && tokenizerFactories.containsKey("pinyin"));
		assertTrue("", tokenFilterFactories.containsKey("pinyin_first_letter") && tokenFilterFactories.containsKey("pinyin"));
	}

}
