package co.id.aruna.typicode.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.id.aruna.typicode.R
import co.id.aruna.typicode.data.model.UsersItem

class UserAdapter(private val context: Context?, private val userList : List<UsersItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view : View)  : RecyclerView.ViewHolder(view){

        private val tvUserId = view.findViewById<TextView>(R.id.tvUserId)
        private val tvId = view.findViewById<TextView>(R.id.tvId)
        private val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        private val tvBody = view.findViewById<TextView>(R.id.tvBody)

        fun bind(user: UsersItem){
            tvUserId.text = user.userId.toString()
            tvId.text = user.id.toString()
            tvTitle.text = user.title
            tvBody.text = user.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }
}