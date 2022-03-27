package chata.can.chata_ai_api.data.model

import chata.can.chata_ai.pojo.dashboard.Dashboard

fun DashboardItemDataResponse.dashboardItemDataToEntity() = Dashboard(
	this.displayType,
	this.h,
	this.i,
	false,
	this.key,
	this.maxH,
	this.minH,
	this.minW,
	this.moved,
	this.query,
	false,
	this.static,
	this.title,
	this.w,
	this.x,
	this.y
)