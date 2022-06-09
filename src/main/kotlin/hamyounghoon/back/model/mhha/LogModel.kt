package hamyounghoon.back.model.mhha

import io.swagger.v3.oas.annotations.media.Schema

data class LogModel(
    @field:Schema(description = "t_mhha_log 순번") var seq: Int = 0,
    @field:Schema(description = "t_mhha_log date", example = "yyyy-MM-dd HH:mm:ss") var log_date: String = "",
    @field:Schema(description = "t_mhha_person 순번") var person_seq: Int = 0,
    @field:Schema(description = "t_mhha_log 내용", example = "[insert or update or delete]\t내용\t키 : 값") var query: String = ""
) {
}
