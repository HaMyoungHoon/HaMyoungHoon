package hamyounghoon.back.service.mhha

import hamyounghoon.back.advice.exception.*
import hamyounghoon.back.config.security.JwtTokenProvider
import hamyounghoon.back.mapper.mhha.LogMapper
import hamyounghoon.back.mapper.mhha.person.RolesMapper
import hamyounghoon.back.model.mhha.person.*
import hamyounghoon.back.model.mhha.roles.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class RolesService {
    @Autowired lateinit var rolesMapper: RolesMapper
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider
    @Autowired lateinit var logMapper: LogMapper

    /**
     * 유저 부서를 추가함.
     * @param token 추가하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 이름 순번
     * @param dept 부서 이름 t_mhha_system_dept_list
     */
    fun addPersonDept(token: String, person_seq: Int, system_seq: Int, dept: String, isMhha: Boolean = false) {
        val user = getPerson(token)
        if (!isMhha) {
            isAdmin(token)
        }

        val findDept = Optional.ofNullable(rolesMapper.findSystemDeptBySystemSeq(system_seq)).orElseThrow {
            addLog(user.seq, "[INSERT]\t부서 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,dept:$dept")
            NotFoundSystemException()
        }.find { it.name == dept }

        if (findDept == null) {
            addLog(user.seq, "[INSERT]\t부서 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,dept:$dept")
            throw NotFoundRolesException()
        }

        val userDept = Optional.ofNullable(rolesMapper.findPersonDeptByPersonSeq(person_seq)).orElseThrow().find { it.system_dept_seq == findDept.seq }
        if (userDept != null) {
            return
        }

        Optional.ofNullable(rolesMapper.addPersonDept(PersonDeptModel().apply {
            this.person_seq = person_seq
            system_dept_seq = findDept.seq
        })).orElseThrow { CommunicationException() }
        addLog(user.seq, "[INSERT]\t부서 추가\tperson_seq:$person_seq,system_seq:$system_seq,dept:${findDept.name}")
    }

    /**
     * 유저 부서를 추가함.
     * @param token 추가하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_system_list
     * @param dept_seq 부서 순번 t_mhha_system_dept_list
     */
    fun addPersonDeptSeq(token: String, person_seq: Int, system_seq: Int, dept_seq: Int, isMhha: Boolean = false) {
        val user = getPerson(token)
        if (!isMhha) {
            isAdmin(token)
        }

        val userDept = Optional.ofNullable(rolesMapper.findPersonDeptByPersonSeq(person_seq)).orElseThrow().find { it.system_dept_seq == dept_seq }
        if (userDept != null) {
            return
        }

        val findDept = Optional.ofNullable(rolesMapper.findSystemDeptBySystemSeq(system_seq)).orElseThrow {
            addLog(user.seq, "[INSERT]\t부서 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,dept_seq:$dept_seq")
            NotFoundSystemException()
        }.find { it.seq == dept_seq }

        if (findDept == null) {
            addLog(user.seq, "[INSERT]\t부서 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,dept_seq:$dept_seq")
            throw NotFoundRolesException()
        }

        Optional.ofNullable(rolesMapper.addPersonDept(PersonDeptModel().apply {
            this.person_seq = person_seq
            system_dept_seq = findDept.seq
        })).orElseThrow { CommunicationException() }
        addLog(user.seq, "[INSERT]\t부서 추가\tperson_seq:$person_seq,system_seq:$system_seq,dept:${findDept.name}")
    }
    /**
     * 유저 부서들을 추가함.
     * @param token 추가하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_system_list
     * @param dept 부서 이름 리스트 t_mhha_system_dept_list
     */
    fun addPersonDeptList(token: String, person_seq: Int, system_seq: Int, dept: MutableList<String>, isMhha: Boolean = false) {
        val user = getPerson(token)
        isAdmin(token)

        val findDept = Optional.ofNullable(rolesMapper.findSystemDeptBySystemSeq(system_seq)).orElseThrow {
            addLog(user.seq, "[INSERT]\t부서 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,dept:${dept.joinToString()}")
            NotFoundSystemException()
        }

        val deptList = getCanAddDeptList(person_seq, dept, findDept)
        if (deptList.size == 0) {
            addLog(user.seq, "[INSERT]\t부서 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,dept:${dept.joinToString()}")
            return
        }

        Optional.ofNullable(rolesMapper.addPersonDeptList(getPersonDeptModel(person_seq, deptList))).orElseThrow { CommunicationException() }
        addLog(user.seq, "[INSERT]부서 추가\tperson_seq:$person_seq,system_seq:$system_seq,dept:${deptList.stream().map { it.name }.toArray().joinToString()}")
    }

    /**
     * 유저 부서들을 추가함.
     * @param token 추가하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_system_list
     * @param dept_seq 부서 순번 리스트 t_mhha_system_dept_list
     */
    fun addPersonDeptSeqList(token: String, person_seq: Int, system_seq: Int, dept_seq: MutableList<Int>, isMhha: Boolean = false) {
        val user = getPerson(token)
        isAdmin(token)

        val findDept = Optional.ofNullable(rolesMapper.findSystemDeptBySystemSeq(system_seq)).orElseThrow {
            addLog(user.seq, "[INSERT]\t부서 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,dept_seq:${dept_seq.joinToString()}")
            NotFoundSystemException()
        }

        val deptList = getCanAddDeptListForSeq(person_seq, dept_seq, findDept)
        if (deptList.size == 0) {
            addLog(user.seq, "[INSERT]\t부서 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,dept_seq:${dept_seq.joinToString()}")
            return
        }

        Optional.ofNullable(rolesMapper.addPersonDeptList(getPersonDeptModel(person_seq, deptList))).orElseThrow { CommunicationException() }
        addLog(user.seq, "[INSERT]\t부서 추가\tperson_seq:$person_seq,system_seq:$system_seq,dept:${deptList.stream().map { it.name }.toArray().joinToString()}")
    }

    /**
     * 유저 권한을 추가함
     * @param token 추가하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_system_list
     * @param roles 권한 이름 t_mhha_system_roles_list
     */
    fun addPersonRoles(token: String, person_seq: Int, system_seq: Int, roles: String, isMhha: Boolean = false) {
        val user = getPerson(token)
        if (!isMhha) {
            isAdmin(token)
        }

        val findRol = Optional.ofNullable(rolesMapper.findSystemRolesBySystemSeq(system_seq)).orElseThrow {
            addLog(user.seq, "[INSERT]\t권한 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,roles:$roles")
            NotFoundSystemException()
        }.find { it.name == roles }

        if (findRol == null) {
            addLog(user.seq, "[INSERT]\t권한 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,roles:$roles")
            throw NotFoundRolesException()
        }

        val userRol = Optional.ofNullable(rolesMapper.findPersonRolesByPersonSeq(person_seq)).orElseThrow().find { it.system_roles_seq == findRol.seq }
        if (userRol != null) {
            return
        }

        Optional.ofNullable(rolesMapper.addPersonRoles(PersonRolesModel().apply {
            this.person_seq = person_seq
            system_roles_seq = findRol.seq
        })).orElseThrow { CommunicationException() }
        addLog(user.seq, "[INSERT]권한 추가\tperson_seq:$person_seq,system_seq:$system_seq,roles:${findRol.name}")
    }

    /**
     * 유저 권한을 추가함
     * @param token 추가하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_system_list
     * @param roles_seq 권한 순번 t_mhha_system_roles_list
     */
    fun addPersonRolesSeq(token: String, person_seq: Int, system_seq: Int, roles_seq: Int, isMhha: Boolean = false) {
        val user = getPerson(token)
        isAdmin(token)

        val userRol = Optional.ofNullable(rolesMapper.findPersonRolesByPersonSeq(person_seq)).orElseThrow().find { it.system_roles_seq == roles_seq }
        if (userRol != null) {
            return
        }

        val findRol = Optional.ofNullable(rolesMapper.findSystemRolesBySystemSeq(system_seq)).orElseThrow {
            addLog(user.seq, "[INSERT]\t권한 추가 실패\tperson_seq$person_seq,system_seq:$system_seq,roles_seq:$roles_seq")
            NotFoundSystemException()
        }.find { it.seq == roles_seq }

        if (findRol == null) {
            addLog(user.seq, "[INSERT]\t권한 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,roles_seq:$roles_seq")
            throw NotFoundRolesException()
        }

        Optional.ofNullable(rolesMapper.addPersonRoles(PersonRolesModel().apply {
            this.person_seq = person_seq
            system_roles_seq = findRol.seq
        })).orElseThrow { CommunicationException() }
        addLog(user.seq, "[INSERT]\t권한 추가\tperson_seq:$person_seq,system_seq:$system_seq,roles:${findRol.name}")
    }

    /**
     * 유저 권한들을 추가함
     * @param token 추가하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_system_list
     * @param roles 권한 이름 리스트 t_mhha_system_roles_list
     */
    fun addPersonRolesList(token: String, person_seq: Int, system_seq: Int, roles: MutableList<String>, isMhha: Boolean = false) {
        val user = getPerson(token)
        isAdmin(token)

        val findRol = Optional.ofNullable(rolesMapper.findSystemRolesBySystemSeq(system_seq)).orElseThrow {
            addLog(user.seq, "[INSERT]\t권한 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,roles:${roles.joinToString()}")
            NotFoundSystemException()
        }

        val rolList = getCanAddRolesList(person_seq, roles, findRol)
        if (rolList.size == 0) {
            addLog(user.seq, "[INSERT]\t권한 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,roles:${roles.joinToString()}")
            return
        }

        val data: MutableList<PersonRolesModel> = arrayListOf()
        rolList.forEach { x ->
            data.add(PersonRolesModel().apply {
                this.person_seq = person_seq
                system_roles_seq = x.seq
            })
        }
        Optional.ofNullable(rolesMapper.addPersonRolesList(getPersonRolesModel(person_seq, rolList))).orElseThrow { CommunicationException() }
        addLog(user.seq, "[INSERT]권한 추가\tperson_seq:$person_seq,system_seq:$system_seq,roles:${rolList.stream().map { it.name }.toArray().joinToString()}")
    }

    /**
     * 유저 권한들을 추가함
     * @param token 추가하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_system_list
     * @param roles_seq 권한 순번 리스트 t_mhha_system_roles_list
     */
    fun addPersonRolesSeqList(token: String, person_seq: Int, system_seq: Int, roles_seq: MutableList<Int>, isMhha: Boolean = false) {
        val user = getPerson(token)
        isAdmin(token)

        val findRol = Optional.ofNullable(rolesMapper.findSystemRolesBySystemSeq(system_seq)).orElseThrow {
            addLog(user.seq, "[INSERT]\t권한 추가 실패\tperson_seq:$person_seq,system_seq:$system_seq,roles_seq:${roles_seq.joinToString()}")
            NotFoundSystemException()
        }

        val rolList = getCanAddRolesListForSeq(person_seq, roles_seq, findRol)
        if (rolList.size == 0) {
            addLog(user.seq, "[INSERT]\t권한 추가 실패]\tperson_seq:$person_seq,system_seq:$system_seq,roles_seq:${roles_seq.joinToString()}")
            return
        }
        Optional.ofNullable(rolesMapper.addPersonRolesList(getPersonRolesModel(person_seq, rolList))).orElseThrow { CommunicationException() }
        addLog(user.seq, "[INSERT]\t권한 추가\tperson_seq:$person_seq,system_seq:$system_seq,roles:${rolList.stream().map { it.name }.toArray().joinToString()}")
    }

    /**
     * 유저 부서를 제거함
     * @param token 제거하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_system_list
     * @param dept 권한 이름 t_mhha_system_roles_list
     * @param isAll is 해당 시스템내 부서 전부 제거
     */
    fun delPersonDept(token: String, person_seq: Int, system_seq: Int, dept: String, isAll: Boolean, isMhha: Boolean = false) {
        val user = getPerson(token)
        isAdmin(token)

        if (isAll) {
            Optional.ofNullable(rolesMapper.delPersonDeptBySystemSeqAndPersonSeq(person_seq, system_seq)).orElseThrow { CommunicationException() }
            addLog(user.seq, "[DELETE]\t부서 삭제\tperson_seq:$person_seq,system_seq:$system_seq,dept:all")
        } else {
            val findRol = Optional.ofNullable(rolesMapper.findSystemRolesBySystemSeq(system_seq)).orElseThrow {
                addLog(user.seq, "[DELETE]\t부서 삭제 실패\tperson_seq:$person_seq,system_seq:$system_seq,dept:$dept")
                NotFoundSystemException()
            }.find {it.name == dept }

            if (findRol == null) {
                addLog(user.seq, "[DELETE]\t부서 삭제 실패\tperson_seq:$person_seq,system_seq:$system_seq,dept:$dept")
                throw NotFoundDeptException()
            }

            Optional.ofNullable(rolesMapper.delPersonDeptBySystemDeptSeqAndPersonSeq(person_seq, findRol.seq)).orElseThrow { CommunicationException() }
            addLog(user.seq, "[DELETE]\t부서 삭제\tperson_seq:$person_seq,system_seq:$system_seq,dept:$dept")
        }
    }

    /**
     * 유저 권한을 제거함
     * @param token 제거하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_system_list
     * @param dept_seq 권한 순번 t_mhha_system_roles_list
     * @param isAll is 해당 시스템내 부서 전부 제거
     */
    fun delPersonDeptSeq(token: String, person_seq: Int, system_seq: Int, dept_seq: Int, isAll: Boolean, isMhha: Boolean = false) {
        val user = getPerson(token)
        isAdmin(token)

        if (isAll) {
            Optional.ofNullable(rolesMapper.delPersonDeptBySystemSeqAndPersonSeq(person_seq, system_seq)).orElseThrow { CommunicationException() }
            addLog(user.seq, "[DELETE]\t부서 삭제\tperson_seq:$person_seq,system_seq:$system_seq,dept:all")
        } else {
            Optional.ofNullable(rolesMapper.delPersonDeptBySystemDeptSeqAndPersonSeq(person_seq, dept_seq)).orElseThrow { CommunicationException() }
            addLog(user.seq, "[DELETE]\t부서 삭제\tperson_seq:$person_seq,system_seq:$system_seq,dept_seq:$dept_seq")
        }
    }

    /**
     * 유저 권한을 제거함
     * @param token 제거하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_system_list
     * @param roles 권한 이름 t_mhha_system_roles_list
     * @param isAll is 해당 시스템내 권한 전부 제거
     */
    fun delPersonRoles(token: String, person_seq: Int, system_seq: Int, roles: String, isAll: Boolean, isMhha: Boolean = false) {
        val user = getPerson(token)
        isAdmin(token)

        if (isAll) {
            Optional.ofNullable(rolesMapper.delPersonRolesBySystemSeqAndPersonSeq(person_seq, system_seq)).orElseThrow { CommunicationException() }
            addLog(user.seq, "[DELETE]\t권한 삭제\tperson_seq:$person_seq,system_seq:$system_seq,roles:all")
        } else {
            val findRol = Optional.ofNullable(rolesMapper.findSystemRolesBySystemSeq(system_seq)).orElseThrow {
                addLog(user.seq, "[DELETE]\t권한 삭제 실패\tperson_seq:$person_seq,system_seq:$system_seq,roles:$roles")
                NotFoundSystemException()
            }.find {it.name == roles }

            if (findRol == null) {
                addLog(user.seq, "[DELETE]\t권한 삭제 실패\tperson_seq:$person_seq,system_seq:$system_seq,roles:$roles")
                throw NotFoundRolesException()
            }

            Optional.ofNullable(rolesMapper.delPersonRolesBySystemRolesSeqAndPersonSeq(person_seq, findRol.seq)).orElseThrow { CommunicationException() }
            addLog(user.seq, "[DELETE]\t권한 삭제\tperson_seq:$person_seq,system_seq:$system_seq:roles:$roles")
        }
    }

    /**
     * 유저 권한을 제거함
     * @param token 제거하는 사람
     * @param person_seq 제거 당하는 사람 : t_mhha_person 순번
     * @param system_seq 시스템 순번 t_mhha_system_list
     * @param roles_seq 권한 순번 t_mhha_system_roles_list
     * @param isAll is 해당 시스템내 권한 전부 제거
     */
    fun delPersonRolesSeq(token: String, person_seq: Int, system_seq: Int, roles_seq: Int, isAll: Boolean, isMhha: Boolean = false) {
        val user = getPerson(token)
        isAdmin(token)

        if (isAll) {
            Optional.ofNullable(rolesMapper.delPersonRolesBySystemSeqAndPersonSeq(person_seq, system_seq)).orElseThrow { CommunicationException() }
            addLog(user.seq, "[DELETE]\t권한 삭제\tperson_seq:$person_seq,system_seq:$system_seq,roles:all")
        } else {
            Optional.ofNullable(rolesMapper.delPersonRolesBySystemRolesSeqAndPersonSeq(person_seq, roles_seq)).orElseThrow { CommunicationException() }
            addLog(user.seq, "[DELETE]\t권한 삭제\tperson_seq:$person_seq,system_seq:$system_seq,roles_seq:$roles_seq")
        }
    }
    /**
     * 등록된 시스템 부서 전체 내역을 콜함.
     * @param token 로그인 토큰
     * @return 시스템 부서 리스트 SystemDeptListModel
     */
    fun getAllDeptList(token: String): List<SystemDeptListModel> {
        if (!jwtTokenProvider.validateToken(token)) {
            throw NotOwnerException()
        }

        return Optional.ofNullable(rolesMapper.findAllSystemDeptList()).orElseThrow { CommunicationException() }
    }

    /**
     * 등록된 시스템 권한 전체 내역을 콜함.
     * @param token 로그인 토큰
     * @return 시스템 권한 리스트 SystemRolesListModel
     */
    fun getAllRolesList(token: String): List<SystemRolesListModel> {
        if (!jwtTokenProvider.validateToken(token)) {
            throw NotOwnerException()
        }

        return Optional.ofNullable(rolesMapper.findAllSystemRolesList()).orElseThrow { CommunicationException() }
    }

    fun findPersonRolesCompFromRolesByPersonSeq(person_seq: Int): List<PersonRolesCompModel> {
        return Optional.ofNullable(rolesMapper.findPersonRolesCompFromRolesByPersonSeq(person_seq)).orElse(arrayListOf())
    }
    fun findPersonRolesCompFromDeptByPersonSeq(person_seq: Int): List<PersonRolesCompModel> {
        return Optional.ofNullable(rolesMapper.findPersonRolesCompFromDeptByPersonSeq(person_seq)).orElse(arrayListOf())
    }
    fun findAllSystemList(token: String): List<SystemListModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findAllSystemList()).orElse(arrayListOf())
    }
    fun findAllPersonDept(token: String): List<PersonDeptModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findAllPersonDept()).orElse(arrayListOf())
    }
    fun findPersonDeptBySystemSeq(token: String, system_seq: Int): List<PersonDeptModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonDeptBySystemSeq(system_seq)).orElse(arrayListOf())
    }
    fun findPersonDeptBySystemSeqAndPersonSeq(token: String, system_seq: Int, person_seq: Int): List<PersonDeptModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonDeptBySystemSeqAndPersonSeq(system_seq, person_seq)).orElse(arrayListOf())
    }
    fun findPersonDeptStringByPersonSeq(token: String, person_seq: Int): List<String> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonDeptStringByPersonSeq(person_seq)).orElse(arrayListOf())
    }
    fun findPersonDeptStringBySystemSeqAndPersonSeq(token: String, system_seq: Int, person_seq: Int): List<String> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonDeptStringBySystemSeqAndPersonSeq(system_seq, person_seq)).orElse(arrayListOf())
    }
    fun findAllPersonRoles(token: String): List<PersonRolesModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findAllPersonRoles()).orElse(arrayListOf())
    }
    fun findPersonRolesBySystemSeq(token: String, system_seq: Int): List<PersonRolesModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonRolesBySystemSeq(system_seq)).orElse(arrayListOf())
    }
    fun findPersonRolesBySystemSeqAndPersonSeq(token: String, system_seq: Int, person_seq: Int): List<PersonRolesModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonRolesBySystemSeqAndPersonSeq(system_seq, person_seq)).orElse(arrayListOf())
    }
    fun findPersonRolesStringByPersonSeq(token: String, person_seq: Int): List<String> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonRolesStringByPersonSeq(person_seq)).orElse(arrayListOf())
    }
    fun findPersonRolesStringBySystemSeqAndPersonSeq(token: String, system_seq: Int, person_seq: Int): List<String> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonRolesStringBySystemSeqAndPersonSeq(system_seq, person_seq)).orElse(arrayListOf())
    }
    fun findPersonRolesCompFromDept(token: String): List<PersonRolesCompModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonRolesCompFromDept()).orElse(arrayListOf())
    }
    fun findPersonRolesCompFromDeptBySystemSeq(token: String, system_seq: Int): List<PersonRolesCompModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonRolesCompFromDeptBySystemSeq(system_seq)).orElse(arrayListOf())
    }
    fun findPersonRolesCompFromDeptBySystemSeqAndPersonSeq(token: String, system_seq: Int, person_seq: Int): List<PersonRolesCompModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonRolesCompFromDeptBySystemSeqAndPersonSeq(system_seq, person_seq)).orElse(arrayListOf())
    }
    fun findPersonRolesCompFromRoles(token: String): List<PersonRolesCompModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonRolesCompFromRoles()).orElse(arrayListOf())
    }
    fun findPersonRolesCompFromRolesBySystemSeq(token: String, system_seq: Int): List<PersonRolesCompModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonRolesCompFromRolesBySystemSeq(system_seq)).orElse(arrayListOf())
    }
    fun findPersonRolesCompFromRolesBySystemSeqAndPersonSeq(token: String, system_seq: Int, person_seq: Int): List<PersonRolesCompModel> {
        isAdmin(token)
        return Optional.ofNullable(rolesMapper.findPersonRolesCompFromRolesBySystemSeqAndPersonSeq(system_seq, person_seq)).orElse(arrayListOf())
    }

    fun isAdmin(token: String, notAdminThrow: Boolean = true): Boolean {
        val user = jwtTokenProvider.getPersonData(token)
        return if (SystemRolesListModel.tryFind(user.rolesList(), SystemRolesListModel.SystemRolesListEnum.PORTAL_ADMIN)) {
            true
        } else if (notAdminThrow) {
            throw NotOwnerException()
        } else {
            false
        }
    }
    fun isValid(token: String, notValidThrow: Boolean = true): Boolean {
        if (!jwtTokenProvider.validateToken(token)) {
            return if (notValidThrow) {
                throw NotOwnerException()
            } else {
                false
            }
        }

        return true
    }
    fun getPerson(token: String): PersonModel {
        return jwtTokenProvider.getPersonData(token)
    }


    private fun getCanAddDeptList(person_seq: Int, dept: List<String>, findDept: List<SystemDeptListModel>): MutableList<SystemDeptListModel> {
        val deptList: MutableList<SystemDeptListModel> = arrayListOf()
        dept.distinct().forEach { x ->
            val temp = findDept.find { it.name == x }
            if (temp != null) {
                deptList.add(temp)
            }
        }

        Optional.ofNullable(rolesMapper.findPersonDeptByPersonSeq(person_seq)).orElseThrow().forEach { x ->
            if (deptList.size == 0) {
                return deptList
            }

            val temp = deptList.find { it.seq == x.system_dept_seq }
            if (temp != null) {
                deptList.remove(temp)
            }
        }

        return deptList
    }
    private fun getCanAddDeptListForSeq(person_seq: Int, dept_seq: List<Int>, findDept: List<SystemDeptListModel>): MutableList<SystemDeptListModel> {
        val deptList: MutableList<SystemDeptListModel> = arrayListOf()
        dept_seq.distinct().forEach { x ->
            val temp = findDept.find { it.seq == x }
            if (temp != null) {
                deptList.add(temp)
            }
        }

        Optional.ofNullable(rolesMapper.findPersonDeptByPersonSeq(person_seq)).orElseThrow().forEach { x ->
            if (deptList.size == 0) {
                return deptList
            }

            val temp = deptList.find { it.seq == x.system_dept_seq }
            if (temp != null) {
                deptList.remove(temp)
            }
        }

        return deptList
    }
    private fun getCanAddRolesList(person_seq: Int, roles: List<String>, findRoles: List<SystemRolesListModel>): MutableList<SystemRolesListModel> {
        val rolesList: MutableList<SystemRolesListModel> = arrayListOf()
        roles.distinct().forEach { x ->
            val temp = findRoles.find { it.name == x }
            if (temp != null) {
                rolesList.add(temp)
            }
        }

        Optional.ofNullable(rolesMapper.findPersonRolesByPersonSeq(person_seq)).orElseThrow().forEach { x ->
            if (rolesList.size == 0) {
                return rolesList
            }

            val temp = rolesList.find { it.seq == x.system_roles_seq }
            if (temp != null) {
                rolesList.remove(temp)
            }
        }

        return rolesList
    }
    private fun getCanAddRolesListForSeq(person_seq: Int, roles_seq: List<Int>, findRoles: List<SystemRolesListModel>): MutableList<SystemRolesListModel> {
        val deptList: MutableList<SystemRolesListModel> = arrayListOf()
        roles_seq.distinct().forEach { x ->
            val temp = findRoles.find { it.seq == x }
            if (temp != null) {
                deptList.add(temp)
            }
        }

        Optional.ofNullable(rolesMapper.findPersonRolesByPersonSeq(person_seq)).orElseThrow().forEach { x ->
            if (deptList.size == 0) {
                return deptList
            }

            val temp = deptList.find { it.seq == x.system_roles_seq }
            if (temp != null) {
                deptList.remove(temp)
            }
        }

        return deptList
    }
    private fun getPersonDeptModel(person_seq: Int, deptList: List<SystemDeptListModel>): List<PersonDeptModel> {
        val data: MutableList<PersonDeptModel> = arrayListOf()
        deptList.forEach { x ->
            data.add(PersonDeptModel().apply {
                this.person_seq = person_seq
                system_dept_seq = x.seq
            })
        }

        return data
    }
    private fun getPersonRolesModel(person_seq: Int, rolesList: List<SystemRolesListModel>): List<PersonRolesModel> {
        val data: MutableList<PersonRolesModel> = arrayListOf()
        rolesList.forEach { x ->
            data.add(PersonRolesModel().apply {
                this.person_seq = person_seq
                system_roles_seq = x.seq
            })
        }

        return data
    }
    private fun addLog(userid: Int, logFormat: String) {
        logMapper.addLog(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), userid, logFormat)
    }
}
