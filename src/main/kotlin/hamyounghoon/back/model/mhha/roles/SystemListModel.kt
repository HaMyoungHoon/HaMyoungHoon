package hamyounghoon.back.model.mhha.roles

import io.swagger.v3.oas.annotations.media.Schema

data class SystemListModel(
    @field:Schema(description = "t_mhha_system_list 순번") var seq: Int = 0,
    @field:Schema(description = "t_mhha_system_list 시스템 이름") var naem: String = "",
) {
    enum class SystemListEnum(val roles: String) {
        NONE("NONE"),
        ADMIN("ADMIN"),
        HAMYOUNGHOON("HAMYOUNGHOON"),
        ETC("ETC")
    }

    companion object {
        fun tryParse(data: String): SystemListEnum? {
            return try {
                SystemListEnum.valueOf(data)
            } catch (e: IllegalArgumentException) {
                null
            }
        }

        fun tryFindContains(data: String): SystemListEnum? {
            return try {
                SystemListEnum.valueOf(data)
            } catch (e: java.lang.IllegalArgumentException) {
                null
            }
        }

        fun tryFind(data: MutableCollection<String>, target: SystemListEnum): Boolean {
            data.forEach {
                try {
                    if (SystemListEnum.valueOf(it) == target) {
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
