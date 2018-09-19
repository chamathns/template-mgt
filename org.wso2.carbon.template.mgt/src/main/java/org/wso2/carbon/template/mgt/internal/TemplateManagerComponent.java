/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.template.mgt.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.template.mgt.dao.TemplateManagerDAO;
import org.wso2.carbon.template.mgt.dao.impl.TemplateManagerDAOImpl;

@Component(
        name = "carbon.identity.template.mgt.component",
        immediate = true
)

public class TemplateManagerComponent {
    private  static Log log = LogFactory.getLog(TemplateManagerComponent.class);
    /**
     * Register Template Manager as an OSGi service.
     *
     * @param componentContext OSGi service component context.
     */
    @Activate
    protected void activate(ComponentContext componentContext){
        BundleContext bundleContext = componentContext.getBundleContext();

        bundleContext.registerService(TemplateManagerDAO.class.getName(),new TemplateManagerDAOImpl(),null);
        if (log.isDebugEnabled()){
            log.debug("Template Manager bundle is activated");
        }

    }
}