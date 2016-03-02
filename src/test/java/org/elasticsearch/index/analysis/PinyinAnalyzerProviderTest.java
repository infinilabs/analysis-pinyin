package org.elasticsearch.index.analysis;

import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PinyinAnalyzerProvider.class, PinyinAnalyzer.class, Settings.class, Loggers.class, Analysis.class })
public class PinyinAnalyzerProviderTest extends TestCase {

	@Test
	public final void testGet() throws Exception {
		// given
		mockStatic(Analysis.class);
		
		Index indexMock = mock(Index.class);
		IndexSettingsService indexSettingsServiceMock = mock(IndexSettingsService.class);
		PinyinAnalyzer pinyinAnalyzerMock = mock(PinyinAnalyzer.class);
		Settings settingsMock = Settings.EMPTY;
		doReturn(settingsMock).when(indexSettingsServiceMock).getSettings();
		whenNew(PinyinAnalyzer.class).withAnyArguments().thenReturn(pinyinAnalyzerMock);
		
		PinyinAnalyzerProvider pinyinAnalyzerProvider = new PinyinAnalyzerProvider(indexMock, indexSettingsServiceMock, null, null, settingsMock);
		
		// when
		PinyinAnalyzer result = pinyinAnalyzerProvider.get();
		
		// then
		assertEquals("Result must be same with " + pinyinAnalyzerMock, pinyinAnalyzerMock, result);
	}

}
