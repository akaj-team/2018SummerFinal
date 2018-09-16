package internship.asiantech.a2018summerfinal.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import internship.asiantech.a2018summerfinal.R
import internship.asiantech.a2018summerfinal.database.model.PlaylistSong
import internship.asiantech.a2018summerfinal.database.model.Song
import internship.asiantech.a2018summerfinal.viewholder.ListSongsChoiceViewHolder
import internship.asiantech.a2018summerfinal.viewholder.RadioButtonEventChoice

class PlaylistChoiceAdapter(var listSongs: MutableList<Song>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),RadioButtonEventChoice {

    private var mListBoolChoice = mutableListOf<Boolean>()
    private var listSongInPlaylist = mutableListOf<Song>()
    private var mListChoiced = mutableListOf<Boolean>()
    private var mIsCheck = false

    private lateinit var listener :RadioButtonEventChoice

    init {
        for (index in listSongInPlaylist.indices) {
            mListBoolChoice.add(false)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_choice_song_add_playlist, parent, false)
        return ListSongsChoiceViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return listSongs.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val song = listSongs[position]

        holder as ListSongsChoiceViewHolder
        holder.tvNameSongListChoice.text = song.name
        holder.tvAristListChoice.text = song.author
    }

    override fun onRadioButtonClickListener(position: Int, ischeck: Boolean) {
        var music = listSongs.get(position)
        if (mIsCheck){
            listSongInPlaylist.add(music)
            mListBoolChoice[position] = true

        }else{
            listSongInPlaylist.remove(music)
            mListBoolChoice[position] =false
        }

    }
    fun getListChoice(): MutableList<Song> {
        val mListChoicedSong = mutableListOf<Song>()
       listSongInPlaylist.forEachIndexed { index, song ->
           if (mListBoolChoice[index] == true) {
               mListChoicedSong.add(song)
           }
       }
        return mListChoicedSong
    }
    fun choiceAllItem(isCheck: Boolean, position: Int){
        var namePlaylist = listSongInPlaylist[position]
        if (isCheck){
            for(song in listSongs){
            listSongInPlaylist.add(song)
            }
            for (index in mListBoolChoice.indices){
                mListBoolChoice[index] = true

            }
        }else{
            listSongInPlaylist.clear()
            for (index in mListBoolChoice.indices){
                mListBoolChoice[index] = false
            }
        }
        notifyDataSetChanged()
    }
}