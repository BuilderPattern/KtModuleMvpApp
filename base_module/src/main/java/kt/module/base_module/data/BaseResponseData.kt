package kt.module.base_module.data

class BaseResponseData<T> {
    var code: Int = 0
    var msg: String? = null
    var data: String? = null
    var result: T? = null
}