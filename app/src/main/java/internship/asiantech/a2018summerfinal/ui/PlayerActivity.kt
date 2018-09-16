package internship.asiantech.a2018summerfinal.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import internship.asiantech.a2018summerfinal.R
import internship.asiantech.a2018summerfinal.adapter.MusicAdapter
import internship.asiantech.a2018summerfinal.database.model.Song
import internship.asiantech.a2018summerfinal.listmusic.ListMusic
import internship.asiantech.a2018summerfinal.model.Music
import internship.asiantech.a2018summerfinal.receiver.MusicReceiver
import internship.asiantech.a2018summerfinal.service.MusicPlayer
import internship.asiantech.a2018summerfinal.service.MusicPlayerEventListener
import internship.asiantech.a2018summerfinal.service.MusicService
import internship.asiantech.a2018summerfinal.service.SongInteractListener
import internship.asiantech.a2018summerfinal.service.utils.TimeTransferUtils
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.fragment_player.view.*

class PlayerActivity : AppCompatActivity(), View.OnClickListener,
        MusicPlayerEventListener,
        SongInteractListener {


    private lateinit var mMusicPlayer: MusicPlayer
    private lateinit var mReceiver: MusicReceiver
    private val mIsBounding: Boolean = false
    private lateinit var listSong: ArrayList<Music>
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_player)
        initViews()
        getAllist()
        mMusicPlayer.setMusicList(listSong)
        mReceiver = MusicReceiver(this)
        position = intent.getIntExtra(MusicAdapter.POSITION_ID, 0)

    }

    override fun onPlayerStart(title: String, duration: Int) {

        var title = listSong[position].songTitle
        var duration = listSong[position].duration
        mMusicPlayer.playAt(position)
        Log.e("aaa", "" + listSong[position].songId)
        updateInfor(title, duration)

    }

    override fun onPlayerPlaying(time: Int) {
        updateTime(time)
    }

    override fun onPlayerPause() {
        pause()
    }

    override fun onPlayerUnPause() {
        unPause()
    }

    override fun onResume() {
        super.onResume()
        mReceiver = MusicReceiver(
                object : MusicPlayerEventListener {
                    override fun onPlayerStart(title: String, duration: Int) {

                        var title = listSong[position].songTitle
                        var duration = listSong[position].duration
                        Log.e("aa", "" + listSong[position].songId)
                        mMusicPlayer.playAt(position)
                        updateInfor(title, duration)
                    }

                    override fun onPlayerPlaying(time: Int) {
                        updateTime(time)
                    }

                    override fun onPlayerPause() {
                        pause()
                    }

                    override fun onPlayerUnPause() {
                        unPause()
                    }
//                             ... functions to update ui here
                })

        var filter = IntentFilter().apply { addAction(MusicReceiver.ACTION_UPDATE_RECEIVER) }
        registerReceiver(mReceiver, filter)

    }

    override fun onPause() {
        super.onPause()
        mReceiver.let {
            unregisterReceiver(mReceiver)
        }
    }

    private fun getAllist() {
        this.let {
            listSong = ArrayList()
            val listMusic = ListMusic(it)
            listSong = listMusic.getListMusics()
        }
    }


    private fun initViews() {

        mMusicPlayer = MusicPlayer(this, this)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                mMusicPlayer.endNotifyTime()
            }

            override fun onStopTrackingTouch(p0: SeekBar) {

                mMusicPlayer.setSeekPosition(p0.progress)
            }

        })

        findViewById<ImageView>(R.id.imgPlayOrPauseSong).setOnClickListener(this)
        findViewById<ImageView>(R.id.imgNextSong).setOnClickListener(this)
        findViewById<ImageView>(R.id.imgPreviousSong).setOnClickListener(this)
    }

    override fun songClicked(position: Int) {
        if (mIsBounding) {
            Log.e("aaa", "lick click")
            mMusicPlayer.playAt(position)
            imgPlayOrPauseSong.setImageResource(R.drawable.ic_pause)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgNextSong -> {
                mMusicPlayer.let {
                    var title = listSong[position].songTitle
                    var duration = listSong[position].duration
                    updateInfor(title, duration)
                    Toast.makeText(this, "vo day", Toast.LENGTH_SHORT).show()
                    mMusicPlayer.nextSong()
                }
            }
            R.id.imgPreviousSong -> {
                mMusicPlayer.let {
                    var title = listSong[position].songTitle
                    var duration = listSong[position].duration
                    updateInfor(title, duration)
                    Toast.makeText(this, "vo day", Toast.LENGTH_SHORT).show()
                    mMusicPlayer.previousSong()
                }
            }
            R.id.imgPlayOrPauseSong -> {

                if (mIsBounding) {
                    Toast.makeText(this, "vo day", Toast.LENGTH_SHORT).show()
                    mMusicPlayer.changeState()
                }
            }
        }
    }

    private fun unPause() {
        imgPlayOrPauseSong.setImageResource(R.drawable.ic_pause)
    }

    private fun pause() {
        imgPlayOrPauseSong.setImageResource(R.drawable.ic_play)
    }

    private fun updateTime(time: Int) {
        seekBar.progress = time
        this.runOnUiThread(Runnable {
            tvRunningTime.text = TimeTransferUtils().millisecondToClock(time)
        })
    }

    fun updateInfor(title: String, duration: Int) {
        tvSongName.text = title
        seekBar.max = duration
        seekBar.progress = 0
        tvTotalTime.text = TimeTransferUtils().millisecondToClock(duration)
    }
}
