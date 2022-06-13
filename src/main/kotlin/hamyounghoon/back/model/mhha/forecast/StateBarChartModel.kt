package hamyounghoon.back.model.mhha.forecast

data class StateBarChartModel(
    var date: String = "",
    var price: Long = 0,
    var state_seq: Int = 0,
    var name: String = "",
    var back_color: String = "",
) {
}
