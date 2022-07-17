package com.flapps.web.controller;

import com.flapps.web.common.RequestFilterTo;
import com.flapps.web.common.RequestFilterUtil;
import com.flapps.web.common.ResponseFilterRowsTo;
import com.flapps.web.entity.*;
import com.flapps.web.service.AttendanceService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;

@Controller
@Path("attendance")
@Api(value = "/attendance", description = "This service allows CLOCK IN/OUT (+ other type of clockins) for user")
@Produces({"application/json"})
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	//search the attendance using userID
	@Path("/{userID}")
	@GET
	@ApiOperation(response = AttendanceResponseStateRTO.class, value = "Return the attendance logs of actual state")
	public AttendanceResponseStateRTO<AttendanceStateRTO> get(@PathParam("userID") Long userId,
															  @Context HttpServletRequest request,
															  @Context HttpServletResponse response) throws IOException {
		UserEntity user = new UserEntity();
		user.setId(userId);
		return attendanceService.getActualStateToResponseForUser(user);
	}

	//get the detail data of last logged user
	@Path("/{userID}/detail")
	@GET
	@ApiOperation(response = AttendanceResponseStateRTO.class, value = "Return summary of last values logged user")
	public ResponseFilterRowsTo<AttendanceDetailFilterRTO, AttendanceDetailRTO>
		getAttendanceDetailList(@Context HttpServletRequest request) {
			RequestFilterTo<AttendanceDetailFilterRTO> filter =
					RequestFilterUtil.parseQuery(AttendanceDetailFilterRTO.class, request);
			return attendanceService.getAttendanceDetailList(filter);
	}

}





