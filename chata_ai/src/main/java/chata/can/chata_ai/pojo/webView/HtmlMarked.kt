package chata.can.chata_ai.pojo.webView

import androidx.core.content.ContextCompat
import chata.can.chata_ai.activity.chat.PropertyChatActivity
import chata.can.chata_ai.pojo.color.ThemeColor

object HtmlMarked
{
	fun getHTML(
		dataForWebView: DataForWebView): String
	{
		var backgroundColor = "#FFFFFF"
		var textColor = "#FFFFFF"

		with(ThemeColor.currentColor)
		{
			PropertyChatActivity.context?.let {
				backgroundColor = "#" + Integer.toHexString(ContextCompat.getColor(
					it,
					drawerBackgroundColor) and 0x00ffffff)

				textColor = "#" + Integer.toHexString(ContextCompat.getColor(
					it,
					drawerColorPrimary) and 0x00ffffff)
			}
		}

		return """<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://unpkg.com/sticky-table-headers"></script>
<style type="text/css">
    body, table, th{
      background: $backgroundColor !important;
			margin-top: 24px;
      color: $textColor !important;
    }
    table {
      padding-top: 0px!important;
    }
    th {
      position: sticky;
      top: 0px;
      z-index: 10;
      padding: 10px 3px 5px 3px;
    }
    table {
      display: table;
      min-width: 100%;
      white-space: nowrap;
      border-collapse: separate;
      border-spacing: 0px!important;
      border-color: grey;
    }
    table {
      font-size: 12px;
    }
    tr td:first-child {
      text-align: center;
    }
    td {
      padding: 3px;
      text-align: center!important;
    }
		.green {
		  color: #2ECC40;
		}
		.red {
		  color: red;
		}
</style>
<title></title>
</head>
<body>
	${dataForWebView.table}
	<div id="bar" style="display:none;">bar container</div>
  <div id="column" style="display:none;">column container</div>
  <div id="line" style="display:none;">line container</div>
  <div id="pie" style="display:none;">pie container</div>
	${dataForWebView.datePivot}
<script>
	var aId = ["nativeTable", "bar", "column", "line", "pie", "pivotTable"];
  function toggleCharts(idChart)
  {
    aId.forEach(
      element =>
      {
        if (element != idChart)
        {
          ${'$'}("#" + element).hide("fast");//show
        }
      }
    );
    ${'$'}("#" + idChart).show("fast");//show
  }
</script>
</body>
</html>"""
	}
}