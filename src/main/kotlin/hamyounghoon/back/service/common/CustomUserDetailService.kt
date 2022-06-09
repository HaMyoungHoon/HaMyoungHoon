package hamyounghoon.back.service.common

import hamyounghoon.back.advice.exception.NotFoundUserException
import hamyounghoon.back.mapper.mhha.person.PersonMapper
import hamyounghoon.back.mapper.mhha.person.RolesMapper
import hamyounghoon.back.model.mhha.person.PersonModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailService: UserDetailsService {
    @Autowired
    lateinit var personMapper: PersonMapper
    @Autowired
    lateinit var rolesMapper: RolesMapper

    override fun loadUserByUsername(login_id: String): PersonModel =
        Optional.ofNullable(personMapper.findPersonByID(login_id)).orElseThrow { NotFoundUserException() }.apply {
            roles.addAll(rolesMapper.findUserRolesCompFromRolesByPersonSeq(this.seq))
            dept.addAll(rolesMapper.findUserRolesCompFromDeptByPersonSeq(this.seq))
        }
}
