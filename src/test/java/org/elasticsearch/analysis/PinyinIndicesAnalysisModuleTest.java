package org.elasticsearch.analysis;

import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

import org.elasticsearch.common.inject.Binder;
import org.elasticsearch.common.inject.binder.AnnotatedBindingBuilder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PinyinIndicesAnalysisModule.class, Binder.class })
public class PinyinIndicesAnalysisModuleTest extends TestCase {

	@SuppressWarnings("rawtypes")
	public final void testConfigure() throws Exception {
		// given
		AnnotatedBindingBuilder annotatedBindingBuilderMock = mock(AnnotatedBindingBuilder.class);
		Binder binderMock = mock(Binder.class);
		doReturn(annotatedBindingBuilderMock).when(binderMock).bind(PinyinIndicesAnalysis.class);
		PinyinIndicesAnalysisModule pinyinIndicesAnalysisModule = new PinyinIndicesAnalysisModule();
		setInternalState(pinyinIndicesAnalysisModule, "binder", binderMock);
		
		// when
		pinyinIndicesAnalysisModule.configure();
		
		// then
		verifyPrivate(pinyinIndicesAnalysisModule).invoke("bind", PinyinIndicesAnalysis.class);
	}

}
