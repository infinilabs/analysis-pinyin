package org.elasticsearch.index.analysis;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.doReturn;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttributeImpl;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Reader.class, PinyinTokenizer.class, Tokenizer.class })
public class PinyinTokenizerTest extends TestCase {
	
	private PinyinTokenizer pinyinTokenizer;
	private OffsetAttributeImpl offsetAttributeMock;
	private String EXPECTED_LETTER = "prefix";
	private String EXPECTED_PADDING = "padding";

	protected void setUp() throws Exception {
		pinyinTokenizer = new PinyinTokenizer(EXPECTED_LETTER, EXPECTED_PADDING);
	}
	
	public void testConstructor() {
		// when
		PinyinTokenizer tokenizer = new PinyinTokenizer(EXPECTED_LETTER, EXPECTED_PADDING);
		
		// then
		String firstLetter = (String) getInternalState(tokenizer, "first_letter");
		String padding = (String) getInternalState(tokenizer, "padding_char");
		
		assertEquals("first_letter must be set by constructor", EXPECTED_LETTER, firstLetter);
		assertEquals("padding_char must be set by constructor", EXPECTED_PADDING, padding);
	}

	public final void testIncrementTokenFirstLetterIsPrefix() throws IOException {
		// given
		Reader inputMock = mock(Reader.class);
		setInternalState(pinyinTokenizer, "input", inputMock);
		doReturn(-1).when(inputMock).read(any(char[].class), anyInt(), anyInt());
		
		// when
		pinyinTokenizer.incrementToken();
		
		// then
		boolean done = (Boolean) getInternalState(pinyinTokenizer, "done");
		CharTermAttribute termAtt = (CharTermAttribute) getInternalState(pinyinTokenizer, "termAtt");
		OffsetAttribute offsetAtt = (OffsetAttribute) getInternalState(pinyinTokenizer, "offsetAtt");
		assertTrue("After incrementToken method call, done field must be true", done);
		assertTrue("After incrementToken method call, termAtt must contains padding chars", new String(termAtt.buffer()).contains(EXPECTED_PADDING));
		assertTrue("After incrementToken method call, offsetAtt must contains padding chars", offsetAtt.toString().contains(EXPECTED_PADDING));
	}

	public final void testEnd() {
		// given
		offsetAttributeMock = mock(OffsetAttributeImpl.class);
		setInternalState(pinyinTokenizer, "offsetAtt", offsetAttributeMock);
		
		// when
		pinyinTokenizer.end();
		
		// then
		verify(offsetAttributeMock).setOffset(0, 0);
	}

	public final void testReset() throws IOException {
		// given
		Reader oldInputPending = (Reader) getInternalState(pinyinTokenizer, "inputPending");
		setInternalState(pinyinTokenizer, "done", true);
		
		// when
		pinyinTokenizer.reset();
		
		// then
		boolean done = (Boolean) getInternalState(pinyinTokenizer, "done");
		assertEquals("After reset method call, input must be set from inputPending.", oldInputPending, getInternalState(pinyinTokenizer, "input"));
		assertFalse("done field must be set to initial value.", done);
	}

}
