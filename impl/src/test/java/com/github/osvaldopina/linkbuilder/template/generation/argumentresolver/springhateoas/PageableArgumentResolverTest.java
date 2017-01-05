package com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.springhateoas;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableType;
import com.github.osvaldopina.linkbuilder.template.Variables;
import org.junit.Test;
import org.springframework.data.domain.Pageable;

public class PageableArgumentResolverTest {

	Method method = PageableArgumentResolverTest.class.getMethod("method", Pageable.class, Object.class);

	int pageableParameterIndex = 0;

	int nonPageableParameterIndex = 1;

	PageableArgumentResolver pageableArgumentResolver = new PageableArgumentResolver();


	public PageableArgumentResolverTest() throws Exception {
	}

	@Test
	public void resolveForPageableParameter() throws Exception {

		assertThat(pageableArgumentResolver.resolveFor(method, pageableParameterIndex), is(true));

	}

	@Test
	public void resolveForNonPageableParameter() throws Exception {

		assertThat(pageableArgumentResolver.resolveFor(method, nonPageableParameterIndex), is(false));

	}

	@Test
	public void create() throws Exception {

		Variables variables = pageableArgumentResolver.create(method, pageableParameterIndex);

		assertThat(variables, is(notNullValue()));

		Variable page = variables.get("page");
		assertThat(page.getVariableType(), is(VariableType.QUERY));
		assertThat(page.getMethod(), is(sameInstance(method)));
		assertThat(page.getParameterIndex(), is(0));

		Variable size = variables.get("size");
		assertThat(size.getVariableType(), is(VariableType.QUERY));
		assertThat(size.getMethod(), is(sameInstance(method)));
		assertThat(size.getParameterIndex(), is(0));

		Variable sort = variables.get("sort");
		assertThat(size.getVariableType(), is(VariableType.QUERY));
		assertThat(size.getMethod(), is(sameInstance(method)));
		assertThat(size.getParameterIndex(), is(0));

	}

	public void method(Pageable pageableParameter, Object nomPageableParameter) {

	}
}