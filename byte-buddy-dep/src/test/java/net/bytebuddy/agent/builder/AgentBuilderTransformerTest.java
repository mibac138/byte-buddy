package net.bytebuddy.agent.builder;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.test.utility.MockitoRule;
import net.bytebuddy.utility.JavaModule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class AgentBuilderTransformerTest {

    @Rule
    public TestRule mockitoRule = new MockitoRule(this);

    @Mock
    private DynamicType.Builder<?> builder;

    @Mock
    private AgentBuilder.Transformer first, second;

    @Mock
    private TypeDescription typeDescription;

    @Mock
    private ClassLoader classLoader;

    @Mock
    private JavaModule module;

    @Test
    @SuppressWarnings("unchecked")
    public void testNoOp() throws Exception {
        assertThat(AgentBuilder.Transformer.NoOp.INSTANCE.transform(builder, typeDescription, classLoader, module), sameInstance((DynamicType.Builder) builder));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCompound() throws Exception {
        when(first.transform(builder, typeDescription, classLoader, module)).thenReturn((DynamicType.Builder) builder);
        when(second.transform(builder, typeDescription, classLoader, module)).thenReturn((DynamicType.Builder) builder);
        assertThat(new AgentBuilder.Transformer.Compound(first, second).transform(builder, typeDescription, classLoader, module), sameInstance((DynamicType.Builder) builder));
        verify(first).transform(builder, typeDescription, classLoader, module);
        verifyNoMoreInteractions(first);
        verify(second).transform(builder, typeDescription, classLoader, module);
        verifyNoMoreInteractions(second);
    }
}
