package kt.module.module_base.data.bean

class BaseEvent<T> {
    var code: Int = 0
    var data: T? = null

    constructor()

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, data: T?) {
        this.code = code
        this.data = data
    }

}