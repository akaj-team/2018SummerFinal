package internship.asiantech.a2018summerfinal.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import internship.asiantech.a2018summerfinal.R
import internship.asiantech.a2018summerfinal.adapter.MusicAdapter
import internship.asiantech.a2018summerfinal.listmusic.ListMusic
import internship.asiantech.a2018summerfinal.model.Music
import internship.asiantech.a2018summerfinal.service.MusicPlayer
import internship.asiantech.a2018summerfinal.service.MusicPlayerEventListener
import internship.asiantech.a2018summerfinal.service.SongInteractListener
import kotlinx.android.synthetic.main.fragment_list_songs.*

class ListSongsFragment : Fragment(), SongInteractListener, MusicPlayerEventListener {

    private lateinit var mMusicPlayer : MusicPlayer
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var musics: MutableList<Music>

    companion object {
        private const val KEY_POSITION = "position"
        fun instance(position: Int): ListSongsFragment {
            val timelineFragment = ListSongsFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_POSITION, position)
            timelineFragment.arguments = bundle
            return timelineFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_songs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListMusic()
        getAllist()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        context?.let { context ->
            val layoutManager = LinearLayoutManager(context)
            recyclerViewMusic.layoutManager = layoutManager
            musicAdapter = MusicAdapter(musics, context,this) { position ->
                musics[position].isFavourite = !musics[position].isFavourite
                musicAdapter.notifyDataSetChanged()
            }
            recyclerViewMusic.adapter = musicAdapter
        }
    }
    override fun songClicked(position: Int) {
        Log.d("abc", "abc");
        mMusicPlayer.playAt(position)
    }

    private fun initListMusic() {
        val bundle = arguments
        var position = 0
        if (bundle != null) {
            position = bundle.getInt(KEY_POSITION)
        }
        when (position) {
            0 -> {
                getAllist()
            }
            1 -> {
                getFavouriteList()
            }
            2 -> {
                getHistoryList()
            }
        }
    }

    private fun getHistoryList() {
        musics = ArrayList()
    }

    private fun getFavouriteList() {
        musics = ArrayList()
    }

    private fun getAllist() {
        context?.let {
            musics = ArrayList()
            val listMusic = ListMusic(it)
            musics = listMusic.getListMusics()
        }
    }

    override fun onPlayerStart(title: String, duration: Int) {
        mMusicPlayer.playAt(9)
    }

    override fun onPlayerPlaying(time: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPlayerPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPlayerUnPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
