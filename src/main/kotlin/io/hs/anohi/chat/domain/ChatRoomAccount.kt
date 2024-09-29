package io.hs.anohi.chat.domain

import jakarta.persistence.*

@Table(name = "chat_room_account", indexes = [
    Index(name = "chat_room_account_chat_room_id_idx", columnList = "chat_room_id"),
    Index(name = "chat_room_account_account_id_idx", columnList = "account_id"),
])
@Entity
class ChatRoomAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "chat_room_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val chatRoom: ChatRoom,

    @Column(name = "account_id", columnDefinition = "bigint", nullable = false)
    val accountId: Long,
)