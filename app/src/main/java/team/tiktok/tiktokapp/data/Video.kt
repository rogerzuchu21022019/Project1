package team.tiktok.tiktokapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    ///basic info
    var email: String? = null,
    var fullName: String? = null,
    var topTopID: String? = null,
    var password: String? = null,
    var uuid: String? = null,
    var follower: Follower? = null,
    var following: Following? = null,
    var hearts: Int = 0,
    var favorites: Int = 0,
    var imgUrl: String? = null,
    var birthDay: String? = null,
    var subscribe: Boolean? = false,
    ///update
    var phone: String? = null,
    var profileUrl: String? = null,
    var videos: Video? = null,
    var urlFollower: String? = null,
    var urlFollowing: String? = null,
    var createAt: String? = null,
    ) : Parcelable

@Parcelize
data class Comment(
    var uidComment: String? = "",
    var message: String? = "",
    var fullName: String? = "",
    var users: User ?= null,
    var createAt: String? = "",
    var updateAt: String? = "",
    var countComments: Int? = 0,
    var hearts: Int? = 0,
    var videos: Video? = null
) : Parcelable
@Parcelize
data class Favorite(
    var heart: Boolean? = false,
    var users: User?= null,
    var videos: Video? = null
) : Parcelable

@Parcelize
data class Video(
    var uidVideo: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var createAt: String? = null,
    var updateAt: String? = null,
    var user: User? = null,
    var countShare:Int? = 0,
    var countSaved:Int? = 0,
    var countHearts:Int? = 0,
    var comments:Comment? = null,
    var countComments: Int? = 0
) : Parcelable{
    constructor() : this(uidVideo = "", title = "", description = "", url = "", createAt = "", updateAt = "", user = null)
    constructor(uidVideo: String?,title: String?,description: String?,url: String?,createAt: String?,updateAt: String?) : this(uidVideo = uidVideo, title = title, description = description, url = url, createAt = createAt, updateAt = updateAt, user = null)
}


@Parcelize
data class Follower(
    var subscribe:Boolean? = false,
    var uid: String? = "",
    var countFollowers: Int? = 0,
    var users: User? = null
) : Parcelable

@Parcelize
data class Following(
    var uid: String? = "",
    var countFollowings: Int? = 0,
    var users: User? = null
) : Parcelable


//@Parcelize
//data class User(
//    ///basic info
//    var email: String? = "",
//    var fullName: String? = "",
//    var topTopID: String? = "",
//    var password: String? = "",
//    var uuid: String?="" ,
//    var follower: Follower?=null,
//    var following: Following?=null,
//    var hearts: Int? = 0,
//    var favorites: Int? = 0,
//    var imgUrl: String? = "",
//    var birthDay: String? = "",
//    ///update
//    var phone: String? = "",
//    var profileUrl: String? = "",
//    var comment: Comment?=null,
//    var video: Video?=null,
//    var urlFollower: String? = "",
//    var urlFollowing: String? = ""
//) : Parcelable
//
//@Parcelize
//data class Comment(
//    val idComment: String?="",
//    val message: String?="",
//    val user: User?=null,
//    val updateAt: String?="",
//    val countComments: Int?=0,
//    val hearts: Int?=0,
//
//    ) : Parcelable
//
//@Parcelize
//data class Video(
//    var title: String? = "",
//    var description: String? = "",
//    var url: String? = "",
//    val createAt: String? = "",
//    val updateAt: String? = "",
//    val user: User?
//) : Parcelable
//
//@Parcelize
//
//data class Heart(
//    var id: Int? = 0,
//    var countHearts: Int? = 0,
//    val createAt: String? = "",
//    val updateAt: String? = ""
//
//) : Parcelable
//
//@Parcelize
//data class Follower(
//    var uid: String?="",
//    var countFollowers: Int? = 0,
//) : Parcelable
//
//@Parcelize
//data class Following(
//    var uid: String?="",
//    var countFollowings: Int? = 0,
//) : Parcelable
