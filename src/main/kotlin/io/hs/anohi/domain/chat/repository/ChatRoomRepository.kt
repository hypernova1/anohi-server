package io.hs.anohi.domain.chat.repository

import io.hs.anohi.domain.chat.entity.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository: JpaRepository<ChatRoom, Long> {

}