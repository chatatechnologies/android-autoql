package chata.can.chata_ai.activity.dataMessenger.holder

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.toCapitalColumn
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor

class HelpHolder(itemView: View): Holder(itemView)
{
	private var llContent = itemView.findViewById<View>(R.id.llContent) ?: null
	private var llContent2 = itemView.findViewById<View>(R.id.llContent2) ?: null
	private var tvGreatNewHelp= itemView.findViewById<TextView>(R.id.tvGreatNewHelp) ?: null
	private var tvContent = itemView.findViewById<TextView>(R.id.tvContent) ?: null

	override fun onPaint()
	{
		llContent?.backgroundGrayWhite()

		tvContent?.let {
			val gray = it.context.getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary)
			it.setTextColor(gray)
			tvGreatNewHelp?.setTextColor(gray)
			llContent2?.backgroundGrayWhite()
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		tvContent?.let {
			tvContent ->
			if (item is QueryBase)
			{
				val list = item.simpleText.split("#")
				if (list.size > 1)
				{
					val url = list[0]
					var content = list[1]
					content = content.toCapitalColumn().replace("-", " ")
					tvContent.text = content
					tvContent.setOnClickListener {
						val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
						tvContent.context?.startActivity(intent)
					}
				}
			}
		}
	}
}