package hamyounghoon.back.model.common

import io.swagger.v3.oas.annotations.media.Schema

class SingleResult<T>: CommonResult() {
    @field:Schema(description = "response T Type Data") var data: T? = null
}
