/*
 *  Copyright (c) 2022~2024 chr_56
 */

package tools.release.upload

sealed class ResponseResult<T>(val code: Int) {
    class OK<T>(code: Int, val data: T) : ResponseResult<T>(code)
    class Failed<T>(code: Int) : ResponseResult<T>(code)
    class Error<T> : ResponseResult<T>(0)
}