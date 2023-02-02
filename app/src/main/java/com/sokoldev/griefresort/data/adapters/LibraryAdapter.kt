package com.sokoldev.griefresort.data.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Library
import com.sokoldev.griefresort.ui.activities.ItemListActivity
import com.sokoldev.griefresort.ui.activities.QuotesActivity
import com.sokoldev.griefresort.ui.fragments.subscription.UnlockActivity
import com.sokoldev.griefresort.utils.Constants


class LibraryAdapter(
    val arrayList: List<Library>
) : RecyclerView.Adapter<LibraryAdapter.DataObjectHolder>() {

    private lateinit var context: Context

    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var image: AppCompatImageView? = itemView?.findViewById(R.id.image)
        var title: AppCompatTextView? = itemView?.findViewById(R.id.title)
        var button: MaterialButton? = itemView?.findViewById(R.id.btnViewAll)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DataObjectHolder {
        context = parent.context
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_library, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val library = arrayList[position]

        holder.title?.text = library.title
        holder.image?.setImageResource(library.image)

        holder.button?.setOnClickListener {
            if (library.title == Constants.BOOKS) {
                var intent = Intent(context, ItemListActivity::class.java)
                intent.putExtra(Constants.ITEM, Constants.BOOKS)
                context.startActivity(intent)
            } else if (library.title == Constants.MOVIES) {
                var intent = Intent(context, ItemListActivity::class.java)
                intent.putExtra(Constants.ITEM, Constants.MOVIES)
                context.startActivity(intent)
            } else if (library.title == Constants.PODCASTS) {
                var intent = Intent(context, UnlockActivity::class.java)
                intent.putExtra(Constants.ITEM, Constants.PODCASTS)
                context.startActivity(intent)
            } else if (library.title == Constants.TVSHOWS) {
                var intent = Intent(context, ItemListActivity::class.java)
                intent.putExtra(Constants.ITEM, Constants.TVSHOWS)
                context.startActivity(intent)
            } else if (library.title == Constants.SONGS) {
                var intent = Intent(context, ItemListActivity::class.java)
                intent.putExtra(Constants.ITEM, Constants.SONGS)
                context.startActivity(intent)
            } else {
                var intent = Intent(context, QuotesActivity::class.java)
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}