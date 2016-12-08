package com.github.osvaldopina.linkbuilder.annotation.value;

import com.github.osvaldopina.linkbuilder.annotation.Link;
import com.github.osvaldopina.linkbuilder.annotation.Param;

public interface LinkValue {

    String getDestination();

    Class<?> getController();

    String getRel();

    String getOverridedRel();

    String getWhen();

    boolean getTemplated();

    ParamValue[] getParams();

}
