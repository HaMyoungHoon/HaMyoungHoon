package hamyounghoon.back.model.mhha.roles

import io.swagger.v3.oas.annotations.media.Schema

data class PersonDeptModel(
    @field:Schema(description = "t_mhha_person_dept 순번")
    var seq: Int = 0,
    @field:Schema(description = "t_mhha_system_dept 순번")
    var system_dept_seq: Int = 0,
    @field:Schema(description = "t_mhha_person 순번")
    var person_seq: Int = 0
) {
}
