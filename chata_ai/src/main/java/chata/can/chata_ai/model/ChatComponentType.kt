package chata.can.chata_ai.model

enum class ChatComponentType(val type: String = "")
{
	INTRODUCTION("text"),
	QUERY_BUILDER("qb"),
	WEB_VIEW("data"),
	BAR("bar"),
	LINE("line"),
	COLUMN("column"),
	PIE("pie"),
	HEAT_MAP("heatmap"),
	BUBBLE("bubble"),
	STACK_COLUMN("stacked_column"),
	STACK_AREA("stacked_line"),
	STACK_BAR("stacked_bar"),
	TABLE("table"),
	SUGGESTION("suggestion"),
	SAFETY("safetynet"),
	UNKNOWN
}