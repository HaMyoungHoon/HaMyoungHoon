package hamyounghoon.back.mapper.mhha.person

import hamyounghoon.back.model.mhha.person.PersonRolesCompModel
import hamyounghoon.back.model.mhha.roles.*
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface RolesMapper {
    /**
     * select 시스템 이름 목록
     * @return List SystemListModel
     */
    fun findAllSystemList(): List<SystemListModel>

    /**
     * select 시스템 이름 별 부서 목록
     * @return List SystemDeptListModel
     */
    fun findAllSystemDeptList(): List<SystemDeptListModel>

    /**
     * select 시스템 이름 별 부서 목록
     * @param system_seq 시스템 순번
     * @return List SystemDeptListModel
     */
    fun findSystemDeptBySystemSeq(@Param(value = "system_seq") system_seq: Int): List<SystemDeptListModel>

    /**
     * select 시스템 이름 별 권한 목록
     * @return List SystemRolesListModel
     */
    fun findAllSystemRolesList(): List<SystemRolesListModel>

    /**
     * select 시스템 이름 별 권한 목록
     * @param system_seq 시스템 순번
     * @return List SystemRolesListModel
     */
    fun findSystemRolesBySystemSeq(@Param(value = "system_seq") system_seq: Int): List<SystemRolesListModel>

    /**
     * select 유저별 부서 목록
     * @return List UserDeptModel
     */
    fun findAllUserDept(): List<PersonDeptModel>

    /**
     * select 유저별 부서 목록
     * @param system_seq 시스템 순번
     * @return List UserDeptModel
     */
    fun findUserDeptBySystemSeq(@Param(value = "system_seq") system_seq: Int): List<PersonDeptModel>

    /**
     * select 유저별 부서 목록
     * @param person_seq t_mhha_person 순번
     * @return List UserDeptModel
     */
    fun findUserDeptByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<PersonDeptModel>

    /**
     * select 유저별 부서 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List UserDeptModel
     */
    fun findUserDeptBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<PersonDeptModel>

    /**
     * select 유저별 부서 목록
     * @param person_seq t_mhha_person 순번
     * @return List String
     */
    fun findUserDeptStringByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<String>

    /**
     * select 유저별 부서 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List String
     */
    fun findUserDeptStringBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<String>

    /**
     * select 유저별 권한 목록
     * @return List UserRolesModel
     */
    fun findAllUserRoles(): List<PersonRolesModel>

    /**
     * select 유저별 권한 목록
     * @param system_seq 시스템 순번
     * @return List UserRolesModel
     */
    fun findUserRolesBySystemSeq(@Param(value = "system_seq") system_seq: Int): List<PersonRolesModel>

    /**
     * select 유저별 권한 목록
     * @param person_seq t_mhha_person 순번
     * @return List UserRolesModel
     */
    fun findUserRolesByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<PersonRolesModel>

    /**
     * select 유저별 권한 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List UserRolesModel
     */
    fun findUserRolesBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<PersonRolesModel>

    /**
     * select 유저별 권한 목록
     * @param person_seq t_mhha_person 순번
     * @return List String
     */
    fun findUserRolesStringByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<String>

    /**
     * select 유저별 권한 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List String
     */
    fun findUserRolesStringBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<String>

    /**
     * select 유저별 부서 목록
     * @return List UserRolesCompModel
     */
    fun findUserRolesCompFromDept(): List<PersonRolesCompModel>

    /**
     * select 유저별 부서 목록
     * @param person_seq 그룹웨어 유저 아이디
     * @return List UserRolesCompModel
     */
    fun findUserRolesCompFromDeptByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<PersonRolesCompModel>

    /**
     * select 유저별 부서 목록
     * @param system_seq 시스템 순번
     * @return List UserRolesCompModel
     */
    fun findUserRolesCompFromDeptBySystemSeq(@Param(value = "system_seq") system_seq: Int): List<PersonRolesCompModel>

    /**
     * select 유저별 부서 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List UserRolesCompModel
     */
    fun findUserRolesCompFromDeptBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<PersonRolesCompModel>

    /**
     * select 유저별 권한 목록
     * @return List UserRolesCompModel
     */
    fun findUserRolesCompFromRoles(): List<PersonRolesCompModel>

    /**
     * select 유저별 권한 목록
     * @param person_seq t_mhha_person 순번
     * @return List UserRolesCompModel
     */
    fun findUserRolesCompFromRolesByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<PersonRolesCompModel>

    /**
     * select 유저별 권한 목록
     * @param system_seq 시스템 이름
     * @return List UserRolesCompModel
     */
    fun findUserRolesCompFromRolesBySystemSeq(@Param(value = "system_seq") system_seq: Int): List<PersonRolesCompModel>

    /**
     * select 유저별 권한 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List UserRolesCompModel
     */
    fun findUserRolesCompFromRolesBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<PersonRolesCompModel>

    /**
     * insert 유저 부서 데이터
     * @param person_dept PersonDeptModel
     */
    fun addUserDept(@Param(value = "person_dept") person_dept: PersonDeptModel)

    /**
     * isnert 유저 부서 데이터
     * @param person_dept List PersonDeptModel
     */
    fun addUserDeptList(@Param(value = "user_dept") person_dept: List<PersonDeptModel>)
    /**
     * delete 유저 부서 데이터
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     */
    fun delUserDeptBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int)

    /**
     * delete 유저 부서 데이터
     * @param system_dept_seq 부서 순번
     * @param person_seq t_mhha_person 순번
     */
    fun delUserDeptBySystemDeptSeqAndPersonSeq(@Param(value = "system_dept_seq") system_dept_seq: Int, @Param(value = "person_seq") person_seq: Int)

    /**
     * insert 유저 권한 데이터
     * @param person_roles PersonRolesModel
     */
    fun addUserRoles(@Param(value = "person_roles") person_roles: PersonRolesModel)

    /**
     * insert 유저 권한 데이터
     * @param person_roles List PersonRolesModel
     */
    fun addUserRolesList(@Param(value = "person_roles") person_roles: List<PersonRolesModel>)
    /**
     * delete 유저 권한 데이터
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     */
    fun delUserRolesBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int)

    /**
     * delete 유저 권한 데이터
     * @param system_roles_seq 권한 순번
     * @param person_seq t_mhha_person 순번
     */
    fun delUserRolesBySystemRolesSeqAndPersonSeq(@Param(value = "system_roles_seq") system_roles_seq: Int, @Param(value = "person_seq") person_seq: Int)
}
