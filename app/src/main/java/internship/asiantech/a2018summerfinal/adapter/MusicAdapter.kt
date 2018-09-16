package internship.asiantech.a2018summerfinal.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import internship.asiantech.a2018summerfinal.R
import internship.asiantech.a2018summerfinal.adapter.MusicAdapter.MusicHolder
import internship.asiantech.a2018summerfinal.model.Music
import internship.asiantech.a2018summerfinal.service.MusicPlayer
import internship.asiantech.a2018summerfinal.service.MusicService
import internship.asiantech.a2018summerfinal.service.SongInteractListener
import internship.asiantech.a2018summerfinal.ui.PlayerActivity

class MusicAdapter(private val musics: List<Music>, private val context: Context,
                   private val listener: SongInteractListener,
                   private val onFavouriteListener: (position: Int) -> Unit)
    : RecyclerView.Adapter<MusicHolder>() {
    private lateinit var mMusicService: MusicService
    private lateinit var mMusicPlayer: MusicPlayer
    private  var  mPosition: Int = -1
    companion object {
     val POSITION_ID ="position"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_music, parent, false)
        return MusicHolder(view)
    }

    override fun getItemCount(): Int {
        return musics.size
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        val music:Music = musics[position]
        holder.tvTitleSong?.text = musics[position].songTitle
        holder.tvSinger?.text = musics[position].artist
        if (musics[position].isFavourite) {
            holder.imgFavourite?.setImageResource(R.drawable.ic_like)
        } else {
            holder.imgFavourite?.setImageResource(R.drawable.ic_unlike)
        }

    }

    inner class MusicHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val tvTitleSong = itemView?.findViewById<TextView>(R.id.tvTitleSong)
        val tvSinger = itemView?.findViewById<TextView>(R.id.tvSinger)
        val imgFavourite = itemView?.findViewById<ImageView>(R.id.imgFavourite)

        init {
            imgFavourite?.setOnClickListener {
                val position = layoutPosition
                onFavouriteListener(position)
            }
            itemView?.setOnClickListener(View.OnClickListener {
                mPosition = layoutPosition
               val intent  = Intent(context,PlayerActivity::class.java )
                intent.putExtra(POSITION_ID,mPosition)
                context.startActivity(intent)
                //listener.(songClickedadapterPosition)

//                context.startService(Intent(context, MusicService::class.java)
//                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            })
        }
    }
}
