package org.elasticsearch.index.analysis;

import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PinyinTokenFilterFactory.class, Index.class, Settings.class, IndexSettingsService.class, PinyinAbbreviationsTokenizer.class })
public class PinyinTokenFilterFactoryTest extends TestCase {

	@Test
	public final void testCreate() throws Exception {
		// given
        String first_letter = "first_letter";
        String padding_char = "padding_char";
        
		Index indexMock = mock(Index.class);
		Version versionMock = mock(Version.class);
		Settings settingsMock = mock(Settings.class);
		TokenStream tokenStreamMock = mock(TokenStream.class);
		PinyinTokenFilter expectedPinyinTokenFilter = mock(PinyinTokenFilter.class);
		IndexSettingsService indexSettingsServiceMock = mock(IndexSettingsService.class);
		
		doReturn(versionMock).when(settingsMock).getAsVersion(IndexMetaData.SETTING_VERSION_CREATED, null);
		doReturn(settingsMock).when(indexSettingsServiceMock).getSettings();
		doReturn(first_letter).when(settingsMock).get("first_letter", "none");
		doReturn(padding_char).when(settingsMock).get("padding_char", "");
		
		whenNew(PinyinTokenFilter.class).withArguments(tokenStreamMock, first_letter, padding_char).thenReturn(expectedPinyinTokenFilter);
		
		PinyinTokenFilterFactory pinyinTokenFilterFactory = new PinyinTokenFilterFactory(indexMock, indexSettingsServiceMock, "", settingsMock);
		
		// when
		TokenStream tokenStream = pinyinTokenFilterFactory.create(tokenStreamMock);
		
		// then
		assertEquals("TokenStream must be equal to " + expectedPinyinTokenFilter, expectedPinyinTokenFilter, tokenStream);
	}

}
