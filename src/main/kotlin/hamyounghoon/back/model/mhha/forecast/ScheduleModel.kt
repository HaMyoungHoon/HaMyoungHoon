package hamyounghoon.back.model.mhha.forecast

data class ScheduleModel(
    var seq: Int = 0,
    var title: String = "",
    var start_date: String = "",
    var end_date: String = "",
    var person_seq: Int = 0,
    var is_del: Int = 0,
)
