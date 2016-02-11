/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.index.analysis;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttributeImpl;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttributeImpl;
import org.apache.lucene.util.AttributeFactory;
import org.apache.lucene.util.AttributeSource.State;
import org.junit.Test;

import junit.framework.TestCase;

public class PinyinTokenFilterTest extends TestCase {

	@Test
	public void testIncrementTokenForNotMatchedOperation() throws IOException {
		// given
		List<String> piyaniConvertedList = new ArrayList<String>();

		// when
		convertCharSetAndApplyOperation("pinyin", "non-matched-operation", "", piyaniConvertedList);
	
		// then
		assertTrue("piyaniConvertedList element must be empty as the operation is not matched",
				piyaniConvertedList.get(0).isEmpty());
	}

	@Test
	public void testIncrementTokenForMatchedOperationOnly() throws IOException {
		// given
		List<String> piyaniConvertedList = new ArrayList<String>();

		// when
		convertCharSetAndApplyOperation("pinyin", "only", "", piyaniConvertedList);

		// then
		assertEquals("piyaniConvertedList element must be equal to pinyin for the given operation", "pinyin",
				piyaniConvertedList.get(0));
	}

	@Test
	public void testIncrementTokenForMatchedOperationNone() throws IOException {
		// given
		List<String> piyaniConvertedList = new ArrayList<String>();

		// when
		convertCharSetAndApplyOperation("刘德华", "none", "", piyaniConvertedList);

		// then
		assertEquals("piyaniConvertedList first element must be equal to liu for the given operation", "liu",
				piyaniConvertedList.get(0));
		assertEquals("piyaniConvertedList second element must be equal to de for the given operation", "de",
				piyaniConvertedList.get(1));
		assertEquals("piyaniConvertedList third element must be equal to hua for the given operation", "hua",
				piyaniConvertedList.get(2));
	}

	@Test
	public void testIncrementTokenForMatchedCriteraPrefix() throws IOException {
		// given
		List<String> piyaniConvertedList = new ArrayList<String>();

		// when
		convertCharSetAndApplyOperation("刘德华", "prefix", "", piyaniConvertedList);

		// then
		assertEquals("piyaniConvertedList first element must be equal to lliu as the prefix is l for liu", "lliu",
				piyaniConvertedList.get(0));
		assertEquals("piyaniConvertedList second element must be equal to dde as the prefix is d for de", "dde",
				piyaniConvertedList.get(1));
		assertEquals("piyaniConvertedList third element must be equal to hhua as the prefix is h for hua", "hhua",
				piyaniConvertedList.get(2));
	}

	@Test
	public void testIncrementTokenForMatchedCriteraAppend() throws IOException {
		// given
		List<String> piyaniConvertedList = new ArrayList<String>();

		// when
		convertCharSetAndApplyOperation("刘德华", "append", " ad", piyaniConvertedList);

		// then
		assertEquals("piyaniConvertedList first element must be equal to 'liu adl ad' as the prefix is l for "
				+ "liu and the append string is 'ad'", "liu adl ad", piyaniConvertedList.get(0));
		assertEquals("piyaniConvertedList second element must be equal to 'de add ad' as the prefix is d for "
				+ "de and the append string is 'ad'", "de add ad", piyaniConvertedList.get(1));
		assertEquals("piyaniConvertedList third element must be equal to 'hua adh ad' as the prefix is h for "
				+ "hua and the append string is 'ad'", "hua adh ad", piyaniConvertedList.get(2));
	}

	@SuppressWarnings({ "resource" })
	private void convertCharSetAndApplyOperation(String charSet, String operation, String suffix,
			List<String> piyaniConvertedList) throws IOException {
		StringReader sr = new StringReader(charSet);
		Analyzer analyzer = new StandardAnalyzer();
		PinyinTokenFilter filter = new PinyinTokenFilter(analyzer.tokenStream("piyanistr", sr), operation, suffix);
		filter.reset();
		while (filter.incrementToken()) {
			CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
			piyaniConvertedList.add(ta.toString());
		}
	}
	
	@SuppressWarnings({ "resource", "rawtypes" })
	@Test
	public void testEnd() throws IOException {
		// given
		TokenStream tokenStreamMock = mock(TokenStream.class);
		LinkedHashMap attributesMock = mock(LinkedHashMap.class);
		LinkedHashMap attributeImplsMock = mock(LinkedHashMap.class);
		AttributeFactory factoryMock = mock(AttributeFactory.class);
		CharTermAttributeImpl charTermAttributeImpl = mock(CharTermAttributeImpl.class);
		OffsetAttributeImpl offsetAttributeImpl = mock(OffsetAttributeImpl.class);
		
		doReturn(charTermAttributeImpl).when(factoryMock).createAttributeInstance(CharTermAttribute.class);
		doReturn(offsetAttributeImpl).when(factoryMock).createAttributeInstance(OffsetAttribute.class);
		
		setInternalState(tokenStreamMock, "attributes", attributesMock);
		setInternalState(tokenStreamMock, "attributeImpls", attributeImplsMock);
		setInternalState(tokenStreamMock, "factory", factoryMock);
		setInternalState(tokenStreamMock, "currentState", new State[1]);
		String first_letter = "append";
		String padding_char = "ad";
		PinyinTokenFilter pinyinTokenFilter = new PinyinTokenFilter(tokenStreamMock, first_letter, padding_char);
		
		// when
		pinyinTokenFilter.end();
		
		// then
		verify(tokenStreamMock).end();
	}
}