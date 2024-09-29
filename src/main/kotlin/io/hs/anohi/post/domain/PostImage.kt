package io.hs.anohi.post.domain

import io.hs.anohi.image.Image
import jakarta.persistence.*

@Table(name = "post_image", indexes = [Index(name = "idx_post_id_image_id_idx", columnList = "post_id, image_id")])
@Entity
class PostImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "post_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val post: Post,

    @ManyToOne
    @JoinColumn(name = "image_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val image: Image,
)