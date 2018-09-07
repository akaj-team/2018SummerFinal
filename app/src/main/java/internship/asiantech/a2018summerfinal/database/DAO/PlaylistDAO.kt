package internship.asiantech.a2018summerfinal.database.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import internship.asiantech.a2018summerfinal.database.model.Playlist

@Dao
interface PlaylistDAO {

    @Query("Select * from playlist")
    fun getAllPlaylist(): List<Playlist>

    @Query("Delete from playlist where name = :name")
    fun deletePlaylistWithName(name: String)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addPlaylist(playlist: Playlist)
}
