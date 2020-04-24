package com.sypm.webapi.webService;

import com.sypm.webapi.bean.StudentBean;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "CommonService", targetNamespace = "http://webService.webapi.sypm.com")
public interface CommonService {

    //   @WebMethod
//    public @WebResult(name = "Hello_RESULT")String sayHello(@WebParam(name = "IM_CTRL") String im_ctrl, @WebParam(name = "IM_DATA")String im_data);

    @WebMethod
    public @WebResult(name = "Sap2Java_RESULT")String Sap2Java(@WebParam(name = "IM_CTRL") String IM_CTRL, @WebParam(name = "IM_DATA")String IM_DATA);
}


