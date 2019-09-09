package kt.module.module_base.data.bean

class BaseResponseData<T>(var code: Int, var msg: String, var data: String, var result: T) {
}