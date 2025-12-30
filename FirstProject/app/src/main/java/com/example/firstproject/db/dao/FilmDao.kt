    package com.example.firstproject.db.dao

    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.OnConflictStrategy
    import androidx.room.Query
    import com.example.firstproject.db.entity.FilmEntity
    @Dao
    interface FilmDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun putFilmData(film: FilmEntity)

        @Query("SELECT id, title, description, releaseYear, genre, country, imageUrl, userId FROM films WHERE id = :filmId")
        fun getFilmDataById(filmId: Int): FilmEntity?

        @Query("SELECT id, title, description, releaseYear, genre, country, imageUrl, userId FROM films")
        fun getFilmsData(): List<FilmEntity>

        @Query("SELECT id, title, description, releaseYear, genre, country, imageUrl, userId FROM films WHERE userId = :userId")
        fun getFilmsDataByUserId(userId: Int): List<FilmEntity>

        @Query("SELECT u.name FROM films f JOIN users u ON u.id = f.userId WHERE userId = :userId")
        fun getAuthorName(userId: Int): String?

        @Query("SELECT COUNT(*) FROM films WHERE title = :title AND userId = :userId")
        fun isFilmExists(title: String, userId: Int): Int

    }