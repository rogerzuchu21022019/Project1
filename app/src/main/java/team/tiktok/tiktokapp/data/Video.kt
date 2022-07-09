package team.tiktok.tiktokapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    ///basic info
    var email: String? = "",
    var fullName: String? = "",
    var topTopID: String? = "",
    var password: String? = "",
    var uuid: String = "",
    var follower: Int? =0,
    var following: Following?,
    var hearts: Int? = 0,
    var favorites: Int? = 0,
    var imgUrl: String? = "",
    var birthDay: String? = "",
    ///update
    var phone: String? = "",
    var profileUrl: String? = "",
    var comment: List<Comment>?,
    var videos: List<Video>?,
    var urlFollower: String? = "",
    var urlFollowing: String? = ""
) : Parcelable




@Parcelize
data class Comment(
    val uidComment: String? = "",
    val message: String? = "",
    val fullName: String? = "",
    val user: User? = null,
    val updateAt: String? = "",
    val countComments: Int? = 0,
    val hearts: Int? = 0,
) : Parcelable

@Parcelize
data class Video(
    var uidVideo:String?="",
    var title: String? = "",
    var description: String? = "",
    var url: String? = "",
    val createAt: String? = "",
    val updateAt: String? = "",
) : Parcelable

@Parcelize

data class Heart(
    var id: Int? = 0,
    var countHearts: Int? = 0,
    val createAt: String? = "",
    val updateAt: String? = ""

) : Parcelable

@Parcelize
data class Follower(
    var uid: String? = "",
    var countFollowers: Int? = 0,
) : Parcelable

@Parcelize
data class Following(
    var uid: String? = "",
    var countFollowings: Int? = 0,
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
