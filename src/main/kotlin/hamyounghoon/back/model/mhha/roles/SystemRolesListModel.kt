package hamyounghoon.back.model.mhha.roles

import io.swagger.v3.oas.annotations.media.Schema

data class SystemRolesListModel(
    @field:Schema(description = "t_mhha_system_roles 순번")
    var seq: Int = 0,
    @field:Schema(description = "t_mhha_system_list 순번")
    var system_seq: Int = 0,
    @field:Schema(description = "t_mhha_system_roles 권한 이름")
    var name: String = ""
) {
    enum class SystemRolesListEnum(val roles: String) {
        NONE("NONE"),
        PORTAL_ADMIN("PORTAL_ADMIN"),
        PORTAL_USER("PORTAL_USER"),
        HAMYOUNGHOON_ADMIN("HAMYOUNGHOON_ADMIN"),
        HAMYOUNGHOON_SCHEDULE_READ("HAMYOUNGHOON_SCHEDULE_READ"),
        HAMYOUNGHOON_SCHEDULE_WRITE("HAMYOUNGHOON_SCHEDULE_WRITE"),
        HAMYOUNGHOON_CONTRACT_READ("HAMYOUNGHOON_CONTRACT_READ"),
        HAMYOUNGHOON_CONTRACT_WRITE("HAMYOUNGHOON_CONTRACT_WRITE"),
    }

    companion object {
        fun tryParse(data: String): SystemRolesListEnum? {
            return try {
                SystemRolesListEnum.valueOf(data)
            } catch (e: IllegalArgumentException) {
                null
            }
        }

        fun tryFindContains(data: String): SystemRolesListEnum? {
            return try {
                SystemRolesListEnum.valueOf(data)
            } catch (e: java.lang.IllegalArgumentException) {
                null
            }
        }

        fun tryFind(data: MutableCollection<String>, target: SystemRolesListEnum): Boolean {
            data.forEach {
                try {
                    if (SystemRolesListEnum.valueOf(it) == target) {
                        return true
                    }
                } catch (e: IllegalArgumentException) {
                    return false
                }
            }

            return false
        }
        fun tryFindList(data: MutableCollection<String>, target: List<SystemRolesListEnum>): Boolean {
            data.forEach {x ->
                try {
                    target.forEach { y ->
                        if (SystemRolesListEnum.valueOf(x) == y) {
                            return true
                        }
                    }
                } catch (e: java.lang.IllegalArgumentException) {
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
