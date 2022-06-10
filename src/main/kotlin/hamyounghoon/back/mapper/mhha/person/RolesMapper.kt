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
     * @return List PersonDeptModel
     */
    fun findAllPersonDept(): List<PersonDeptModel>

    /**
     * select 유저별 부서 목록
     * @param system_seq 시스템 순번
     * @return List PersonDeptModel
     */
    fun findPersonDeptBySystemSeq(@Param(value = "system_seq") system_seq: Int): List<PersonDeptModel>

    /**
     * select 유저별 부서 목록
     * @param person_seq t_mhha_person 순번
     * @return List PersonDeptModel
     */
    fun findPersonDeptByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<PersonDeptModel>

    /**
     * select 유저별 부서 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List PersonDeptModel
     */
    fun findPersonDeptBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<PersonDeptModel>

    /**
     * select 유저별 부서 목록
     * @param person_seq t_mhha_person 순번
     * @return List String
     */
    fun findPersonDeptStringByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<String>

    /**
     * select 유저별 부서 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List String
     */
    fun findPersonDeptStringBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<String>

    /**
     * select 유저별 권한 목록
     * @return List PersonRolesModel
     */
    fun findAllPersonRoles(): List<PersonRolesModel>

    /**
     * select 유저별 권한 목록
     * @param system_seq 시스템 순번
     * @return List PersonRolesModel
     */
    fun findPersonRolesBySystemSeq(@Param(value = "system_seq") system_seq: Int): List<PersonRolesModel>

    /**
     * select 유저별 권한 목록
     * @param person_seq t_mhha_person 순번
     * @return List PersonRolesModel
     */
    fun findPersonRolesByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<PersonRolesModel>

    /**
     * select 유저별 권한 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List PersonRolesModel
     */
    fun findPersonRolesBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<PersonRolesModel>

    /**
     * select 유저별 권한 목록
     * @param person_seq t_mhha_person 순번
     * @return List String
     */
    fun findPersonRolesStringByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<String>

    /**
     * select 유저별 권한 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List String
     */
    fun findPersonRolesStringBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<String>

    /**
     * select 유저별 부서 목록
     * @return List PersonRolesCompModel
     */
    fun findPersonRolesCompFromDept(): List<PersonRolesCompModel>

    /**
     * select 유저별 부서 목록
     * @param person_seq 그룹웨어 유저 아이디
     * @return List PersonRolesCompModel
     */
    fun findPersonRolesCompFromDeptByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<PersonRolesCompModel>

    /**
     * select 유저별 부서 목록
     * @param system_seq 시스템 순번
     * @return List PersonRolesCompModel
     */
    fun findPersonRolesCompFromDeptBySystemSeq(@Param(value = "system_seq") system_seq: Int): List<PersonRolesCompModel>

    /**
     * select 유저별 부서 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List PersonRolesCompModel
     */
    fun findPersonRolesCompFromDeptBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<PersonRolesCompModel>

    /**
     * select 유저별 권한 목록
     * @return List PersonRolesCompModel
     */
    fun findPersonRolesCompFromRoles(): List<PersonRolesCompModel>

    /**
     * select 유저별 권한 목록
     * @param person_seq t_mhha_person 순번
     * @return List PersonRolesCompModel
     */
    fun findPersonRolesCompFromRolesByPersonSeq(@Param(value = "person_seq") person_seq: Int): List<PersonRolesCompModel>

    /**
     * select 유저별 권한 목록
     * @param system_seq 시스템 이름
     * @return List PersonRolesCompModel
     */
    fun findPersonRolesCompFromRolesBySystemSeq(@Param(value = "system_seq") system_seq: Int): List<PersonRolesCompModel>

    /**
     * select 유저별 권한 목록
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     * @return List PersonRolesCompModel
     */
    fun findPersonRolesCompFromRolesBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int): List<PersonRolesCompModel>

    /**
     * insert 유저 부서 데이터
     * @param person_dept PersonDeptModel
     */
    fun addPersonDept(@Param(value = "person_dept") person_dept: PersonDeptModel)

    /**
     * isnert 유저 부서 데이터
     * @param person_dept List PersonDeptModel
     */
    fun addPersonDeptList(@Param(value = "Person_dept") person_dept: List<PersonDeptModel>)
    /**
     * delete 유저 부서 데이터
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     */
    fun delPersonDeptBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int)

    /**
     * delete 유저 부서 데이터
     * @param system_dept_seq 부서 순번
     * @param person_seq t_mhha_person 순번
     */
    fun delPersonDeptBySystemDeptSeqAndPersonSeq(@Param(value = "system_dept_seq") system_dept_seq: Int, @Param(value = "person_seq") person_seq: Int)

    /**
     * insert 유저 권한 데이터
     * @param person_roles PersonRolesModel
     */
    fun addPersonRoles(@Param(value = "person_roles") person_roles: PersonRolesModel)

    /**
     * insert 유저 권한 데이터
     * @param person_roles List PersonRolesModel
     */
    fun addPersonRolesList(@Param(value = "person_roles") person_roles: List<PersonRolesModel>)
    /**
     * delete 유저 권한 데이터
     * @param system_seq 시스템 순번
     * @param person_seq t_mhha_person 순번
     */
    fun delPersonRolesBySystemSeqAndPersonSeq(@Param(value = "system_seq") system_seq: Int, @Param(value = "person_seq") person_seq: Int)

    /**
     * delete 유저 권한 데이터
     * @param system_roles_seq 권한 순번
     * @param person_seq t_mhha_person 순번
     */
    fun delPersonRolesBySystemRolesSeqAndPersonSeq(@Param(value = "system_roles_seq") system_roles_seq: Int, @Param(value = "person_seq") person_seq: Int)
}
