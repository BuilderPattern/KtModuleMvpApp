package kt.module.base_module.data

import android.os.Parcel
import android.os.Parcelable

class RvData : Parcelable {
    var name: String? = null
    var age: Int = 0

    constructor() {}

    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }

    override fun toString(): String {
        return "RvData{" +
                "name='" + name + '\''.toString() +
                ", age=" + age +
                '}'.toString()
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        age = `in`.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeInt(age)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<RvData> = object : Parcelable.Creator<RvData> {
            override fun createFromParcel(`in`: Parcel): RvData {
                return RvData(`in`)
            }

            override fun newArray(size: Int): Array<RvData?> {
                return arrayOfNulls(size)
            }
        }
    }
}