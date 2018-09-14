package internship.asiantech.a2018summerfinal.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import internship.asiantech.a2018summerfinal.R
import internship.asiantech.a2018summerfinal.service.MusicPlayer
import internship.asiantech.a2018summerfinal.service.SongInteractListener
import internship.asiantech.a2018summerfinal.service.utils.TimeTransferUtils
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.fragment_player.view.*

class PlayerActivity : AppCompatActivity(), View.OnClickListener,SongInteractListener {



    private lateinit var mMusicPlayer: MusicPlayer
    private val mIsBounding: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_player)
        initViews()
        setListener()
    }

    private fun initViews() {

    }
    override fun songClicked(position: Int) {

    }

    private fun setListener() {

        imgSongStateRepeat.imgStateShuffle.
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgNextSong -> {
                mMusicPlayer.let {
                    mMusicPlayer.nextSong()
                }
            }
            R.id.imgPreviousSong -> {
                mMusicPlayer.let {
                    mMusicPlayer.previousSong()
                }
            }
            R.id.imgPlayOrPauseSong -> {
                if (mIsBounding) {
                    mMusicPlayer.changeState()
                }
            }
        }
    }
    private fun unPause(){
        imgPlayOrPauseSong.setImageDrawable(resources.getDrawable(R.drawable.ic_pause))
    }

    private fun pause() {
        imgPlayOrPauseSong.setImageResource(R.drawable.ic_play)
    }

    private fun updateTime(time: Int) {
        seekBar.progress = time
        tvRunningTime.text = TimeTransferUtils().millisecondToClock(time)
    }

    private fun updateInfor(title: String,artist: String , duration: Int) {
        tvSongName.text = title
        tvSongArtist.text = artist
        seekBar.max = duration
        seekBar.progress = 0
        tvTotalTime.text = TimeTransferUtils().millisecondToClock(duration)
    }

}
