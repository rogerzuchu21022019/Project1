package team.tiktok.tiktokapp.data

data class User (
    ///basic info
    var email: String? = "",
    var fullName: String?="",
    var topTopID: String?="",
    var password: String?="",
    var uuid: String?="",
    var follower:Follower,
    var following:Following,
    var hearts: Int = 0,
    var favorites: Int = 0,
    var imgUrl: String?="",
    var birthDay:String?="",
    ///update
    var phone: String?="",
    var profileUrl: String?="",
    var comment:Comment,
    var video:Video,
    var urlFollower:String?="",
    var urlFollowing:String?=""
)



data class Comment(
    val idComment: String,
    val message: String,
    val fullName: String,
    val user:User,
    val updateAt: String,
    val countComments: Double,
    val hearts: Heart,

    )
data class Video(
    var title:String?="",
    var description:String?="",
    var url:String?="",
    val createAt: String?="",
    val updateAt: String?="",
)
data class Heart(
    var id:Int =0,
    var countHearts:Int?=0,
    val createAt: String?="",
    val updateAt: String?=""

)
data class Follower(
    var id:Int?=0,
    var countFollowers:Int?=0,


    )
data class Following(
    var id:Int?=0,
    var countFollowings:Int?=0,
)
