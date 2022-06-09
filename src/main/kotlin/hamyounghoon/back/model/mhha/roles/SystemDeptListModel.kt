package hamyounghoon.back.model.mhha.roles

import io.swagger.v3.oas.annotations.media.Schema

data class SystemDeptListModel(
    @field:Schema(description = "t_mhha_system_dept 순번")
    var seq: Int = 0,
    @field:Schema(description = "t_mhha_system_list 순번")
    var system_seq: Int = 0,
    @field:Schema(description = "t_mhha_system_dept 부서명")
    var name: String = ""
) {
    enum class SystemDeptListEnum(val dept: String) {
        NONE("NONE"),
        SOFTWARE("SOFTWARE"),
        FINANCE("FINANCE"),
        SALES("SALES"),
        PRODUCT("PRODUCT"),
        HR("HR"),
    }

    companion object {
        fun tryParse(data: String): SystemDeptListEnum? {
            return try {
                SystemDeptListEnum.valueOf(data)
            } catch (e: IllegalArgumentException) {
                null
            }
        }

        fun tryFindContains(data: String): SystemDeptListEnum? {
            return try {
                SystemDeptListEnum.valueOf(data)
            } catch (e: java.lang.IllegalArgumentException) {
                null
            }
        }

        fun tryFind(data: MutableCollection<String>, target: SystemDeptListEnum): Boolean {
            data.forEach {
                try {
                    if (SystemDeptListEnum.valueOf(it) == target) {
                        return true
                    }
                } catch (e: IllegalArgumentException) {
                    return false
                }
            }

            return false
        }

        fun tryFindContains(data: List<String>, target: String): Boolean {
            data.forEach {
                return try {
                    when {
                        it.uppercase().contains(target.uppercase()) -> {
                            return true
                        }
                    }

                    false
                } catch (e: IllegalArgumentException) {
                    false
                }
            }

            return false
        }
    }
}
