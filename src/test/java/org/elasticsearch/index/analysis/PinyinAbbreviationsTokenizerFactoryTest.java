package org.elasticsearch.index.analysis;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PinyinAbbreviationsTokenizerFactory.class, Index.class, Settings.class, IndexSettingsService.class, PinyinAbbreviationsTokenizer.class })
public class PinyinAbbreviationsTokenizerFactoryTest extends TestCase {

	public final void testCreate() throws Exception {
		// given
		Index indexMock = mock(Index.class);
		Version versionMock = mock(Version.class);
		Settings settingsMock = mock(Settings.class);
		PinyinAbbreviationsTokenizer tokenizerMock = mock(PinyinAbbreviationsTokenizer.class);
		IndexSettingsService indexSettingsServiceMock = mock(IndexSettingsService.class);
		doReturn(versionMock).when(settingsMock).getAsVersion(IndexMetaData.SETTING_VERSION_CREATED, null);
		doReturn(settingsMock).when(indexSettingsServiceMock).getSettings();
		whenNew(PinyinAbbreviationsTokenizer.class).withNoArguments().thenReturn(tokenizerMock);
		PinyinAbbreviationsTokenizerFactory pinyinAbbreviationsTokenizerFactory = new PinyinAbbreviationsTokenizerFactory(indexMock, indexSettingsServiceMock, "", settingsMock);
		
		// when
		Tokenizer tokenizer = pinyinAbbreviationsTokenizerFactory.create();
		
		// then
		assertEquals("Tokenizer must be equal to " + tokenizerMock, tokenizerMock, tokenizer);
	}

}
