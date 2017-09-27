/*
 * Copyright (c) 2017, WSO2 Inc. (http://wso2.com) All Rights Reserved.
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

package org.demo.customer.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.demo.customer.bean.Customer;
import org.demo.customer.dao.CustomerDAO;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 0.1-SNAPSHOT
 */
@Api(value = "customerservice")
@SwaggerDefinition(
        info = @Info(
                title = "Customer service Swagger Definition", version = "1.0",
                description = "The endpoint which is used to manage Customer",
                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0")
        )
)
@Path("/")
public class CustomerService {

    private static final Log logger = LogFactory.getLog(CustomerService.class);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Return customer details for the give id ",
            notes = "Returns HTTP 404 if customer doesn't exist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{id:808812,firstName:Johan,..}"),
            @ApiResponse(code = 404, message = "Particular exception message")})
    public Response getCustomer(@ApiParam(value = "id", required = true)
                                @PathParam("id") String id) {

        logger.info("HTTP GET /{id} resource invoked: [id] " + id);
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customerBean = customerDAO.getCustomer(id);

        if (customerBean != null) {
            JSONObject returnObject = new JSONObject();
            returnObject.put("id", customerBean.getId());
            returnObject.put("firstName", customerBean.getFname());
            returnObject.put("lastName", customerBean.getLname());
            returnObject.put("addrress", customerBean.getAddress());
            returnObject.put("state", customerBean.getState());
            returnObject.put("postalCode", customerBean.getPostalcode());
            returnObject.put("country", customerBean.getCountry());
            return Response.status(Response.Status.OK).entity(returnObject.toString()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("").build();
        }
    }
}
