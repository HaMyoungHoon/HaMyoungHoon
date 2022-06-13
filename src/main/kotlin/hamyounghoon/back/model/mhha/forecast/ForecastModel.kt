package hamyounghoon.back.model.mhha.forecast

data class ForecastModel(
    var seq: Int = 0,
    var insert_date: String = "",
    var project_names_seq: Int = 0,
    var principals_seq: Int = 0,
    var model: String = "",
    var application_seq: Int = 0,
    var price: Long? = null,
    var price_etc: String? = null,
    var possibility: Int = 0,
    var quote_date: String = "",
    var contract_month: String = "",
    var situation: String = "",
    var state_seq: Int = 0,
    var person_seq: Int = 0,
    var support_seq: Int? = null,
) {

}
