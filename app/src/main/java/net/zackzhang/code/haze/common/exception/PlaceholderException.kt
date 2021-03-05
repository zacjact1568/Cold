package net.zackzhang.code.haze.common.exception

/** 还没决定好 exception 的名字 */
class PlaceholderException(code: Int, info: String) : Exception("This a placeholder exception with ($code: $info)")