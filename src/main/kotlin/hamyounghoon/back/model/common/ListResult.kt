package hamyounghoon.back.model.common

import io.swagger.v3.oas.annotations.media.Schema

class ListResult<T>: CommonResult() {
    @field:Schema(description = "response List T Type Data") var data: List<T>? = null
}
