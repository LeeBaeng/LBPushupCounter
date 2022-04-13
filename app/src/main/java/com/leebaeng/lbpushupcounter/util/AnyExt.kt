package com.leebaeng.lbpushupcounter.util

inline fun <T:Any> runNotNull(vararg args: Any?, block : ()->T) {
    args.forEach {
        if(it == null) return
    }
    block()
}

inline fun <T, R> T.runIf(condition: Boolean, block: (T)->R): R?{
    if(condition) return block(this)
    return null
}

inline fun <T, R> T.runIfWithFalse(condition: Boolean, trueBlock: (T)->R, falseBlock: (T)->R): R?{
    return if(condition) trueBlock(this)
    else falseBlock(this)
}
