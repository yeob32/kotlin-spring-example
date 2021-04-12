package com.example.demo.global.error

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String
) {
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    INTERNAL_SERVER_ERROR(500, "C001", "Server Error"),

    // Order
    NOT_FOUND_ORDER(404, "O001", "Not Found Order"),

    // Member
    NOT_FOUND_MEMBER(404, "M001", "Not Found Member")

    ;
}
