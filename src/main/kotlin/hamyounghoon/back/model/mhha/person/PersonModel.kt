package hamyounghoon.back.model.mhha.person

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

data class PersonModel(
    @field:Schema(description = "t_mhha_person 순번") var seq: Int = 0,
    @field:Schema(description = "t_mhha_person id") var id: String = "",
    @field:Schema(description = "t_mhha_person pw") var pw: String = "",
    @field:Schema(description = "t_mhha_person name") var name: String = "",
    @field:Schema(description = "t_mhha_person entry_date") var entry_date: String = "",
    @field:Schema(description = "t_mhha_person resign_date") var resign_date: String = "",
    @field:Schema(description = "t_mhha_person 계정 상태", example = "ONLINE : 정상, DELETE : 삭제 됨, STOP : 중지 됨") var status: String = "",
): UserDetails {
    @Schema(description = "t_mhha_system_roles join user roles data list")
    var roles: MutableList<PersonRolesCompModel> = arrayListOf()
    @Schema(description = "t_mhha_system_dept join user dept data list")
    var dept: MutableList<PersonRolesCompModel> = arrayListOf()

    fun rolesList(): MutableCollection<String> =
        roles.stream().map { it.name }.collect(Collectors.toList())
    fun deptList(): MutableCollection<String> =
        dept.stream().map { it.name }.collect(Collectors.toList())

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val ret: MutableList<PersonRolesCompModel> = arrayListOf()
        ret.addAll(roles)
        ret.addAll(dept)
        return ret.stream().map { SimpleGrantedAuthority(it.name) }.collect(Collectors.toList())
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun getUsername() = this.name

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun getPassword() = this.pw

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isAccountNonExpired() = true

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isAccountNonLocked() = true

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isCredentialsNonExpired() = true

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isEnabled() = true
}
