package chata.can.chata_ai.pojo

import chata.can.chata_ai.BuildConfig

val nullValue = null

const val referenceIdKey = "reference_id"
const val dataKey = "data"
const val messageKey = "message"

const val urlBase = "https://backend.chata.ai/"
const val urlStaging = "https://backend-staging.chata.ai/"
const val urlChataIODev = "https://backend-staging.chata.io/"
const val urlChataIOProd = "https://backend-staging.chata.io/"
//const val urlChataIOProd = "https://backend.chata.io/"
const val api1 = "api/v1/"

fun getMainURL() = if (BuildConfig.DEBUG && !BuildConfig.isDevProd) urlChataIODev else urlChataIOProd
