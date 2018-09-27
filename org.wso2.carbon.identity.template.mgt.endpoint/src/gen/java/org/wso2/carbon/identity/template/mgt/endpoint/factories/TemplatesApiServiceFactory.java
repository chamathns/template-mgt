package org.wso2.carbon.identity.template.mgt.endpoint.factories;

import org.wso2.carbon.identity.template.mgt.endpoint.TemplatesApiService;
import org.wso2.carbon.identity.template.mgt.endpoint.impl.TemplatesApiServiceImpl;

public class TemplatesApiServiceFactory {

   private final static TemplatesApiService service = new TemplatesApiServiceImpl();

   public static TemplatesApiService getTemplatesApi()
   {
      return service;
   }
}
