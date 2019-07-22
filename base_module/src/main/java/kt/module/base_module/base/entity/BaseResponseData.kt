package kt.module.base_module.base.entity

class BaseResponseData<T> {
    var code: Int = 0
    var msg: String? = null
    var data: String? = null
    var result: T? = null
}