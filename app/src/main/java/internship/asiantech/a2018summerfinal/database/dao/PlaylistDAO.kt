package internship.asiantech.a2018summerfinal.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import internship.asiantech.a2018summerfinal.database.model.Playlist

@Dao
interface PlaylistDAO {

    @Query("Select * from playlist")
    fun getAllPlaylist(): List<Playlist>

    @Query("Delete from playlist where title = :title")
    fun deletePlaylistWithName(title: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPlaylist(playlist: Playlist)
}
