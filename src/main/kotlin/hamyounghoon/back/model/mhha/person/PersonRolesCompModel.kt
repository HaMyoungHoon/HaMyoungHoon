package hamyounghoon.back.model.mhha.person

import io.swagger.v3.oas.annotations.media.Schema

data class PersonRolesCompModel(
    @field:Schema(description = "t_mhha_person 순번") var person_seq: Int = 0,
    @field:Schema(description = "t_mhha_person id") var person_id: Int = 0,
    @field:Schema(description = "t_mhha_system_list 순번") var system_seq: Int = 0,
    @field:Schema(description = "t_mhha_system_list name") var system_name: Int = 0,
    @field:Schema(description = "t_mhha_system_dept or t_mhha_system_roles 순번") var seq: Int = 0,
    @field:Schema(description = "t_mhha_system_dept or t_mhha_system_roles name") var name: String = "",
) {
}
