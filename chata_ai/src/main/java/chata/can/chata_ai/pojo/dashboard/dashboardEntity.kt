package chata.can.chata_ai.pojo.dashboard

fun DashboardItemDataResponse.dashboardItemDataToEntity() = Dashboard(
	this.displayType,
	this.h,
	this.i,
	this.isNewTile,
	this.key,
	this.maxH,
	this.minH,
	this.minW,
	this.moved,
	this.query,
	this.splitView,
	this.static,
	this.title,
	this.w,
	this.x,
	this.y
).apply {
	secondDisplayType = this@dashboardItemDataToEntity.secondDisplayType
	secondQuery = this@dashboardItemDataToEntity.secondQuery
}