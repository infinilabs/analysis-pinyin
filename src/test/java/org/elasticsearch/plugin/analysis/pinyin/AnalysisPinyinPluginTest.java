package org.elasticsearch.plugin.analysis.pinyin;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.Collection;

import org.elasticsearch.analysis.PinyinIndicesAnalysisModule;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.PinyinAnalysisBinderProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AnalysisPinyinPlugin.class, PinyinAnalysisBinderProcessor.class, PinyinIndicesAnalysisModule.class })
public class AnalysisPinyinPluginTest extends TestCase {

	private AnalysisPinyinPlugin analysisPinyinPlugin;
	
	@Before
	public void setUp() throws Exception {
		analysisPinyinPlugin = new AnalysisPinyinPlugin();
	}

	@Test
	public final void testName() {
		// when
		String name = analysisPinyinPlugin.name();
		
		// then
		assertEquals("Name must be equal to analysis-pinyin", "analysis-pinyin", name);
	}

	@Test
	public final void testDescription() {
		// when
		String description = analysisPinyinPlugin.description();
		
		// then
		assertEquals("description must be equal to convert Chinese to Pinyin", "convert Chinese to Pinyin", description);
	}

	@Test
	public final void testNodeModules() throws Exception {
		// given
		PinyinIndicesAnalysisModule pinyinIndicesAnalysisModuleMock = mock(PinyinIndicesAnalysisModule.class);
		whenNew(PinyinIndicesAnalysisModule.class).withNoArguments().thenReturn(pinyinIndicesAnalysisModuleMock);
		
		// when
		Collection<Module> modules = analysisPinyinPlugin.nodeModules();
		
		// then
		assertTrue("NodeModules method must add " + pinyinIndicesAnalysisModuleMock, modules.contains(pinyinIndicesAnalysisModuleMock));
	}
	
	@Test
	public final void testOnModule() throws Exception {
		// given
		AnalysisModule analysisModuleMock = mock(AnalysisModule.class);
		PinyinAnalysisBinderProcessor pinyinAnalysisBinderProcessorMock = mock(PinyinAnalysisBinderProcessor.class);
		
		whenNew(PinyinAnalysisBinderProcessor.class).withNoArguments().thenReturn(pinyinAnalysisBinderProcessorMock);
		
		// when
		analysisPinyinPlugin.onModule(analysisModuleMock);
		
		// then
		verify(analysisModuleMock).addProcessor(pinyinAnalysisBinderProcessorMock);
	}

}
