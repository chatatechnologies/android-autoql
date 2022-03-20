package chata.can.chata_ai.retrofit.core.extension

import chata.can.chata_ai.pojo.SinglentonDrawer
import java.util.*

fun getCurrentLocale(): Locale = SinglentonDrawer.localLocale?: run { Locale.US }