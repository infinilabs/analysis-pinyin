package org.elasticsearch.index.analysis;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttributeImpl;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttributeImpl;
import org.apache.lucene.analysis.util.CharacterUtils;
import org.apache.lucene.util.AttributeFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PinyinAbbreviationsTokenizer.class, PinyinHelper.class, HanyuPinyinOutputFormat.class, Reader.class, CharacterUtils.CharacterBuffer.class, Character.class, CharTermAttributeImpl.class })
public class PinyinAbbreviationsTokenizerTest extends TestCase {
	
	private PinyinAbbreviationsTokenizer pinyinAbbreviationsTokenizer;
	private AttributeFactory attributeFactoryMock;
	private OffsetAttributeImpl offsetAttributeMock;
	private CharTermAttributeImpl charTermAttributeImplMock;

	@Before
	public void setUp() throws Exception {
		attributeFactoryMock = mock(AttributeFactory.class);
		offsetAttributeMock = mock(OffsetAttributeImpl.class);
		charTermAttributeImplMock = mock(CharTermAttributeImpl.class);
		
		doReturn(offsetAttributeMock).when(attributeFactoryMock).createAttributeInstance(OffsetAttribute.class);
		doReturn(charTermAttributeImplMock).when(attributeFactoryMock).createAttributeInstance(CharTermAttribute.class);
		
		pinyinAbbreviationsTokenizer = new PinyinAbbreviationsTokenizer(attributeFactoryMock);
	}
	
	@Test
	public final void testIncrementTokenEmptyBuffer() throws IOException {
		// given
		Reader readerMock = mock(Reader.class);
		CharacterUtils.CharacterBuffer bufferMock = mock(CharacterUtils.CharacterBuffer.class);
		doReturn(0).when(bufferMock).getLength();
		char[] chars = new char[2];
		setInternalState(bufferMock, "buffer", chars);
		setInternalState(pinyinAbbreviationsTokenizer, "input", readerMock);
		setInternalState(pinyinAbbreviationsTokenizer, "ioBuffer", bufferMock);
		doReturn(2).when(readerMock).read(any(char[].class), anyInt(), anyInt());
		
		// when
		boolean result = pinyinAbbreviationsTokenizer.incrementToken();
		
		// then
		assertFalse("Buffer is empty therefore increment token method must return false.", result);
	}
	
	@Test
	public final void testIncrementToken() throws IOException {
		// given
		mockStatic(Character.class);
		when(Character.isLetterOrDigit(0)).thenReturn(true);
		when(Character.toChars(0)).thenReturn(new char[1]);
		when(Character.toChars(0, null, 0)).thenReturn(255);
		
		Reader readerMock = mock(Reader.class);
		CharacterUtils.CharacterBuffer bufferMock = mock(CharacterUtils.CharacterBuffer.class);
		doReturn(2).when(bufferMock).getLength();
		char[] chars = new char[2];
		CharacterUtils characterUtilsMock = mock(CharacterUtils.class);
		setInternalState(pinyinAbbreviationsTokenizer, "charUtils", characterUtilsMock);
		setInternalState(bufferMock, "buffer", chars);
		setInternalState(pinyinAbbreviationsTokenizer, "input", readerMock);
		setInternalState(pinyinAbbreviationsTokenizer, "ioBuffer", bufferMock);
		doReturn(2).when(readerMock).read(any(char[].class), anyInt(), anyInt());
		
		// when
		boolean result = pinyinAbbreviationsTokenizer.incrementToken();
		
		// then
		assertTrue("Buffer is not empty therefore increment token method must return false.", result);
	}

	@Test
	public final void testEnd() throws IOException {
		// when
		pinyinAbbreviationsTokenizer.end();
		
		// then
		verify(offsetAttributeMock).setOffset(0, 0);
	}

	@Test
	public final void testReset() throws IOException {
		// given
		setInternalState(pinyinAbbreviationsTokenizer, "offset", 1);
		setInternalState(pinyinAbbreviationsTokenizer, "dataLen", 1);
		setInternalState(pinyinAbbreviationsTokenizer, "bufferIndex", 1);
		setInternalState(pinyinAbbreviationsTokenizer, "finalOffset", 1);
		
		// when
		pinyinAbbreviationsTokenizer.reset();
		
		// then
		assertEquals("Reset method must set offset to 0", 0, getInternalState(pinyinAbbreviationsTokenizer, "offset"));
		assertEquals("Reset method must set dataLen to 0", 0, getInternalState(pinyinAbbreviationsTokenizer, "dataLen"));
		assertEquals("Reset method must set bufferIndex to 0", 0, getInternalState(pinyinAbbreviationsTokenizer, "bufferIndex"));
		assertEquals("Reset method must set finalOffset to 0", 0, getInternalState(pinyinAbbreviationsTokenizer, "finalOffset"));
	}

	@Test
	public final void testIsTokenChar() {
		// given
		char character = 'a';
		
		// when
		boolean result = pinyinAbbreviationsTokenizer.isTokenChar(character);
		
		// then
		assertTrue(character + " is a char", result);
	}
	
	@Test
	public final void testIsTokenCharNot() {
		// when
		boolean result = pinyinAbbreviationsTokenizer.isTokenChar(0);
		
		// then
		assertFalse(0 + " is not a char", result);
	}

	@Test
	public final void testNormalize() throws Exception {
		// given
		char character = 'a';
		String[] test = { String.valueOf(character) };
		HanyuPinyinOutputFormat formatMock = mock(HanyuPinyinOutputFormat.class);
		setInternalState(pinyinAbbreviationsTokenizer, "format", formatMock);
		
		mockStatic(PinyinHelper.class);
		when(PinyinHelper.toHanyuPinyinStringArray(character, formatMock)).thenReturn(test);
		
		// when
		int result = pinyinAbbreviationsTokenizer.normalize(character);
		
		// then
		assertEquals("", (int)character, result);
	}

	@Test
	public final void testNormalizeEmptyHanyuPinyinStringArray() throws Exception {
		// given
		char character = 'a';
		HanyuPinyinOutputFormat formatMock = mock(HanyuPinyinOutputFormat.class);
		setInternalState(pinyinAbbreviationsTokenizer, "format", formatMock);
		
		mockStatic(PinyinHelper.class);
		when(PinyinHelper.toHanyuPinyinStringArray(character, formatMock)).thenReturn(null);
		
		// when
		int result = pinyinAbbreviationsTokenizer.normalize(character);
		
		// then
		assertEquals("", (int)character, result);
	}

}
