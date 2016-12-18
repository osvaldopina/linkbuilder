package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.List;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableVariableValueDiscoverTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	IntrospectionUtils introspectionUtils;

	Method method = PageableVariableValueDiscoverTest.class.getMethod("method", Pageable.class, Object.class);

	@Mock
	MethodCall methodCall;

	@Mock
	Variables variables;

	@Mock
	Variable pageVariable;

	@Mock
	Variable sizeVariable;

	@Mock
	Variable sortVariable;

	@Mock
	Sort sort;

	@Mock
	Pageable pageable;

	Object payload = new Object();

	@Mock
	ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies;


	int pageableParameterIndex = 0;

	int nonPageableParameterIndex = 1;

	@TestSubject
	PageableVariableValueDiscover pageableParameterVariableValueDiscover =
			new PageableVariableValueDiscover();

	public PageableVariableValueDiscoverTest() throws Exception {
	}

	@Test
	public void getVariableValues() throws Exception {
		expect(methodCall.getParam(pageableParameterIndex)).andReturn(pageable);

		expect(variables.get("page")).andReturn(pageVariable);
		expect(pageable.getPageNumber()).andReturn(1);
		expect(conditionalVariableSubstitutionStrategies.shouldSubstitute(pageVariable, 1)).andReturn(true);

		expect(variables.get("size")).andReturn(sizeVariable);
		expect(pageable.getPageSize()).andReturn(1);
		expect(conditionalVariableSubstitutionStrategies.shouldSubstitute(sizeVariable, 1)).andReturn(true);

		expect(variables.get("sort")).andReturn(sortVariable);
		expect(pageable.getSort()).andReturn(sort);
		expect(conditionalVariableSubstitutionStrategies.shouldSubstitute(sortVariable, sort)).andReturn(true);


		replayAll();

		List<VariableValue> variableValueList = pageableParameterVariableValueDiscover.
				getVariableValues(variables, methodCall, payload, pageableParameterIndex, conditionalVariableSubstitutionStrategies);

		verifyAll();

		assertThat(variableValueList, hasSize(3));
	}



	@Test
	public void canDiscover() throws Exception {
		expect(methodCall.getMethod()).andReturn(method);

		replayAll();

		assertThat(pageableParameterVariableValueDiscover.canDiscover(methodCall, pageableParameterIndex), is(true));

		verifyAll();
	}

	@Test
	public void canDiscover_notPageableParameer() throws Exception {
		expect(methodCall.getMethod()).andReturn(method);

		replayAll();

		assertThat(pageableParameterVariableValueDiscover.canDiscover(methodCall, nonPageableParameterIndex), is(false));

		verifyAll();
	}

	public void method(Pageable pageableParameter, Object nonPageableParameter) {

	}
}