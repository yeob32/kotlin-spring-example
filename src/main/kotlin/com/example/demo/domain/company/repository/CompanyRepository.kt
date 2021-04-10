package com.example.demo.domain.company.repository

import com.example.demo.domain.company.Company
import com.example.demo.domain.company.custom.CustomCompanyRepository
import com.example.demo.domain.company.dto.CompanyReqDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CompanyRepository : JpaRepository<Company, Long>, JpaSpecificationExecutor<Company>, CustomCompanyRepository {

    fun findAllByAddressEquals(address: String, pageable: Pageable): Page<Company>
    fun findAllByAddressLike(address: String, pageable: Pageable): Page<Company>

    @Query(
        """
       SELECT c 
        FROM Company c 
        WHERE c.name like :#{#dto.name} 
        AND c.email = :#{#dto.email} 
        AND c.address = :#{#dto.address}
        AND c.phone = :#{#dto.phone}
    """
    )
    fun findAllBySearchContext(@Param("dto") dto: CompanyReqDto, pageable: Pageable): Page<Company>
}