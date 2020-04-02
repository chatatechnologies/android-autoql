package chata.can.chata_ai.pojo.webView

import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.PropertyChatActivity
import chata.can.chata_ai.pojo.color.ThemeColor

object HtmlMarked
{
	fun getTable(htmlTable: String): String
	{
		val backgroundColor = PropertyChatActivity.context?.let {
			"#" + Integer.toHexString(ContextCompat.getColor(
				it,
				ThemeColor.currentColor.drawerBackgroundColor) and 0x00ffffff)
		} ?: run {
			"#FFFFFF"
		}
		return """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<style>
        body {
          background-color: $backgroundColor;
        }
		</style>
    <title></title>
</head>
<body>
	$htmlTable
</body>
</html>"""
	}
}