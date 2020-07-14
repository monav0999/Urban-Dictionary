package com.urbandictionary.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.urbandictionary.R
import com.urbandictionary.remote.Information
import com.urbandictionary.util.Common
import kotlinx.android.synthetic.main.item_dictionary.view.*

class DictionaryAdapter(
    private val foContext: Context,
    private val fInformationList: List<Information>
) : RecyclerView.Adapter<DictionaryAdapter.ViewHolder>() {

    override fun onBindViewHolder(foViewHolder: ViewHolder, fiPosition: Int) {
        val info = fInformationList[fiPosition]

        foViewHolder.loTvTitle.text = info.title

        if (info.description.isNullOrEmpty()) {
            foViewHolder.loTvDescription.visibility = View.GONE
        } else {
            foViewHolder.loTvDescription.visibility = View.VISIBLE
            foViewHolder.loTvDescription.text = info.description!!.replace("[", "").replace("]", "")
        }

        if (info.example.isNullOrEmpty()) {
            foViewHolder.loTvExample.visibility = View.GONE
        } else {
            foViewHolder.loTvExample.visibility = View.VISIBLE
            foViewHolder.loTvExample.text = info.example!!.replace("[", "").replace("]", "")
        }

        if (info.author.isNullOrEmpty().not()) {
            foViewHolder.loTvAuthor.text = foContext.getString(R.string.TV_AUTHOR, info.author)
        }

        if (info.date.isNullOrEmpty().not()) {
            foViewHolder.loTvDate.text = Common.getRelativeTimeSpanString(info.date)
        }

        foViewHolder.loTvThumbsUp.text = info.thumbsUp.toString()
        foViewHolder.loTvThumbsDown.text = info.thumbsDown.toString()
    }

    override fun onCreateViewHolder(foViewGroup: ViewGroup, fiPosition: Int): ViewHolder {
        val loView = LayoutInflater.from(foViewGroup.context).inflate(
            R.layout.item_dictionary, foViewGroup, false
        )

        return ViewHolder(loView)
    }

    override fun getItemCount(): Int {
        return fInformationList.size
    }

    class ViewHolder(foView: View) : RecyclerView.ViewHolder(foView) {
        var loTvTitle: TextView = foView.tvTitle
        var loTvDescription: TextView = foView.tvDescription
        var loTvExample: TextView = foView.tvExample
        var loTvAuthor: TextView = foView.tvAuthor
        var loTvDate: TextView = foView.tvDate
        var loTvThumbsUp: TextView = foView.tvThumbsUp
        var loTvThumbsDown: TextView = foView.tvThumbsDown
    }
}