package kt.module.base_module.base.entity

class BaseResponseData<T>(var code: Int, var msg: String, var data: String, var result: T) {
}