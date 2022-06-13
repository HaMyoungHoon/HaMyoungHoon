package hamyounghoon.back.mapper.mhha.forecast

import hamyounghoon.back.model.mhha.forecast.ProjectNamesModel
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface ProjectNamesMapper {
    fun findProjectNamesBySeq(@Param(value = "seq") seq: Int): ProjectNamesModel
    fun getProjectNamesList(): List<ProjectNamesModel>
    fun addProjectNames(@Param(value = "name") name: String, @Param(value = "customer") customer: String, @Param(value = "market_field") marketField: String)
}
