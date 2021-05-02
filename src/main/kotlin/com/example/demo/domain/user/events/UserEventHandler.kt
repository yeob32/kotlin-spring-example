package com.example.demo.domain.user.events

import com.example.demo.domain.user.User
import com.example.demo.domain.user.UserHistory
import com.example.demo.domain.user.UserHistoryRepository
import com.example.demo.logger.Log
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import org.springframework.transaction.support.TransactionSynchronizationManager

@Component
class UserEventHandler(
    private val userHistoryRepository: UserHistoryRepository
) {
    companion object: Log

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun appendUserHistory(userEvent: UserEvent) {
        logger.info("listen appendUserHistory {} !!!", TransactionSynchronizationManager.getCurrentTransactionName())
        userHistoryRepository.save(userEvent.user.toHistory())
    }

    private fun User.toHistory() = UserHistory(
        user = this,
        email = email.value,
        name = name,
        password = password
    )
}