package io.hs.anohi.chat.infra

import io.hs.anohi.chat.domain.ChatRoom
import io.hs.anohi.chat.domain.ChatRoomRepository
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomJpaRepository: ChatRoomRepository, JpaRepository<ChatRoom, Long> {

}