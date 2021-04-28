package chata.can.chata_ai.pojo.html

object Actions
{
	fun getActions(): String
	{
		return """
	<div style="display: none;">
    <button class="button" onclick="updateData(TypeEnum.TABLE)">TABLE</button>
    <!-- <button class="button" onclick="updateData(TypeEnum.PIVOT)">PIVOT</button> -->
    <button class="button" onclick="updateData(TypeEnum.COLUMN)">COLUMN</button>
    <button class="button" onclick="updateData(TypeEnum.BAR)">BAR</button>
    <button class="button" onclick="updateData(TypeEnum.LINE)">LINE</button>
    <button class="button" onclick="updateData(TypeEnum.PIE)">PIE</button>
    <!-- <button class="button" onclick="updateData(TypeEnum.HEATMAP)">HEATMAP</button> -->
    <!-- <button class="button" onclick="updateData(TypeEnum.BUBBLE)">BUBBLE</button> -->
    <!-- <button class="button" onclick="updateData(TypeEnum.STACKED_BAR)">STACKED BAR</button> -->
    <!-- <button class="button" onclick="updateData(TypeEnum.STACKED_COLUMN)">STACKED COLUMN</button> -->
    <!-- <button class="button" onclick="updateData(TypeEnum.STACKED_AREA)">STACKED AREA</button> -->
  </div>
	<table><thead><tr><th>Month</th><th>Total Order Amount</th></tr></thead><tbody><tr><td>nov. 2019</td><td>${'$'}106,630.75</td></tr><tr><td>oct. 2019</td><td>${'$'}104,254.00</td></tr><tr><td>sep. 2019</td><td>${'$'}57,326.75</td></tr><tr><td>ago. 2019</td><td>${'$'}122,868.00</td></tr><tr><td>may. 2019</td><td>${'$'}100,500.00</td></tr></tbody></table>
		"""
	}
}