package hamyounghoon.back.advice.exception

class NotFoundUserException: RuntimeException {
    constructor(msg : String, t : Throwable): super(msg, t)
    constructor(msg : String): super(msg)
    constructor(): super()
}
