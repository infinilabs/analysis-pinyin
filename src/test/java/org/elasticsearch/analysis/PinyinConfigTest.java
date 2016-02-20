package org.elasticsearch.analysis;

import static org.mockito.internal.util.reflection.Whitebox.getInternalState;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class PinyinConfigTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testEnums() {
		assertEquals("Mode.onlyFirstLetter must be constructed with 1", 1, getInternalState(PinyinConfig.Mode.onlyFirstLetter, "value"));
		assertEquals("Mode.fullPinyin must be constructed with 2", 2, getInternalState(PinyinConfig.Mode.fullPinyin, "value"));
		assertEquals("Mode.fullPinyinWithSpace must be constructed with 3", 3, getInternalState(PinyinConfig.Mode.fullPinyinWithSpace, "value"));
		assertEquals("Mode.supportPolyphony must be constructed with 4", 4, getInternalState(PinyinConfig.Mode.supportPolyphony, "value"));
	}

}
