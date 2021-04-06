package com.example.demo.domain.company.extension

import com.example.demo.domain.company.Company

/**
 * Lazy Load 를 수행하기 위해 메소드를 별도로 생성
 */
internal fun Iterable<Company>.extractEmployeeNames() = map { it.employees[0].name }