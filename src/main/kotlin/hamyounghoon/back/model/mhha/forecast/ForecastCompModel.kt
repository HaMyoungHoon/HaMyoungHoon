package hamyounghoon.back.model.mhha.forecast

data class ForecastCompModel(
    var seq: Int = 0,
    var insert_date: String = "",
    var project_name: String? = null,
    var principals_name: String? = null,
    var model: String = "",
    var application_name: String? = null,
    var price: Long? = null,
    var price_etc: String? = null,
    var possibility: Int = 0,
    var quote_date: String = "",
    var contract_month: String = "",
    var situation: String = "",
    var state_name: String? = null,
    var person_name: String? = null,
    var support_name: String? = null,
    var back_color: String = "",
    var text_color: String = "",
) {

}
