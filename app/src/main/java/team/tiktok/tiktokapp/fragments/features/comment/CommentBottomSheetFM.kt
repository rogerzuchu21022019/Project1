package team.tiktok.tiktokapp.fragments.features.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import team.tiktok.tiktokapp.R
import team.tiktok.tiktokapp.adapter.comment.CommentsAdapter
import team.tiktok.tiktokapp.data.Comment
import team.tiktok.tiktokapp.data.User
import team.tiktok.tiktokapp.data.Video
import team.tiktok.tiktokapp.databinding.FragmentCommentBottomSheetBinding

class CommentBottomSheetFM : BottomSheetDialogFragment(), View.OnClickListener {
    lateinit var binding: FragmentCommentBottomSheetBinding
    lateinit var navController: NavController
    lateinit var adapter: CommentsAdapter
    val navArgs: CommentBottomSheetFMArgs by navArgs()
    lateinit var dbComment: DatabaseReference
    lateinit var dbVideos: DatabaseReference
    val auth = Firebase.auth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBottomSheetBinding.inflate(layoutInflater)
//        clickButton()
        getVideo()
        initClick()
        initRecyclerView()
        return binding.root
    }

    fun getVideo(): Video {
        return navArgs.video
    }


    private fun initRecyclerView() {
        val listComment = mutableListOf<Comment>()
        /// TODO: Get video with dbVideos
        dbVideos = Firebase.database.getReference("videos").child(getVideo().uidVideo!!)
        dbVideos.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val video = snapshot.getValue(Video::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        /// TODO: Create recyclerview
        adapter = CommentsAdapter()
        binding.rvComment.adapter = adapter
        adapter.setAdapter(listComment)
        binding.rvComment.setHasFixedSize(true)
        binding.rvComment.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        /// TODO: Get comment with dbComment
        dbComment =
            Firebase.database.getReference("videos").child(getVideo().uidVideo!!).child("comments")
        dbComment.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val comment = it.getValue(Comment::class.java)!!
                    binding.comment = comment
                    if (comment == null) {
                        return
                    }
                    if (!listComment.contains(comment)) {
                        listComment.add(0, comment)
                        val result = listComment.size
                        binding.idCountComments.text = result.toString()
                        updateDataVideo(result)
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }


    private fun updateDataVideo(countComments: Int) {
        var hashMap: MutableMap<String, Int> = HashMap()
        hashMap.put("countComments", countComments)

        dbVideos = Firebase.database.getReference("videos").child(getVideo().uidVideo!!)
        dbVideos.updateChildren(hashMap as Map<String, Any>)
    }

    private fun initClick() {
        binding.ivClose.setOnClickListener(this)
        binding.imgSend.setOnClickListener(this)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme1
    }

    override fun onClick(view: View?) {
        val id = view?.id
        when (id) {
            R.id.ivClose -> {
                this.dismiss()
            }
            R.id.imgSend -> {
                val message = binding.edtMessage.text.toString().trim()
                binding.edtMessage.text.clear()
                val dbUser = Firebase.database.getReference("users")


                /// TODO: Before comment, check current user is logging
                dbUser.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                      for (element in snapshot.children){
                          /// TODO: Use snapshot.children and ref uuid of child to get uuid
                          val uuid = element.child("uuid").getValue(String::class.java)!!
                          /// TODO: Compare equal with currentUser of auth
                          if (auth.currentUser!!.uid == uuid) {
                              /// TODO: Ref dbUser use ValueEvent
                              dbUser.child(element.key!!).addValueEventListener(object :ValueEventListener{
                                  override fun onDataChange(snapshotUser: DataSnapshot) {
                                      /// TODO: Get user from element.key and compare with uuid
                                      val user = snapshotUser.getValue(User::class.java)!!
                                      val video = navArgs.video
                                      val uuidVideo = navArgs.video.uidVideo!!
                                      val comment = Comment(
                                          message = message,
                                          uidComment = message,
                                          countComments = 300,
                                          users = user,
                                          videos = video
                                      )
                                      /// TODO: Set value for dbComment
                                      dbComment = Firebase.database.getReference("comments")
                                      dbComment.child(message).setValue(comment)

                                      /// TODO: Set value for dbVideo
                                      val dbVideo = Firebase.database.getReference("videos")
                                      dbVideo.child(uuidVideo).child("comments").child(message).push().key
                                      dbVideo.child(uuidVideo).child("comments").child(message)
                                          .setValue(comment)
                                  }

                                  override fun onCancelled(error: DatabaseError) {
                                  }
                              })

                          }
                      }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }

    override fun onStart() {
        super.onStart()
//        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
//        adapter.stopListening()
    }

}