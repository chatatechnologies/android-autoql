package chata.can.chata_ai.dialog.hideColumn

interface ColumnChanges
{
	interface AllColumn
	{
		fun changeAllColumn(value: Boolean)
	}
	interface SingleColumn
	{
		fun changeVisible(position: Int, value: Boolean)
	}
}